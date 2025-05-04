package com.example.kicklist_proyectotfg.VentanasVentas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.VentanaPrincipal.Menu;

public class VentanaVentas extends AppCompatActivity {

    Button btn_realizar_venta, btn_ver_historial_ventas, btn_consignar, btn_ver_productos_consignados;
    ImageButton btn_volver_ventas;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_ventas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        // Obtener los elementos de la interfaz
        btn_realizar_venta = findViewById(R.id.btn_realizar_venta);
        btn_ver_historial_ventas = findViewById(R.id.btn_ver_historial_ventas);
        btn_volver_ventas = findViewById(R.id.btn_volver_ventas);
        btn_consignar = findViewById(R.id.btn_consignar);
        btn_ver_productos_consignados = findViewById(R.id.btn_ver_productos_consignados);

        btn_volver_ventas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaVentas.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });

        btn_realizar_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaVentas.this, VentanaRealizarVenta.class);
                startActivity(intent);
                finish();
            }
        });

        btn_ver_historial_ventas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaVentas.this, VentanaHistorialVentas.class);
                startActivity(intent);
                finish();
            }
        });

        btn_consignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaVentas.this, VentanaConsignar.class);
                startActivity(intent);
                finish();
            }
        });

        btn_ver_productos_consignados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaVentas.this, VentanaVerProductosConsignados.class);
                startActivity(intent);
                finish();
            }
        });

    }
}