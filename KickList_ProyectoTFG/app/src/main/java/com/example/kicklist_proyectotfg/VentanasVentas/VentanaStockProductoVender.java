package com.example.kicklist_proyectotfg.VentanasVentas;

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

import com.example.kicklist_proyectotfg.Persistencia.CarritoTemporal;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;

import java.util.List;
import java.util.Map;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorStockProductoVender;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

public class VentanaStockProductoVender extends AppCompatActivity {

    TextView txt_nombre, txt_sku;
    RecyclerView recycler_tallas;
    Button btn_aniadir, btn_volver;
    String nombre, sku;
    DatabaseHelper db;
    AdaptadorStockProductoVender adaptador;
    List<Stock> lista;
    String consignar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_stock_producto_vender);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener elementos de la interfaz
        txt_nombre = findViewById(R.id.txt_nombre_producto_a_vender);
        txt_sku = findViewById(R.id.txt_sku_producto_a_vender);
        recycler_tallas = findViewById(R.id.recycler_tallas_producto_a_vender);
        recycler_tallas.setLayoutManager(new LinearLayoutManager(this));
        btn_aniadir = findViewById(R.id.btn_aniadir_productos_a_vender);
        btn_volver = findViewById(R.id.btn_volver_menu_vender);

        db = new DatabaseHelper(this);

        // Obtener datos del intent
        Intent intent = getIntent();
        if (intent != null) {
            // Recuperar datos del intent
            nombre = intent.getStringExtra("nombre");
            sku = intent.getStringExtra("sku");
            consignar = intent.getStringExtra("consignar");
            txt_nombre.setText(nombre);
            txt_sku.setText(sku);
        }

        // Cargar el stock por SKU
        lista = db.obtenerStockPorSku(sku);

        // Crear adaptador
        adaptador = new AdaptadorStockProductoVender(this, lista, sku);
        recycler_tallas.setAdapter(adaptador);

        // Añadir productos seleccionados
        btn_aniadir.setOnClickListener(v -> {
            Map<Double, Integer> seleccionados = adaptador.getCantidadesSeleccionadas();

            // Añadir productos seleccionados al carrito
            for (Map.Entry<Double, Integer> entry : seleccionados.entrySet()) {
                int cantidad = entry.getValue();
                // Verificar si la cantidad es mayor que cero
                if (cantidad > 0) {
                    Stock producto = new Stock(0, sku, entry.getKey(), cantidad);
                    // Añadir el producto al carrito
                    CarritoTemporal.getInstancia().aniadirProducto(producto);
                }
            }

            if(consignar != null){
                Intent intentDetalle = new Intent(VentanaStockProductoVender.this, VentanaConsignar.class);
                startActivity(intentDetalle);
                finish();
            }else{
                Intent intentDetalle = new Intent(VentanaStockProductoVender.this, VentanaRealizarVenta.class);
                startActivity(intentDetalle);
                finish();
            }
        });



        // Volver sin vender
        btn_volver.setOnClickListener(v -> {
            if(consignar != null){
                Intent intentVolver = new Intent(VentanaStockProductoVender.this, VentanaConsignar.class);
                startActivity(intentVolver);
                finish();
            }else{
                Intent intentVolver = new Intent(VentanaStockProductoVender.this, VentanaRealizarVenta.class);
                startActivity(intentVolver);
                finish();
            }
        });

    }
}
