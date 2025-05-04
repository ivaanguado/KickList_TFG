package com.example.kicklist_proyectotfg.VentanasInventario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorModificarStock;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;

import java.util.List;

import com.example.kicklist_proyectotfg.Persistencia.Producto;

public class VentanaModificarStock extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private GridView gridView;
    private List<Producto> productos;
    Button botonVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_modificar_stock);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        productos = dbHelper.obtenerProductos();

        // Configurar el GridView
        gridView = findViewById(R.id.gridView_modificarStock);
        AdaptadorModificarStock adaptador = new AdaptadorModificarStock(this, productos);
        // Asignar el adaptador al GridView
        gridView.setAdapter(adaptador);

        botonVolver = findViewById(R.id.btn_volver_modificar);
        botonVolver.setOnClickListener(v -> {
            Intent intent = new Intent(VentanaModificarStock.this, VentanaInventario.class);
            startActivity(intent);
            finish();
        });



    }
}