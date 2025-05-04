package com.example.kicklist_proyectotfg.Adaptadores;

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

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorCarrito extends RecyclerView.Adapter<AdaptadorCarrito.ViewHolder> {

    Context context;
    ArrayList<Stock> stockCarrito;
    LayoutInflater inflater;
    String sku;
    DatabaseHelper db;

    // Constructor
    public AdaptadorCarrito(Context context, ArrayList<Stock> stockCarrito, String sku) {
        this.context = context;
        this.stockCarrito = stockCarrito;
        this.inflater = LayoutInflater.from(context);
        this.sku = sku;
        this.db = new DatabaseHelper(context);
    }

    // Clase interna para el ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTalla, txtCantidad;
        Button btnSumar, btnRestar;
        ImageView imgProducto;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inicializa las vistas del ViewHolder
            txtTalla = itemView.findViewById(R.id.txt_carrito_talla);
            txtCantidad = itemView.findViewById(R.id.txt_cantidad_stock_carrito);
            btnSumar = itemView.findViewById(R.id.btn_aniadir_stock_carrito);
            btnRestar = itemView.findViewById(R.id.btn_disminuir_stock_carrito);
            imgProducto = itemView.findViewById(R.id.img_carrito);
        }
    }

    @NonNull
    @Override
    public AdaptadorCarrito.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista del ViewHolder
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_estilo_recycler_view_carrito, parent, false);
        return new AdaptadorCarrito.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCarrito.ViewHolder holder, int position) {
        // Configura los datos en el ViewHolder
        Stock stock = stockCarrito.get(position);

        holder.txtTalla.setText("Talla: " + stock.getTalla());

        holder.txtCantidad.setText(String.valueOf(stock.getCantidad()));

        int stockDisponible = 0;
        List<Stock> stockBD = db.obtenerStockPorSku(stock.getSku());
        for (Stock s : stockBD) {
            if (s.getTalla() == stock.getTalla()) {
                stockDisponible = s.getCantidad();
                break;
            }
        }

        int finalStockDisponible = stockDisponible;

        holder.btnSumar.setOnClickListener(v -> {
            // Verificar si la cantidad actual es menor que el stock disponible
            int actual = stock.getCantidad();
            if (actual < finalStockDisponible) {
                stock.setCantidad(actual + 1);
                holder.txtCantidad.setText(String.valueOf(stock.getCantidad()));
            }
        });

        holder.btnRestar.setOnClickListener(v -> {
            // Verificar si la cantidad actual es mayor que 0
            if (stock.getCantidad() > 0) {
                stock.setCantidad(stock.getCantidad() - 1);
                holder.txtCantidad.setText(String.valueOf(stock.getCantidad()));
            }
        });

        String imagen = db.obtenerImagenPorSku(stock.getSku());
        // Cargar la imagen
        if (imagen != null && !imagen.isEmpty()) {
            if (imagen.startsWith("/")) {
                // Ruta del almacenamiento interno
                Bitmap bitmap = BitmapFactory.decodeFile(imagen);
                if (bitmap != null) {
                    holder.imgProducto.setImageBitmap(bitmap);
                } else {
                    holder.imgProducto.setImageResource(R.drawable.imagen_no_disponible);
                }
            } else {
                // Nombre del drawable
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
        return stockCarrito.size();
    }
}
