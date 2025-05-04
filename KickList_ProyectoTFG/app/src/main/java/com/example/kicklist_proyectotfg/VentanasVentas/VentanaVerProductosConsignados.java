package com.example.kicklist_proyectotfg.VentanasVentas;

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

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorProductosConsignados;
import com.example.kicklist_proyectotfg.R;

import java.util.HashMap;
import java.util.Map;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class VentanaVerProductosConsignados extends AppCompatActivity {

    private GridView gridView;
    private Button btnVolver;
    DatabaseHelper db;
    AdaptadorProductosConsignados adaptador;
    Map<String, Integer> tiendas = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_ver_productos_consignados);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView = findViewById(R.id.gridview_prod_consignados);
        btnVolver = findViewById(R.id.btn_volver_prod_consignados);

        db = new DatabaseHelper(this);

        // Obtener los productos consignados
        tiendas = db.obtenerMapaTiendas();

        // Mostrar los productos consignados en el GridView
        if (tiendas.isEmpty()) {
            gridView.setVisibility(View.GONE);
        }else{
            // Crear el adaptador y asignarlo al GridView
            adaptador = new AdaptadorProductosConsignados(this, tiendas);
            gridView.setAdapter(adaptador);
        }

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaVerProductosConsignados.this, VentanaVentas.class);
                startActivity(intent);
                finish();
            }
        });

    }
}