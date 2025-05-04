package com.example.kicklist_proyectotfg.VentanaPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.Persistencia.Preferencias;
import com.example.kicklist_proyectotfg.R;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class VentanaCambiarContrasenia extends AppCompatActivity {

    private EditText txt_pwd1, txt_pwd2;
    private Button btn_contrasenia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_cambiar_contrasenia);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // Buscar los elementos de la interfaz
        txt_pwd1 = findViewById(R.id.txt_pwd1);
        txt_pwd2 = findViewById(R.id.txt_pwd2);
        btn_contrasenia = findViewById(R.id.btn_cambiar_contrasenia);

        // Manejar el clic en el botón de registro
        btn_contrasenia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contrasenia = txt_pwd1.getText().toString().trim();
                String repetirContrasenia = txt_pwd2.getText().toString().trim();

                // Validaciones básicas
                if (contrasenia.isEmpty() || repetirContrasenia.isEmpty()) {
                    Toast.makeText(VentanaCambiarContrasenia.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Verificar si las contraseñas coinciden
                if (!contrasenia.equals(repetirContrasenia)) {
                    Toast.makeText(VentanaCambiarContrasenia.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    txt_pwd1.setText("");
                    txt_pwd2.setText("");
                    return;
                }else if(contrasenia.length()<8){
                    Toast.makeText(VentanaCambiarContrasenia.this, "La contraseña debe tener al menos 8 caracteres", Toast.LENGTH_SHORT).show();
                    txt_pwd1.setText("");
                    txt_pwd2.setText("");
                    return;
                }

                boolean contraseniaCambiada = false;
                // Guardar la nueva contraseña
                contraseniaCambiada = Preferencias.guardarContrasenia(VentanaCambiarContrasenia.this, contrasenia);

                if (contraseniaCambiada) {
                    Toast.makeText(VentanaCambiarContrasenia.this, "Cambio de contraseña exitoso", Toast.LENGTH_SHORT).show();
                    limpiarCampos();
                    // Redirigir al usuario al login
                    Intent intent = new Intent(VentanaCambiarContrasenia.this, MainActivity.class);
                    startActivity(intent);
                    finish();  // Finaliza la actividad de registro para evitar volver atrás
                } else {
                    Toast.makeText(VentanaCambiarContrasenia.this, "Error al cambiar la contraseña. Intente nuevamente.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para limpiar los campos después del registro o error
    private void limpiarCampos() {
        txt_pwd1.setText("");
        txt_pwd2.setText("");
    }
}