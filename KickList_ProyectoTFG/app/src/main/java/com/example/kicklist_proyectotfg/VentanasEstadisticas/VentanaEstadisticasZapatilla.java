package com.example.kicklist_proyectotfg.VentanasEstadisticas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class VentanaEstadisticasZapatilla extends AppCompatActivity {

    DatabaseHelper db;
    Button btn_volver_estadisticas_zapatilla;
    ImageView img_estadisticas_zapatilla;
    TextView txt_nombre_zapatilla, txt_sku_zapatilla, txt_total_beneficio_zapatilla, txt_total_ventas_zapatilla, txt_talla_mas_vendida, txt_mes_ventas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_estadisticas_zapatilla);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        // Obtener los elementos de la interfaz
        btn_volver_estadisticas_zapatilla = findViewById(R.id.btn_volver_estadisticas_zapatilla);
        img_estadisticas_zapatilla = findViewById(R.id.img_estadisticas_zapatilla);
        txt_nombre_zapatilla = findViewById(R.id.txt_nombre_zapatilla);
        txt_sku_zapatilla = findViewById(R.id.txt_sku_zapatilla);
        txt_total_beneficio_zapatilla = findViewById(R.id.txt_total_beneficio_zapatilla);
        txt_total_ventas_zapatilla = findViewById(R.id.txt_total_ventas_zapatilla);
        txt_talla_mas_vendida = findViewById(R.id.txt_talla_mas_vendida);
        txt_mes_ventas = findViewById(R.id.txt_mes_ventas);

        btn_volver_estadisticas_zapatilla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaEstadisticasZapatilla.this, VentanaConsultarDatosZapatilla.class);
                startActivity(intent);
                finish();
            }
        });

        // Obtener los datos de la zapatilla
        String nombre = getIntent().getStringExtra("nombre");
        String sku = getIntent().getStringExtra("sku");
        String imagen = getIntent().getStringExtra("imagen");

        // Mostrar los datos de la zapatilla
        txt_nombre_zapatilla.setText(nombre);
        txt_sku_zapatilla.setText("SKU: " + sku);

        // Mostrar la imagen de la zapatilla
        if (imagen != null && !imagen.isEmpty()) {
            if (imagen.startsWith("/")) {
                // Ruta del almacenamiento interno
                Bitmap bitmap = BitmapFactory.decodeFile(imagen);
                if (bitmap != null) {
                    img_estadisticas_zapatilla.setImageBitmap(bitmap);
                } else {
                    img_estadisticas_zapatilla.setImageResource(R.drawable.imagen_no_disponible);
                }
            } else {
                // Nombre del drawable
                int idImagen = VentanaEstadisticasZapatilla.this.getResources().getIdentifier(imagen, "drawable", VentanaEstadisticasZapatilla.this.getPackageName());
                if (idImagen != 0) {
                    img_estadisticas_zapatilla.setImageResource(idImagen);
                } else {
                    img_estadisticas_zapatilla.setImageResource(R.drawable.imagen_no_disponible);
                }
            }
        } else {
            img_estadisticas_zapatilla.setImageResource(R.drawable.imagen_no_disponible);
        }

        // Mostrar los datos de la zapatilla
        double beneficio = db.obtenerBeneficio(sku);
        int totalVentas = db.obtenerTotalVentas(sku);
        String tallaMasVendida = db.obtenerTallaMasVendida(sku);
        String mesMasVentas = db.obtenerMesMasVentas(sku);

        txt_total_beneficio_zapatilla.setText("Beneficio: " + beneficio + "€");
        txt_total_ventas_zapatilla.setText("Total ventas: " + totalVentas);
        txt_talla_mas_vendida.setText("Talla más vendida: " + tallaMasVendida);
        txt_mes_ventas.setText("Mes más ventas: " + mesMasVentas);

    }
}