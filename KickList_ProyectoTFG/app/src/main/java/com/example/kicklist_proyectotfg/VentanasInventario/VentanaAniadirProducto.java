package com.example.kicklist_proyectotfg.VentanasInventario;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class VentanaAniadirProducto extends AppCompatActivity {

    EditText edt_nombre, edt_sku, edt_precio;
    ImageView img_producto;
    ImageButton btn_cargar, btn_quitar_img;
    Spinner spn_marcas, spn_genero;
    Button btn_aniadirProducto;
    ImageButton btn_volver_aniadir_prod;

    DatabaseHelper db;

    //Producto producto;
    String marca, nombre, sku;
    String genero;
    double precio;
    String imagen_producto;

    // Variables para la galería
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    // Variables para los spinners
    String[] marcas = {"Nike", "Adidas", "Jordan", "New Balance", "Yeezy", "Asics", "Off-White", "Balenciaga", "Dr. Martens", "Gucci", "Alexander McQueen"};
    String[] generos = {"Hombre", "Mujer", "Unisex"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_aniadir_producto);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);

        // Inicializar vistas
        edt_nombre = findViewById(R.id.edt_nombre);
        edt_sku = findViewById(R.id.edt_sku);
        edt_precio = findViewById(R.id.edt_precio);
        spn_marcas = findViewById(R.id.spn_marcas);
        spn_genero = findViewById(R.id.spn_genero);
        img_producto = findViewById(R.id.img_producto);
        btn_cargar = findViewById(R.id.btn_cargar);
        btn_quitar_img = findViewById(R.id.btn_quitar_img);
        btn_aniadirProducto = findViewById(R.id.btn_aniadirProducto);
        btn_volver_aniadir_prod = findViewById(R.id.btn_volver_aniadir_prod);

        btn_volver_aniadir_prod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaAniadirProducto.this, VentanaInventario.class);
                startActivity(intent);
                finish();
            }
        });

        // Configurar spinners
        ArrayAdapter<String> arrayMarca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, marcas);
        // Asignar el adaptador al spinner
        spn_marcas.setAdapter(arrayMarca);

        // Configurar spinners
        ArrayAdapter<String> arrayGeneros = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, generos);
        // Asignar el adaptador al spinner
        spn_genero.setAdapter(arrayGeneros);

        btn_aniadirProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los datos del producto
                nombre = edt_nombre.getText().toString();
                sku = edt_sku.getText().toString();
                marca = spn_marcas.getSelectedItem().toString();
                genero = spn_genero.getSelectedItem().toString();
                String precioString = edt_precio.getText().toString();
                if (precioString.isEmpty()) {
                    Toast.makeText(VentanaAniadirProducto.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }
                precio = Double.parseDouble(precioString);

                // Validar campos
                if(sku.isEmpty() || nombre.isEmpty() || marca.isEmpty() || genero.isEmpty() || String.valueOf(precio).isEmpty()){
                    Toast.makeText(VentanaAniadirProducto.this, "Rellene todos los campos", Toast.LENGTH_SHORT).show();
                }else{
                    if(sku.length() < 5){
                        Toast.makeText(VentanaAniadirProducto.this, "El SKU no es válido", Toast.LENGTH_SHORT).show();
                    }else if(precio < 0){
                        Toast.makeText(VentanaAniadirProducto.this, "El precio no es válido", Toast.LENGTH_SHORT).show();
                    } else{
                        if (imagen_producto == null) {
                            imagen_producto = "";
                        }

                        // Formatear el nombre
                        nombre = formatearNombre(nombre);

                        // Insertar el producto en la base de datos
                        boolean exito = db.aniadirProducto(nombre, sku, marca, genero, precio, imagen_producto != null ? imagen_producto : null);

                        if (exito) {
                            Toast.makeText(VentanaAniadirProducto.this, "Producto añadido", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(VentanaAniadirProducto.this, "Error al añadir el producto", Toast.LENGTH_SHORT).show();
                        }
                        // Volver a la ventana de inventario
                        Toast.makeText(VentanaAniadirProducto.this, "Producto añadido", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(VentanaAniadirProducto.this, VentanaInventario.class);
                        startActivity(intent);
                    }
                }
            }
        });

        btn_cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirGaleria();
            }
        });


    }

    // Método para abrir la galería
    private void abrirGaleria() {
        // Abre la galería
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Filtra las imágenes
        intent.setType("image/*");
        // Inicia la actividad para seleccionar una imagen
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Si la imagen se seleccionó correctamente
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            try {
                // Guarda la imagen y recibe la ruta
                String rutaGuardada = guardarImagenEnInterno(imageUri);
                imagen_producto = rutaGuardada;
                img_producto.setImageURI(Uri.parse(rutaGuardada)); // Mostrar la imagen guardada

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String guardarImagenEnInterno(Uri uri) throws IOException {
        InputStream inputStream = getContentResolver().openInputStream(uri);
        // Convierte el InputStream en un Bitmap
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

        // Crea un nombre único
        String nombreArchivo = "producto_" + System.currentTimeMillis() + ".png";

        // Ruta en el almacenamiento interno de la app
        File archivo = new File(getFilesDir(), nombreArchivo);

        FileOutputStream fos = new FileOutputStream(archivo);
        // Guarda la imagen en el archivo
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();

        // Devuelve la ruta del archivo
        return archivo.getAbsolutePath();
    }

    // Método para formatear el nombre y poner la primera letra en mayúscula
    public static String formatearNombre(String texto) {
        String[] palabras = texto.toLowerCase().split(" ");
        StringBuilder sb = new StringBuilder();
        // Recorre las palabras y capitaliza la primera letra
        for (String palabra : palabras) {
            // Si la palabra no está vacía, la capitaliza y la agrega al StringBuilder
            if (palabra.length() > 0) {
                sb.append(Character.toUpperCase(palabra.charAt(0)))
                        .append(palabra.substring(1))
                        .append(" ");
            }
        }
        // Devuelve el texto formateado
        return sb.toString().trim();
    }


}