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
import com.example.kicklist_proyectotfg.Persistencia.CarritoTemporal;
import com.example.kicklist_proyectotfg.R;

import java.time.LocalDate;
import java.util.ArrayList;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

public class VentanaDetallesVenta extends AppCompatActivity {

    RecyclerView recycler_stock_carrito;
    Button btn_aniadir_carrito, btn_volver_stock_producto;
    AdaptadorDetallesVenta adaptador;
    ArrayList<Stock> stockSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_detalles_venta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener los productos del carrito
        recycler_stock_carrito = findViewById(R.id.recycler_stock_carrito);
        recycler_stock_carrito.setLayoutManager(new LinearLayoutManager(this));

        btn_aniadir_carrito = findViewById(R.id.btn_aniadir_carrito);
        btn_volver_stock_producto = findViewById(R.id.btn_volver_stock_producto);

        Intent intent = getIntent();
        // Obtener los productos del carrito
        stockSeleccionado = (ArrayList<Stock>) intent.getSerializableExtra("productos_finales");

        // Crear el adaptador y asignarlo al RecyclerView
        adaptador = new AdaptadorDetallesVenta(this, stockSeleccionado);
        recycler_stock_carrito.setAdapter(adaptador);

        btn_volver_stock_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lanzamos un AlertDialog para preguntar si queremos cancelar la venta o no
                AlertDialog.Builder builder = new AlertDialog.Builder(VentanaDetallesVenta.this);
                builder.setTitle("Cancelar venta");
                builder.setMessage("¿Desea cancelar la venta?");
                builder.setPositiveButton("Sí", (dialog, which) -> {
                    CarritoTemporal.getInstancia().vaciar();
                    Intent intentVolver = new Intent(VentanaDetallesVenta.this, VentanaRealizarVenta.class);
                    startActivity(intentVolver);
                    finish();
                });
                builder.setNegativeButton("No", (dialog, which) -> {
                    dialog.dismiss();
                });

                builder.show();
            }
        });

        btn_aniadir_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean edtsRellenos = true;

                // Verificar si los EditText están llenos
                for (int i = 0; i < recycler_stock_carrito.getChildCount(); i++) {
                    View itemView = recycler_stock_carrito.getChildAt(i);
                    EditText edtCompra = itemView.findViewById(R.id.edt_compra_detalles);
                    EditText edtVenta = itemView.findViewById(R.id.edt_venta_detalles);

                    if (edtCompra.getText().toString().trim().isEmpty() || edtVenta.getText().toString().trim().isEmpty()) {
                        edtsRellenos = false;
                        break;
                    }
                }

                if (!edtsRellenos) {
                    new AlertDialog.Builder(VentanaDetallesVenta.this)
                            .setTitle("Campos incompletos")
                            .setMessage("Rellena todos los precios antes de finalizar la venta.")
                            .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                            .show();
                } else {
                    double resultado = 0;
                    DatabaseHelper db = new DatabaseHelper(VentanaDetallesVenta.this);

                    for (int i = 0; i < stockSeleccionado.size(); i++) {
                        Stock stock = stockSeleccionado.get(i);
                        int cantidad = stock.getCantidad();

                        // Si la cantidad es menor o igual a 0, no realizar la venta
                        if (cantidad <= 0) continue;

                        View itemView = recycler_stock_carrito.getLayoutManager().findViewByPosition(i);
                        if (itemView != null) {
                            EditText edtCompra = itemView.findViewById(R.id.edt_compra_detalles);
                            EditText edtVenta = itemView.findViewById(R.id.edt_venta_detalles);

                            double precioCompra = Double.parseDouble(edtCompra.getText().toString().trim());
                            double precioVenta = Double.parseDouble(edtVenta.getText().toString().trim());

                            if (precioCompra < 0 || precioVenta < 0){
                                Toast.makeText(VentanaDetallesVenta.this, "Los precios no pueden ser negativos", Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String fecha = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O
                                    ? LocalDate.now().toString() : "";

                            // Recorrer la cantidad de productos para realizar la venta
                            for (int j = 0; j < cantidad; j++) {
                                // Realizar la venta
                                db.insertarVenta(stock.getSku(), stock.getTalla(), precioCompra, precioVenta, fecha);
                                // Calcular el resultado
                                resultado += (precioVenta - precioCompra);
                            }

                            // Actualizar el stock
                            int stockActual = db.obtenerStockPorSku(stock.getSku()).stream()
                                    .filter(s -> s.getTalla() == stock.getTalla())      // Filtrar por talla
                                    .mapToInt(Stock::getCantidad)                       // Mapear a cantidad
                                    .findFirst().orElse(0);                       // Obtener el primer valor o 0 si no se encuentra

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

                    new AlertDialog.Builder(VentanaDetallesVenta.this)
                            .setTitle("Venta realizada")
                            .setMessage("La venta se ha realizado correctamente con un resultado de " + resultado + "€.")
                            .setCancelable(false)
                            .setPositiveButton("OK", (dialog, which) -> {
                                dialog.dismiss();
                                CarritoTemporal.getInstancia().vaciar();
                                Intent intentVolver = new Intent(VentanaDetallesVenta.this, VentanaVentas.class);
                                startActivity(intentVolver);
                                finish();
                            })
                            .show();
                }
            }
        });



    }
}