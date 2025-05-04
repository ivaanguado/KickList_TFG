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

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorRealizarVenta;
import com.example.kicklist_proyectotfg.Persistencia.CarritoTemporal;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;

import java.util.List;

import com.example.kicklist_proyectotfg.Persistencia.Producto;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

public class VentanaRealizarVenta extends AppCompatActivity {

    Button btn_salir_realizar_venta;
    ImageButton btn_carrito;
    TextView txt_cantidad_carrito;
    GridView gridview_productos_disponibles_vender;
    DatabaseHelper db;
    List<Producto> productos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_realizar_venta);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener los elementos de la interfaz
        btn_salir_realizar_venta = findViewById(R.id.btn_salir_realizar_venta);
        btn_carrito = findViewById(R.id.btn_carrito);
        txt_cantidad_carrito = findViewById(R.id.txt_cantidad_carrito);
        gridview_productos_disponibles_vender = findViewById(R.id.gridview_productos_disponibles_vender);

        db = new DatabaseHelper(this);

        // Obtener los productos disponibles desde la base de datos y mostrarlos en el GridView
        productos = db.obtenerProductos();
        if (productos.isEmpty()) {
            gridview_productos_disponibles_vender.setVisibility(View.GONE);
        } else {
            // Crear el adaptador y asignarlo al GridView
            AdaptadorRealizarVenta adaptador = new AdaptadorRealizarVenta(this, productos);
            gridview_productos_disponibles_vender.setAdapter(adaptador);
        }

        btn_salir_realizar_venta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Vaciar el carrito y volver a la ventana de ventas
                CarritoTemporal.getInstancia().vaciar();
                Intent intent = new Intent(VentanaRealizarVenta.this, VentanaVentas.class);
                startActivity(intent);
                finish();
            }
        });

        btn_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaRealizarVenta.this, VentanaCarrito.class);
                startActivity(intent);
                finish();
            }
        });

        int totalUnidades = 0;
        // Recorrer la lista de productos del carrito y sumar las unidades
        for (Stock s : CarritoTemporal.getInstancia().getProductos()) {
            totalUnidades += s.getCantidad();
        }
        txt_cantidad_carrito.setText(String.valueOf(totalUnidades));



    }
}