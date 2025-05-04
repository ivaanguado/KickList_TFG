package com.example.kicklist_proyectotfg.VentanaPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.VentanasEstadisticas.VentanaEstadisticas;
import com.example.kicklist_proyectotfg.VentanasInventario.VentanaInventario;
import com.example.kicklist_proyectotfg.VentanasVentas.VentanaVentas;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class Menu extends AppCompatActivity {

    DatabaseHelper db;
    Toolbar tb;
    ImageButton btnStock, btnVentas, btnEstadisticas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //inicializamos la base de datos
        db = new DatabaseHelper(this);

        btnStock = findViewById(R.id.btnStock);
        btnVentas = findViewById(R.id.btnVentas);
        btnEstadisticas = findViewById(R.id.btnEstadisticas);
        tb = findViewById(R.id.toolbar);
        //quitar el titulo por defecto
        tb.setTitle("");
        // Asignar la toolbar como la barra de acción
        setSupportActionBar(tb);

        //vinculamos el nav_header con el menu
        tb.inflateMenu(R.menu.nav_menu);

        btnStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, VentanaInventario.class);
                startActivity(intent);
                finish();
            }
        });

        btnVentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, VentanaVentas.class);
                startActivity(intent);
                finish();
            }
        });

        btnEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, VentanaEstadisticas.class);
                startActivity(intent);
                finish();
            }
        });

    }

    //Método para cargar el menu
    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);

        // Forzar la visibilidad de los íconos
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);  // Mostrar siempre el ícono
        }

        return true;
    }


    //Método para gestionar las opciones que seleccionemos en el menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemSeleccionado = item.getItemId();

        //Opciones del menu
        if(itemSeleccionado == R.id.nav_inicio){
        }else if(itemSeleccionado == R.id.nav_acerca){
            Intent intent = new Intent(Menu.this, VentanaManualUsuario.class);
            startActivity(intent);
        }else if(itemSeleccionado == R.id.nav_cerrar){
            Intent intent = new Intent(Menu.this, MainActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}