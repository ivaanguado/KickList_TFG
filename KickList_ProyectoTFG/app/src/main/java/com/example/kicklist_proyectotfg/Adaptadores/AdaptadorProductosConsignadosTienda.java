package com.example.kicklist_proyectotfg.Adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.Persistencia.Consigna;
import com.example.kicklist_proyectotfg.Persistencia.Producto;
import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

import java.util.List;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class AdaptadorProductosConsignadosTienda extends RecyclerView.Adapter<AdaptadorProductosConsignadosTienda.ViewHolder> {

    private Context context;
    private List<Consigna> listaConsignas;
    private DatabaseHelper db;

    public AdaptadorProductosConsignadosTienda(Context context, List<Consigna> listaConsignas) {
        this.context = context;
        this.listaConsignas = listaConsignas;
        this.db = new DatabaseHelper(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView nombre, fecha, sku, talla, compra, venta;
        Button btnVender, btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Inicializar los elementos de la vista
            img = itemView.findViewById(R.id.img_prod_consignado);
            nombre = itemView.findViewById(R.id.txt_nombre_prod_consignado);
            fecha = itemView.findViewById(R.id.txt_fecha_prod_consignado);
            sku = itemView.findViewById(R.id.txt_sku_prod_consignado);
            talla = itemView.findViewById(R.id.txt_talla_prod_consignado);
            compra = itemView.findViewById(R.id.txt_compra_prod_consignado);
            venta = itemView.findViewById(R.id.txt_venta_prod_consignado);
            btnVender = itemView.findViewById(R.id.btn_vender_prod_consignado);
            btnEliminar = itemView.findViewById(R.id.btn_eliminar_prod_consignado);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista del ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.activity_estilo_recycler_prods_tienda, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Configura los datos en el ViewHolder
        Consigna c = listaConsignas.get(position);
        Producto p = db.obtenerProductoPorSku(c.getSkuConsigna());

        // Establecer los valores en los elementos de la vista
        holder.nombre.setText(p.getNombre());
        holder.fecha.setText(c.getFechaConsigna());
        holder.sku.setText(c.getSkuConsigna());
        holder.talla.setText("Talla: " + c.getTallaConsigna());
        holder.compra.setText("P. Compra: " + c.getPrecioCompra() + "€");
        holder.venta.setText("P. Venta: " + c.getPrecioVenta() + "€");

        String imagen = p.getImagenProducto();
        // Cargar la imagen
        if (imagen != null && !imagen.isEmpty()) {
            if (imagen.startsWith("/")) {
                // Ruta del almacenamiento interno
                Bitmap bitmap = BitmapFactory.decodeFile(imagen);
                if (bitmap != null) {
                    holder.img.setImageBitmap(bitmap);
                } else {
                    holder.img.setImageResource(R.drawable.imagen_no_disponible);
                }
            } else {
                // Nombre del drawable
                int idImagen = context.getResources().getIdentifier(imagen, "drawable", context.getPackageName());
                if (idImagen != 0) {
                    holder.img.setImageResource(idImagen);
                } else {
                    holder.img.setImageResource(R.drawable.imagen_no_disponible);
                }
            }
        } else {
            holder.img.setImageResource(R.drawable.imagen_no_disponible);
        }

        holder.btnEliminar.setOnClickListener(v -> {
            // Confirmar eliminación
            new AlertDialog.Builder(context)
                    .setTitle("Eliminar producto")
                    .setMessage("¿Deseas eliminar este producto de la consigna?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Recuperar info de la consigna
                        String sku = c.getSkuConsigna();
                        double talla = c.getTallaConsigna();

                        // Obtener stock actual
                        int stockActual = db.obtenerStockPorSku(sku).stream()
                                .filter(s -> s.getTalla() == talla)
                                .mapToInt(Stock::getCantidad)
                                .findFirst()
                                .orElse(0);

                        // Sumar 1 y actualizar stock
                        db.insertarOActualizarStock(sku, talla, stockActual + 1);

                        // Eliminar de consigna
                        db.eliminarConsigna(c.getIdConsigna());

                        // Quitar de la lista y notificar al adaptador
                        listaConsignas.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaConsignas.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });


        holder.btnVender.setOnClickListener(v -> {
            // Confirmar venta
            new AlertDialog.Builder(context)
                    .setTitle("Vender producto")
                    .setMessage("¿Confirmas la venta de este producto?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Insertar en tabla ventas
                        db.insertarVenta(c.getSkuConsigna(), c.getTallaConsigna(), c.getPrecioCompra(), c.getPrecioVenta(), c.getFechaConsigna());
                        // Eliminar de consigna
                        db.eliminarConsigna(c.getIdConsigna());
                        listaConsignas.remove(position);
                        // Quitar de la lista y notificar al adaptador
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, listaConsignas.size());
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return listaConsignas.size();
    }
}