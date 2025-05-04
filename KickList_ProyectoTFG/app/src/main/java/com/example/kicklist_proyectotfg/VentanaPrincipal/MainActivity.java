package com.example.kicklist_proyectotfg.VentanaPrincipal;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.Persistencia.Preferencias;
import com.example.kicklist_proyectotfg.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    // Variables
    Button btn_iniciar, btn_registrarse;
    TextInputEditText txt_pwd;
    TextInputLayout txtInput_pwd;
    int intentos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        btn_iniciar = findViewById(R.id.btn_iniciar_sesion);
        btn_registrarse = findViewById(R.id.btn_registrarse);
        txt_pwd = findViewById(R.id.txt_pwd);
        txtInput_pwd = findViewById(R.id.txtInput_pwd);

        // Acción del botón Iniciar Sesión
        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = txt_pwd.getText().toString().trim();

                // Validar campos vacíos
                if (pwd.isEmpty()) {
                    txtInput_pwd.setError("Campo requerido");
                    return;
                } else {
                    txtInput_pwd.setError(null);
                }

                boolean contraseniaValida = false;
                // Obtener la contraseña guardada
                String contraseniaGuardada = Preferencias.obtenerContrasenia(MainActivity.this);

                // Comparar la contraseña ingresada con la contraseña guardada
                if (contraseniaGuardada.equals(pwd)) {
                    contraseniaValida = true;
                }

                if (contraseniaValida) {
                    // Inicio de sesión exitoso
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    startActivity(intent);
                    finish();  // Finaliza la actividad de login para no volver con el botón de atrás
                } else {
                    txtInput_pwd.setError("Contraseña incorrecta");
                    intentos++;

                    if (intentos >= 3) {
                        // Mostrar Snackbar después de 3 intentos fallidos
                        Snackbar snackbar = Snackbar.make(v, "¿No te acuerdas de la contraseña? ¡Cámbiala!", Snackbar.LENGTH_LONG)
                                .setBackgroundTint(Color.parseColor("#4169E1"))
                                .setTextColor(Color.WHITE)
                                .setActionTextColor(Color.WHITE);

                        snackbar.setAction("Cambiar contraseña", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, VentanaCambiarContrasenia.class);
                                startActivity(intent);
                            }
                        });
                        snackbar.show();
                    }
                }
            }
        });

        // Acción del botón Registrarse
        btn_registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VentanaCambiarContrasenia.class);
                startActivity(intent);
            }
        });


    }
}
