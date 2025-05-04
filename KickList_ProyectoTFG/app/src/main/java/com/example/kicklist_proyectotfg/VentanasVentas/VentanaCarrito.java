package com.example.kicklist_proyectotfg.VentanasVentas;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.Adaptadores.AdaptadorCarrito;
import com.example.kicklist_proyectotfg.Persistencia.CarritoTemporal;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;

import java.util.ArrayList;

import com.example.kicklist_proyectotfg.Persistencia.Stock;

public class VentanaCarrito extends AppCompatActivity {

    RecyclerView recycler_carrito;
    Button btn_finalizar_carrito, btn_volver_carrito;
    DatabaseHelper db;
    AdaptadorCarrito adaptador;
    ArrayList<Stock> stockCarrito;
    boolean consigna = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_carrito);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recycler_carrito = findViewById(R.id.recycler_carrito);
        recycler_carrito.setLayoutManager(new LinearLayoutManager(this));

        btn_finalizar_carrito = findViewById(R.id.btn_finalizar_carrito);
        btn_volver_carrito = findViewById(R.id.btn_volver_carrito);

        Intent intent = getIntent();
        // Obtener el valor de la variable
        consigna = intent.getBooleanExtra("consigna", false);

        // Actualizar el texto del botón
        if (consigna) {
            btn_finalizar_carrito.setText("Finalizar Consigna");
        }else{
            btn_finalizar_carrito.setText("Finalizar Venta");
        }

        db = new DatabaseHelper(this);
        // Obtener los productos del carrito
        stockCarrito = new ArrayList<>(CarritoTemporal.getInstancia().getProductos());
        // Verificar si el carrito está vacío
        if (stockCarrito.isEmpty()) {
            recycler_carrito.setVisibility(View.GONE);
        }else{
            // Crear el adaptador y asignarlo al RecyclerView
            adaptador = new AdaptadorCarrito(this, stockCarrito, stockCarrito.get(0).getSku());
            recycler_carrito.setAdapter(adaptador);
        }

        btn_volver_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Volver a la ventana anterior
                if (consigna) {
                    Intent intentVolver = new Intent(VentanaCarrito.this, VentanaConsignar.class);
                    startActivity(intentVolver);
                    finish();
                }else{
                    Intent intentVolver = new Intent(VentanaCarrito.this, VentanaRealizarVenta.class);
                    startActivity(intentVolver);
                    finish();
                }
            }
        });

        btn_finalizar_carrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar si el carrito está vacío
                if (stockCarrito.isEmpty()) {
                    Toast.makeText(VentanaCarrito.this, "El carrito está vacío", Toast.LENGTH_SHORT).show();
                }else{
                    if (consigna) {
                        Intent intentFinalizar = new Intent(VentanaCarrito.this, VentanaRealizarConsigna.class);
                        // Pasar los productos del carrito a la siguiente ventana de consigna
                        intentFinalizar.putExtra("productos_finales", CarritoTemporal.getInstancia().getProductos());
                        startActivity(intentFinalizar);
                        finish();
                    }else{
                        Intent intentFinalizar = new Intent(VentanaCarrito.this, VentanaDetallesVenta.class);
                        // Pasar los productos del carrito a la siguiente ventana de venta
                        intentFinalizar.putExtra("productos_finales", CarritoTemporal.getInstancia().getProductos());
                        startActivity(intentFinalizar);
                        finish();
                    }
                }
            }
        });

    }
}