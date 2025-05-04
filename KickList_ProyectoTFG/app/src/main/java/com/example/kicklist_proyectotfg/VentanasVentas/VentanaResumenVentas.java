package com.example.kicklist_proyectotfg.VentanasVentas;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.VentanasEstadisticas.VentanaEstadisticas;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class VentanaResumenVentas extends AppCompatActivity {

    TextView txt_total_ventas, txt_coste_total, txt_ventas_brutas, txt_beneficio, txt_ventas_mes_actual, txt_media_ventas, txt_modelo_mas_vendido, txt_productos_consignados, txt_stock_disponible;
    Button btn_volver_resumen;
    DatabaseHelper db;

    int total_ventas = 0;
    double coste_total = 0.0;
    double ventas_brutas = 0.0;
    double beneficio = 0.0;
    int ventas_mes_actual = 0;
    double media_ventas = 0.0;
    String modelo_mas_vendido = "";
    int productos_consignados = 0;
    int stock_disponible = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_resumen_ventas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Obtener los elementos de la interfaz
        txt_total_ventas = findViewById(R.id.txt_total_ventas);
        txt_coste_total = findViewById(R.id.txt_coste_total);
        txt_ventas_brutas = findViewById(R.id.txt_ventas_brutas);
        txt_beneficio = findViewById(R.id.txt_beneficio);
        txt_ventas_mes_actual = findViewById(R.id.txt_ventas_mes_actual);
        txt_media_ventas = findViewById(R.id.txt_media_ventas);
        txt_modelo_mas_vendido = findViewById(R.id.txt_modelo_mas_vendido);
        txt_productos_consignados = findViewById(R.id.txt_productos_consignados);
        txt_stock_disponible = findViewById(R.id.txt_stock_disponible);

        btn_volver_resumen = findViewById(R.id.btn_volver_resumen);
        db = new DatabaseHelper(this);

        btn_volver_resumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaResumenVentas.this, VentanaEstadisticas.class);
                startActivity(intent);
                finish();
            }
        });

        // Recuperar los datos de la base de datos
        total_ventas = db.totalVentas();
        coste_total = db.costeTotal();
        ventas_brutas = db.ventasBrutas();
        beneficio = db.totalBeneficio();
        ventas_mes_actual = db.ventasMesActual();
        media_ventas = db.mediaVentas();
        media_ventas = Math.round(media_ventas * 100.0) / 100.0;
        modelo_mas_vendido = db.obtenerZapatillaMasVendida();
        productos_consignados = db.productosConsignados();
        stock_disponible = db.stockDisponible();

        // Mostrar los datos en la interfaz
        txt_total_ventas.setText(String.valueOf(total_ventas));
        txt_coste_total.setText(coste_total + "€");
        txt_ventas_brutas.setText(ventas_brutas + "€");
        txt_beneficio.setText(beneficio + "€");
        txt_ventas_mes_actual.setText(String.valueOf(ventas_mes_actual));
        txt_media_ventas.setText(String.valueOf(media_ventas));

        txt_modelo_mas_vendido.setText(modelo_mas_vendido);
        txt_modelo_mas_vendido.setPaintFlags(txt_modelo_mas_vendido.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        txt_modelo_mas_vendido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ventana emergente con el modelo más vendido
                AlertDialog.Builder builder = new AlertDialog.Builder(VentanaResumenVentas.this);
                builder.setTitle("Modelo más vendido");
                String nombre_modelo_mas_vendido = db.obtenerNombrePorSku(modelo_mas_vendido);
                builder.setMessage("El modelo más vendido es: \n" + nombre_modelo_mas_vendido);
                builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
                builder.show();
            }
        });
        // Mostrar los datos en la interfaz
        txt_productos_consignados.setText(String.valueOf(productos_consignados));
        txt_stock_disponible.setText(String.valueOf(stock_disponible));

    }
}