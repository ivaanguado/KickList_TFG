package com.example.kicklist_proyectotfg.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

import java.util.List;

public class AdaptadorModificarStockProducto extends RecyclerView.Adapter<AdaptadorModificarStockProducto.ViewHolder> {

    Context context;
    List<Stock> lista;
    String sku;
    DatabaseHelper db;

    public AdaptadorModificarStockProducto(Context context, List<Stock> lista, String sku) {
        this.context = context;
        this.lista = lista;
        this.sku = sku;
        this.db = new DatabaseHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTalla, txtCantidad, txtTallaNumero;
        Button btnSumar, btnRestar;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inicializar los elementos de la vista
            txtTalla = itemView.findViewById(R.id.txt_modificar_talla);
            txtTallaNumero = itemView.findViewById(R.id.txt_modificar_talla_numero);
            txtCantidad = itemView.findViewById(R.id.txt_modificar_cantidad);
            btnSumar = itemView.findViewById(R.id.btn_aniadir_stock_talla);
            btnRestar = itemView.findViewById(R.id.btn_disminuir_stock_talla);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla la vista del ViewHolder
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_estilo_recycler_view_modificar_stock_producto, parent, false);
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Configura los datos en el ViewHolder
        Stock stock = lista.get(position);

        // Establecer los valores en los elementos de la vista
        holder.txtTalla.setText("Talla:");
        holder.txtTallaNumero.setText(String.valueOf(stock.getTalla()));
        holder.txtCantidad.setText(String.valueOf(stock.getCantidad()));

        holder.btnSumar.setOnClickListener(v -> {
            // Incrementar la cantidad
            stock.setCantidad(stock.getCantidad() + 1);
            holder.txtCantidad.setText(String.valueOf(stock.getCantidad()));
        });

        holder.btnRestar.setOnClickListener(v -> {
            // Disminuir la cantidad
            if (stock.getCantidad() > 0) {
                stock.setCantidad(stock.getCantidad() - 1);
                holder.txtCantidad.setText(String.valueOf(stock.getCantidad()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
