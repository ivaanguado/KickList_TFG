package com.example.kicklist_proyectotfg.VentanasEstadisticas;

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

import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.VentanaPrincipal.Menu;
import com.example.kicklist_proyectotfg.VentanasVentas.VentanaResumenVentas;

public class VentanaEstadisticas extends AppCompatActivity {

    ImageButton btn_volver_estadisticas;
    Button btn_grafico_uds, btn_grafico_beneficio, btn_consultar_datos, btn_resumen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_estadisticas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener los elementos de la interfaz
        btn_volver_estadisticas = findViewById(R.id.btn_volver_estadisticas);
        btn_grafico_uds = findViewById(R.id.btn_grafico_uds);
        btn_grafico_beneficio = findViewById(R.id.btn_grafico_beneficio);
        btn_consultar_datos = findViewById(R.id.btn_consultar_datos);
        btn_resumen = findViewById(R.id.btn_resumen);

        btn_volver_estadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaEstadisticas.this, Menu.class);
                startActivity(intent);
            }
        });

        btn_grafico_uds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaEstadisticas.this, VentanaGrafico_UdsMes.class);
                startActivity(intent);
            }
        });

        btn_grafico_beneficio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaEstadisticas.this, VentanaGrafico_BeneficioMes.class);
                startActivity(intent);
            }
        });

        btn_consultar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaEstadisticas.this, VentanaConsultarDatosZapatilla.class);
                startActivity(intent);
            }
        });

        btn_resumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaEstadisticas.this, VentanaResumenVentas.class);
                startActivity(intent);
            }
        });

    }
}