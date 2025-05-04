package com.example.kicklist_proyectotfg.VentanasInventario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorVerStockDetallado;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;

import java.util.List;

import com.example.kicklist_proyectotfg.Persistencia.Stock;

public class VentanaVerStockDetallado extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private RecyclerView recyclerView;
    private List<Stock> stock;
    private String nombreProducto;
    private String sku;
    TextView txt_nombre_producto_ver_stock, txt_sku_ver_stock;
    Button btn_volver_ver_stock_detallado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_ver_stock_detallado);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar componentes de la interfaz
        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler_ver_stock);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Obtener los datos de la actividad anterior
        txt_nombre_producto_ver_stock = findViewById(R.id.txt_nombre_producto_ver_stock);
        txt_sku_ver_stock = findViewById(R.id.txt_sku_ver_stock);
        btn_volver_ver_stock_detallado = findViewById(R.id.btn_volver_ver_stock_detallado);

        // Obtener los datos de la actividad anterior
        nombreProducto = getIntent().getStringExtra("nombre");
        sku = getIntent().getStringExtra("sku");

        txt_nombre_producto_ver_stock.setText(nombreProducto);
        txt_sku_ver_stock.setText(sku);

        // Obtener el stock del producto
        stock = dbHelper.obtenerStockPorSku(sku);

        // Crear el adaptador y asignarlo al RecyclerView
        AdaptadorVerStockDetallado adaptador = new AdaptadorVerStockDetallado(this, stock, sku);
        recyclerView.setAdapter(adaptador);

        btn_volver_ver_stock_detallado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaVerStockDetallado.this, VentanaVerStock.class);
                startActivity(intent);
                finish();
            }
        });


    }
}