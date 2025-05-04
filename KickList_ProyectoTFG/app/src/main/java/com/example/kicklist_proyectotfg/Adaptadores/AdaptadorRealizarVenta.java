package com.example.kicklist_proyectotfg.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kicklist_proyectotfg.Persistencia.Producto;
import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.VentanasVentas.VentanaStockProductoVender;

import java.util.List;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class AdaptadorRealizarVenta extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Producto> productos;
    DatabaseHelper dbHelper;

    public AdaptadorRealizarVenta(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
        this.inflater = LayoutInflater.from(context);
        this.dbHelper = new DatabaseHelper(context);
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener el producto actual
        Producto producto = productos.get(position);

        int stock = dbHelper.obtenerStock(producto.getSku());

        // Si no hay stock, omite la creación de la vista
        if (stock <= 0) {
            View emptyView = new View(context);
            emptyView.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
            return emptyView;
        }

        convertView = inflater.inflate(R.layout.activity_estilo_grid_view_realizar_venta, null);

        // Configurar los elementos de la vista
        TextView txt_sku = convertView.findViewById(R.id.txt_sku_vender);
        TextView txt_producto = convertView.findViewById(R.id.txt_nombre_vender);
        ImageView imagen_producto = convertView.findViewById(R.id.imgs_productos_disponibles_vender);

        txt_sku.setText(producto.getSku());
        txt_producto.setText(producto.getNombre());

        String imagen = producto.getImagenProducto();
        // Cargar la imagen
        if (imagen != null && !imagen.isEmpty()) {
            if (imagen.startsWith("/")) {
                // Ruta del almacenamiento interno
                Bitmap bitmap = BitmapFactory.decodeFile(imagen);
                if (bitmap != null) {
                    imagen_producto.setImageBitmap(bitmap);
                } else {
                    imagen_producto.setImageResource(R.drawable.imagen_no_disponible);
                }
            } else {
                // Nombre del drawable
                int idImagen = context.getResources().getIdentifier(imagen, "drawable", context.getPackageName());
                if (idImagen != 0) {
                    imagen_producto.setImageResource(idImagen);
                } else {
                    imagen_producto.setImageResource(R.drawable.imagen_no_disponible);
                }
            }
        } else {
            imagen_producto.setImageResource(R.drawable.imagen_no_disponible);
        }

        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, VentanaStockProductoVender.class);
            // Pasar datos al siguiente activity
            intent.putExtra("nombre", producto.getNombre());
            intent.putExtra("sku", producto.getSku());
            context.startActivity(intent);
        });

        return convertView;
    }
}
