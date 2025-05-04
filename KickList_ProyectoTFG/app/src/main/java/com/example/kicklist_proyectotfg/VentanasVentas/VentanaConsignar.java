package com.example.kicklist_proyectotfg.VentanasVentas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;

import java.util.List;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorConsgina;
import com.example.kicklist_proyectotfg.Persistencia.CarritoTemporal;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.Persistencia.Producto;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

public class VentanaConsignar extends AppCompatActivity {

    Button btn_salir_consigna;
    ImageButton btn_envio_consigna;
    TextView txt_cantidad_consigna;
    GridView gridview_productos_consignar;
    DatabaseHelper db;
    List<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_consignar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_salir_consigna = findViewById(R.id.btn_volver_consigna);
        btn_envio_consigna = findViewById(R.id.img_envio_consigna);
        txt_cantidad_consigna = findViewById(R.id.txt_cantidad_consigna);
        gridview_productos_consignar = findViewById(R.id.gridview_prod_consigna);

        db = new DatabaseHelper(this);

        // Obtener los productos de la base de datos
        productos = db.obtenerProductos();
        // Verificar si la lista de productos está vacía
        if (productos.isEmpty()) {
            gridview_productos_consignar.setVisibility(View.GONE);
        } else {
            // Crear el adaptador y asignarlo al GridView
            AdaptadorConsgina adaptador = new AdaptadorConsgina(this, productos);
            gridview_productos_consignar.setAdapter(adaptador);
        }

        btn_salir_consigna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarritoTemporal.getInstancia().vaciar();
                Intent intent = new Intent(VentanaConsignar.this, VentanaVentas.class);
                startActivity(intent);
                finish();
            }
        });

        btn_envio_consigna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaConsignar.this, VentanaCarrito.class);
                intent.putExtra("consigna", true);
                startActivity(intent);
                finish();
            }
        });

        int totalUnidades = 0;
        // Calcular el total de unidades en el carrito
        for (Stock s : CarritoTemporal.getInstancia().getProductos()) {
            totalUnidades += s.getCantidad();
        }
        // Actualizar el TextView con el total de unidades
        txt_cantidad_consigna.setText(String.valueOf(totalUnidades));


    }
}