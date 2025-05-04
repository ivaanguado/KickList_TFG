package com.example.kicklist_proyectotfg.VentanasEstadisticas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;

import java.util.ArrayList;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorConsultarDatos;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.Persistencia.Producto;

public class VentanaConsultarDatosZapatilla extends AppCompatActivity {

    Button btn_buscar_producto, btn_volver_consultar_datos;
    EditText edt_consultar_datos;
    GridView gridView_consultar_datos;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_consultar_datos_zapatilla);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_buscar_producto = findViewById(R.id.btn_buscar_producto);
        btn_volver_consultar_datos = findViewById(R.id.btn_volver_consultar_datos);

        edt_consultar_datos = findViewById(R.id.edt_consultar_datos);
        gridView_consultar_datos = findViewById(R.id.gridView_consultar_datos);
        db = new DatabaseHelper(this);


        btn_buscar_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String producto = edt_consultar_datos.getText().toString();
                ArrayList<Producto> productos = db.buscarProductos(producto);

                // Verificar si la lista de productos está vacía
                if (productos.isEmpty()) {
                    Toast.makeText(VentanaConsultarDatosZapatilla.this, "No se encontraron productos", Toast.LENGTH_SHORT).show();
                    edt_consultar_datos.setText("");
                } else {
                    // Crear el adaptador y asignarlo al GridView
                    AdaptadorConsultarDatos adaptador = new AdaptadorConsultarDatos(VentanaConsultarDatosZapatilla.this, productos);
                    gridView_consultar_datos.setAdapter(adaptador);
                }
            }
        });

        btn_volver_consultar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaConsultarDatosZapatilla.this, VentanaEstadisticas.class);
                startActivity(intent);
                finish();
            }
        });

    }
}