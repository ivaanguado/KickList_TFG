package com.example.kicklist_proyectotfg.VentanasVentas;

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

import com.example.kicklist_proyectotfg.R;

import java.util.ArrayList;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorProductosConsignadosTienda;
import com.example.kicklist_proyectotfg.Persistencia.Consigna;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class VentanaTienda extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    Button btn_volver;
    TextView txt_tienda;
    AdaptadorProductosConsignadosTienda adaptador;
    ArrayList<Consigna> productosConsignados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_tienda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dbHelper = new DatabaseHelper(this);
        recyclerView = findViewById(R.id.recycler_prods_consignados);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btn_volver = findViewById(R.id.btn_volver_prods_consignados);

        // Obtener el nombre de la tienda del intent
        Intent intent = getIntent();
        String tienda = intent.getStringExtra("nombreTienda");
        txt_tienda = findViewById(R.id.txt_tienda_consigna);
        txt_tienda.setText(tienda);

        // Obtener los productos consignados de la tienda
        productosConsignados = dbHelper.obtenerProductosConsignados(tienda);
        adaptador = new AdaptadorProductosConsignadosTienda(this, productosConsignados);
        recyclerView.setAdapter(adaptador);

        btn_volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaTienda.this, VentanaVerProductosConsignados.class);
                startActivity(intent);
                finish();
            }
        });

    }
}