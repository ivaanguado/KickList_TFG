package com.example.kicklist_proyectotfg.Adaptadores;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

import java.util.ArrayList;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class AdaptadorDetallesVenta extends RecyclerView.Adapter<AdaptadorDetallesVenta.ViewHolder> {

    Context context;
    ArrayList<Stock> stockExpandido;
    DatabaseHelper db;

    public AdaptadorDetallesVenta(Context context, ArrayList<Stock> stockSeleccionado) {
        this.context = context;
        this.stockExpandido = new ArrayList<>();
        this.db = new DatabaseHelper(context);

        // Agregar los elementos al stock expandido
        for (Stock s : stockSeleccionado) {
            for (int i = 0; i < s.getCantidad(); i++) {
                this.stockExpandido.add(new Stock(0, s.getSku(), s.getTalla(), 1));
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTalla;
        EditText edtCompra, edtVenta;
        ImageView imgProducto;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTalla = itemView.findViewById(R.id.txt_talla_detalles);
            edtCompra = itemView.findViewById(R.id.edt_compra_detalles);
            edtVenta = itemView.findViewById(R.id.edt_venta_detalles);
            imgProducto = itemView.findViewById(R.id.img_detalles);
        }
    }

    @NonNull
    @Override
    public AdaptadorDetallesVenta.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista del ViewHolder
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_estilo_recycler_view_detalles_venta, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Configura los datos en el ViewHolder
        Stock item = stockExpandido.get(position);
        holder.txtTalla.setText("Talla: " + item.getTalla());

        // Cargar la imagen
        String imagen = db.obtenerImagenPorSku(item.getSku());
        if (imagen != null && !imagen.isEmpty()) {
            if (imagen.startsWith("/")) {
                Bitmap bitmap = BitmapFactory.decodeFile(imagen);
                if (bitmap != null) {
                    holder.imgProducto.setImageBitmap(bitmap);
                } else {
                    holder.imgProducto.setImageResource(R.drawable.imagen_no_disponible);
                }
            } else {
                int idImagen = context.getResources().getIdentifier(imagen, "drawable", context.getPackageName());
                if (idImagen != 0) {
                    holder.imgProducto.setImageResource(idImagen);
                } else {
                    holder.imgProducto.setImageResource(R.drawable.imagen_no_disponible);
                }
            }
        } else {
            holder.imgProducto.setImageResource(R.drawable.imagen_no_disponible);
        }
    }

    @Override
    public int getItemCount() {
        return stockExpandido.size();
    }
}
