package com.example.kicklist_proyectotfg.VentanasInventario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;

import java.util.List;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorVerStock;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.Persistencia.Producto;

public class VentanaVerStock extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private GridView gridView;
    private List<Producto> productos;
    Button volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_ver_stock);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView = findViewById(R.id.gridView);
        dbHelper = new DatabaseHelper(this);
        // Obtener la lista de productos
        productos = dbHelper.obtenerProductos();

        if(productos.isEmpty()){
            gridView.setVisibility(View.GONE);
        }else{
            // Crear el adaptador y asignarlo al GridView
            AdaptadorVerStock adaptador = new AdaptadorVerStock(this, productos);
            gridView.setAdapter(adaptador);
        }

        volver = findViewById(R.id.btn_volver_ver_stock);
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaVerStock.this, VentanaInventario.class);
                startActivity(intent);
                finish();
            }
        });

    }
}