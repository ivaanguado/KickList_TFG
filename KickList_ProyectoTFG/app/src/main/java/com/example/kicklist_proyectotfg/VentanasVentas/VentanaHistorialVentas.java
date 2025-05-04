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

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorHistorialVentas;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.Persistencia.Venta;
import com.example.kicklist_proyectotfg.R;

import java.util.ArrayList;

public class VentanaHistorialVentas extends AppCompatActivity {

    private GridView gridView;
    private Button btnVolver;
    DatabaseHelper db;
    AdaptadorHistorialVentas adaptador;
    ArrayList<Venta> ventas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_historial_ventas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        gridView = findViewById(R.id.gridview_historial_ventas);
        btnVolver = findViewById(R.id.btn_volver_historial_ventas);

        db = new DatabaseHelper(this);
        // Obtener las ventas de la base de datos
        ventas = db.obtenerVentas();

        // Crear el adaptador y asignarlo al GridView
        if (ventas.isEmpty()) {
            gridView.setVisibility(View.GONE);
        }else{
            // Crear el adaptador y asignarlo al GridView
            adaptador = new AdaptadorHistorialVentas(this, ventas);
            gridView.setAdapter(adaptador);
        }

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaHistorialVentas.this, VentanaVentas.class);
                startActivity(intent);
                finish();
            }
        });


    }
}