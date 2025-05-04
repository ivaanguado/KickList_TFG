package com.example.kicklist_proyectotfg.Adaptadores;

import android.content.Context;
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
import com.example.kicklist_proyectotfg.Persistencia.Venta;

import java.util.ArrayList;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class AdaptadorHistorialVentas extends BaseAdapter {

    Context context;
    ArrayList<Venta> ventas;
    DatabaseHelper db;
    LayoutInflater inflater;

    public AdaptadorHistorialVentas(Context context, ArrayList<Venta> ventas) {
        this.context = context;
        this.ventas = ventas;
        this.db = new DatabaseHelper(context);
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return ventas.size();
    }

    @Override
    public Object getItem(int position) {
        return ventas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener la venta actual
        Venta venta = ventas.get(position);

        convertView = inflater.inflate(R.layout.activity_estilo_grid_view_historial_ventas, parent,false);

        // Configurar los elementos de la vista
        TextView txt_nombre = convertView.findViewById(R.id.txt_nombre_venta);
        TextView txt_sku = convertView.findViewById(R.id.txt_sku_venta);
        TextView txt_talla = convertView.findViewById(R.id.txt_talla_venta);
        TextView txt_precio_compra = convertView.findViewById(R.id.txt_precio_compra);
        TextView txt_precio_venta = convertView.findViewById(R.id.txt_precio_venta);
        TextView txt_fecha = convertView.findViewById(R.id.txt_fecha_venta);
        ImageView imagen_producto = convertView.findViewById(R.id.img_prod_venta);

        Producto producto = db.obtenerProductoPorSku(venta.getSkuVenta());

        // Establecer los valores en los elementos de la vista
        txt_nombre.setText(producto.getNombre());
        txt_sku.setText(venta.getSkuVenta());
        txt_talla.setText("Talla: " + venta.getTallaVenta());
        txt_precio_compra.setText("Compra: " + venta.getCompra() + "€");
        txt_precio_venta.setText("Venta: " + venta.getVenta() + "€");
        txt_fecha.setText(venta.getFecha());

        // Cargar la imagen
        String nombreImagen = producto.getImagenProducto();
        if (nombreImagen != null && !nombreImagen.isEmpty()) {
            if (nombreImagen.startsWith("/")) {
                // Ruta del almacenamiento interno
                Bitmap bitmap = BitmapFactory.decodeFile(nombreImagen);
                if (bitmap != null) {
                    imagen_producto.setImageBitmap(bitmap);
                } else {
                    imagen_producto.setImageResource(R.drawable.imagen_no_disponible);
                }
            } else {
                // Nombre del drawable
                int idImagen = context.getResources().getIdentifier(nombreImagen, "drawable", context.getPackageName());
                if (idImagen != 0) {
                    imagen_producto.setImageResource(idImagen);
                } else {
                    imagen_producto.setImageResource(R.drawable.imagen_no_disponible);
                }
            }
        } else {
            imagen_producto.setImageResource(R.drawable.imagen_no_disponible);
        }

        return convertView;
    }
}
