package com.example.kicklist_proyectotfg.VentanasInventario;

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

public class VentanaInventario extends AppCompatActivity {

    Button btn_ver_stock, btn_modificar_stock, btn_aniadir_producto;
    ImageButton btn_volver_inventario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_inventario);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener los elementos de la interfaz
        btn_ver_stock = findViewById(R.id.btn_ver_stock);
        btn_modificar_stock = findViewById(R.id.btn_modificar_stock);
        btn_aniadir_producto = findViewById(R.id.btn_aniadir_producto);
        btn_volver_inventario = findViewById(R.id.btn_volver_inventario);

        btn_volver_inventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaInventario.this, Menu.class);
                startActivity(intent);
                finish();
            }
        });

        btn_modificar_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaInventario.this, VentanaModificarStock.class);
                startActivity(intent);
                finish();
            }
        });

        btn_ver_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaInventario.this, VentanaVerStock.class);
                startActivity(intent);
                finish();
            }
        });

        btn_aniadir_producto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaInventario.this, VentanaAniadirProducto.class);
                startActivity(intent);
                finish();
            }
        });


    }
}