package com.example.kicklist_proyectotfg.VentanasVentas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorDetallesVenta;
import com.example.kicklist_proyectotfg.R;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.kicklist_proyectotfg.Persistencia.CarritoTemporal;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

public class VentanaRealizarConsigna extends AppCompatActivity {

    RecyclerView recycler_precios_consigna;
    Button btn_finalizar_consigna, btn_cancelar_consigna;
    AdaptadorDetallesVenta adaptador;
    ArrayList<Stock> stockSeleccionado;
    EditText edt_tienda;
    String fecha = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_realizar_consigna);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener los elementos de la interfaz
        recycler_precios_consigna = findViewById(R.id.recycler_precios_consigna);
        recycler_precios_consigna.setLayoutManager(new LinearLayoutManager(this));

        btn_finalizar_consigna = findViewById(R.id.btn_aniadir_consigna);
        btn_cancelar_consigna = findViewById(R.id.btn_cancelar_consigna);
        edt_tienda = findViewById(R.id.edt_tienda);

        Intent intent = getIntent();
        // Obtener la lista de productos seleccionados desde la actividad anterior
        stockSeleccionado = (ArrayList<Stock>) intent.getSerializableExtra("productos_finales");

        // Crear el adaptador y asignarlo al RecyclerView
        adaptador = new AdaptadorDetallesVenta(this, stockSeleccionado);
        recycler_precios_consigna.setAdapter(adaptador);

        // Guardar fecha de venta
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            fecha = LocalDate.now().toString();
        }

        btn_cancelar_consigna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzamos un AlertDialog para preguntar si queremos cancelar la venta o no
                AlertDialog.Builder builder = new AlertDialog.Builder(VentanaRealizarConsigna.this);
                builder.setTitle("Cancelar consigna");
                builder.setMessage("¿Desea cancelar la consigna?");
                builder.setPositiveButton("Sí", (dialog, which) -> {
                    CarritoTemporal.getInstancia().vaciar();
                    Intent intentVolver = new Intent(VentanaRealizarConsigna.this, VentanaVentas.class);
                    startActivity(intentVolver);
                    finish();
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });

                builder.show();
            }
        });

        btn_finalizar_consigna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean edtsRellenos = true;

                // Verificar si los EditText están llenos
                if (edt_tienda.getText().toString().trim().isEmpty()) {
                    edtsRellenos = false;
                } else {
                    for (int i = 0; i < recycler_precios_consigna.getChildCount(); i++) {
                        View itemView = recycler_precios_consigna.getChildAt(i);
                        EditText edtCompra = itemView.findViewById(R.id.edt_compra_detalles);
                        EditText edtVenta = itemView.findViewById(R.id.edt_venta_detalles);

                        if (edtCompra.getText().toString().trim().isEmpty() || edtVenta.getText().toString().trim().isEmpty()) {
                            edtsRellenos = false;
                            break;
                        }
                    }
                }

                if (!edtsRellenos) {
                    new AlertDialog.Builder(VentanaRealizarConsigna.this)
                            .setTitle("Campos incompletos")
                            .setMessage("Rellena todos los precios antes de finalizar la consigna.")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                } else {
                    DatabaseHelper db = new DatabaseHelper(VentanaRealizarConsigna.this);
                    String tienda = edt_tienda.getText().toString().trim();
                    tienda = formatearNombreTienda(tienda);

                    // Fecha
                    String fecha = "";
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        fecha = LocalDate.now().toString();
                    }

                    // Recorrer la lista de productos
                    for (int i = 0; i < stockSeleccionado.size(); i++) {
                        Stock stock = stockSeleccionado.get(i);
                        int cantidad = stock.getCantidad();

                        if (cantidad <= 0) continue;

                        View itemView = recycler_precios_consigna.getLayoutManager().findViewByPosition(i);
                        if (itemView != null) {
                            EditText edtCompra = itemView.findViewById(R.id.edt_compra_detalles);
                            EditText edtVenta = itemView.findViewById(R.id.edt_venta_detalles);

                            double precioCompra = Double.parseDouble(edtCompra.getText().toString().trim());
                            double precioVenta = Double.parseDouble(edtVenta.getText().toString().trim());

                            if (precioCompra < 0 || precioVenta < 0) {
                                Toast.makeText(VentanaRealizarConsigna.this, "Los precios no pueden ser negativos", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            // Recorrer la cantidad de productos para realizar la consigna
                            for (int j = 0; j < cantidad; j++) {
                                db.insertarConsigna(tienda, stock.getTalla(), stock.getSku(), precioCompra, precioVenta, fecha);
                            }

                            int stockActual = db.obtenerStockPorSku(stock.getSku()).stream()
                                    .filter(s -> s.getTalla() == stock.getTalla())  // Filtrar por talla
                                    .mapToInt(Stock::getCantidad)                   // Mapear a cantidad
                                    .findFirst().orElse(0);                   // Obtener el primer valor o 0 si no se encuentra

                            // Actualizar el stock
                            int nuevoStock = stockActual - cantidad;

                            if (nuevoStock > 0) {
                                // Actualizar el stock en la base de datos
                                db.insertarOActualizarStock(stock.getSku(), stock.getTalla(), nuevoStock);
                            } else {
                                // Eliminar el stock si es menor o igual a 0
                                db.eliminarStockSiExiste(stock.getSku(), stock.getTalla());
                            }
                        }
                    }

                    new AlertDialog.Builder(VentanaRealizarConsigna.this)
                            .setTitle("Consigna realizada")
                            .setCancelable(false)
                            .setMessage("La consigna en " + tienda + " se ha realizado correctamente.")
                            .setPositiveButton("OK", (dialog, which) -> {
                                dialog.dismiss();
                                CarritoTemporal.getInstancia().vaciar();
                                Intent intentVolver = new Intent(VentanaRealizarConsigna.this, VentanaVentas.class);
                                startActivity(intentVolver);
                                finish();
                            })
                            .show();
                }
            }
        });


    }

    // Formatear el nombre de la tienda
    public String formatearNombreTienda(String nombre) {
        // Dividir el nombre en palabras
        String[] palabras = nombre.trim().toLowerCase().split(" ");
        StringBuilder formateado = new StringBuilder();

        // Formatear cada palabra
        for (String palabra : palabras) {
            // Si la palabra no está vacía, formatearla
            if (!palabra.isEmpty()) {
                // Formatear la primera letra en mayúscula y el resto en minúsculas
                formateado.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }

        return formateado.toString().trim();
    }


}