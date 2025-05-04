package com.example.kicklist_proyectotfg.VentanasInventario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorModificarStockProducto;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.kicklist_proyectotfg.Persistencia.Stock;

public class VentanaModificarStockProducto extends AppCompatActivity {

    DatabaseHelper dbHelper;
    List<Stock> stock;
    AdaptadorModificarStockProducto adaptador;
    RecyclerView recyclerView;
    TextView txt_modificar_tallas_nombre, txt_modificar_tallas_sku;
    Button btn_guardar_stock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_modificar_stock_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener los elementos de la interfaz
        txt_modificar_tallas_nombre = findViewById(R.id.txt_modificar_tallas_nombre);
        txt_modificar_tallas_sku = findViewById(R.id.txt_modificar_tallas_sku);
        recyclerView = findViewById(R.id.recyclerView_modificarStock);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btn_guardar_stock = findViewById(R.id.btn_guardar_stock);

        dbHelper = new DatabaseHelper(this);

        // Obtener los datos del producto seleccionado
        String nombre = getIntent().getStringExtra("nombre");
        String sku = getIntent().getStringExtra("sku");

        // Mostrar los datos en los TextViews
        txt_modificar_tallas_nombre.setText(nombre);
        txt_modificar_tallas_sku.setText(sku);

        // Cargar todas las tallas desde la 36 hasta la 50 (incluyendo medias)
        List<Stock> stockCompleto = new ArrayList<>();
        List<Stock> stockExistente = dbHelper.obtenerStockPorSku(sku);

        // Crear un mapa con las tallas existentes
        Map<Double, Integer> mapaExistente = new HashMap<>();
        for (Stock s : stockExistente) {
            // Guardar la talla y la cantidad en el mapa
            mapaExistente.put(s.getTalla(), s.getCantidad());
        }

        // Recorrer todas las tallas desde la 36 hasta la 50
        for (double talla = 36.0; talla <= 50.0; talla += 0.5) {
            // Si la talla no existe en el mapa, agregarla con cantidad 0
            int cantidad = mapaExistente.containsKey(talla) ? mapaExistente.get(talla) : 0;
            // Agregar el stock completo a la lista
            stockCompleto.add(new Stock(0, sku, talla, cantidad));
        }

        // Asignar la lista de stock al adaptador
        stock = stockCompleto;

        // Crear el adaptador y asignarlo al RecyclerView
        adaptador = new AdaptadorModificarStockProducto(this, stock, sku);
        recyclerView.setAdapter(adaptador);

        btn_guardar_stock.setOnClickListener(v -> {
            // Si el stock no está vacío, actualizarlo
            if (stock != null) {
                for (Stock s : stock) {
                    // Si la cantidad es mayor que 0, insertar o actualizar el stock, de lo contrario, eliminar
                    if (s.getCantidad() > 0) {
                        dbHelper.insertarOActualizarStock(s.getSku(), s.getTalla(), s.getCantidad());
                    } else {
                        // Si la cantidad es 0, eliminar el stock
                        dbHelper.eliminarStockSiExiste(s.getSku(), s.getTalla());
                    }
                }
                Intent intent = new Intent(VentanaModificarStockProducto.this, VentanaModificarStock.class);
                startActivity(intent);
                finish();
            }
        });



    }
}