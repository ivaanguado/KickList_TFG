package com.example.kicklist_proyectotfg.VentanaPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;

public class SplashScreen extends AppCompatActivity {

    // Declaración de las barras de animación
    TextView b1, b2, b3, b4, b5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicialización de las barras de animación
        b1 = findViewById(R.id.barra1);
        b2 = findViewById(R.id.barra2);
        b3 = findViewById(R.id.barra3);
        b4 = findViewById(R.id.barra4);
        b5 = findViewById(R.id.barra5);

        TextView[] barras = {b1, b2, b3, b4, b5};
        long delay = 300; // Retraso entre animaciones

        for (int i = 0; i < barras.length; i++) {
            Animation animacion = AnimationUtils.loadAnimation(this, R.anim.vertical);
            animacion.setStartOffset(i * delay); // Inicio de la animación por barra
            barras[i].startAnimation(animacion);
        }

        // Cambia a la actividad principal 500 ms después de que la última barra comience su animación
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            finish(); // Finaliza la actividad de la pantalla de inicio
        }, barras.length * delay + 500);
    }
}
