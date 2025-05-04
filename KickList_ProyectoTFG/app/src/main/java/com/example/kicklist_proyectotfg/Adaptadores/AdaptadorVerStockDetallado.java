package com.example.kicklist_proyectotfg.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

import java.util.List;

public class AdaptadorVerStockDetallado extends RecyclerView.Adapter<AdaptadorVerStockDetallado.ViewHolder> {

    Context context;
    List<Stock> lista;
    String sku;
    DatabaseHelper db;

    public AdaptadorVerStockDetallado(Context context, List<Stock> lista, String sku) {
        this.context = context;
        this.lista = lista;
        this.sku = sku;
        this.db = new DatabaseHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCantidad, txtTalla, txtTallaNumero;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inicializar los elementos de la vista
            txtCantidad = itemView.findViewById(R.id.txt_ver_stock_cantidad);
            txtTalla = itemView.findViewById(R.id.txt_ver_stock_talla);
            txtTallaNumero = itemView.findViewById(R.id.txt_ver_stock_talla_numero);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Infla la vista del ViewHolder
        View view = LayoutInflater.from(context).inflate(R.layout.activity_estilo_recycler_view_ver_stock_detallado, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorVerStockDetallado.ViewHolder holder, int position) {
        // Obtener el stock actual
        Stock stock = lista.get(position);

        holder.txtTallaNumero.setText(String.valueOf(stock.getTalla()));
        holder.txtCantidad.setText(String.valueOf(stock.getCantidad()));
        holder.txtTalla.setText("Talla:");
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
