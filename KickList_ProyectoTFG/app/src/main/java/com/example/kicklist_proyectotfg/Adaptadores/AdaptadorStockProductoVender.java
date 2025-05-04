package com.example.kicklist_proyectotfg.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kicklist_proyectotfg.Persistencia.CarritoTemporal;
import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;
import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.Persistencia.Stock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdaptadorStockProductoVender extends RecyclerView.Adapter<AdaptadorStockProductoVender.ViewHolder> {

    Context context;
    List<Stock> lista;
    Map<Double, Integer> cantidadesSeleccionadas; // Mapa para almacenar las cantidades seleccionadas por talla
    DatabaseHelper db;
    String sku;

    public AdaptadorStockProductoVender(Context context, List<Stock> lista, String sku) {
        this.context = context;
        this.lista = lista;
        this.sku = sku;
        this.db = new DatabaseHelper(context);
        this.cantidadesSeleccionadas = new HashMap<>();
        // Inicializar el mapa con las cantidades seleccionadas
        for (Stock s : lista) {
            cantidadesSeleccionadas.put(s.getTalla(), 0);
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTalla, txtCantidad, txtTallaNumero;
        Button btnSumar, btnRestar;

        public ViewHolder(View itemView) {
            super(itemView);
            // Inicializar los elementos de la vista
            txtTalla = itemView.findViewById(R.id.txt_talla_producto_a_vender);
            txtTallaNumero = itemView.findViewById(R.id.txt_talla_numero_a_vender);
            txtCantidad = itemView.findViewById(R.id.txt_cantidad_a_vender);
            btnSumar = itemView.findViewById(R.id.btn_aumentar_venta);
            btnRestar = itemView.findViewById(R.id.btn_disminuir_venta);
        }
    }

    @NonNull
    @Override
    public AdaptadorStockProductoVender.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Infla la vista del ViewHolder
        View vista = LayoutInflater.from(context).inflate(R.layout.activity_estilo_recycler_view_stock_producto_vender, parent, false);
        return new AdaptadorStockProductoVender.ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(AdaptadorStockProductoVender.ViewHolder holder, int position) {
        Stock stock = lista.get(position);
        double talla = stock.getTalla();
        int stockEnBD = stock.getCantidad();

        // Verificamos si ya hay stock de la talla en el carrito
        int stockEnCarrito = 0;
        for (Stock s : CarritoTemporal.getInstancia().getStockSeleccionado()) {
            if (s.getSku().equals(sku) && s.getTalla() == talla) {
                stockEnCarrito += s.getCantidad();
            }
        }

        // Stock real disponible para añadir en esta pantalla
        int stockDisponible = stockEnBD - stockEnCarrito;

        holder.txtTalla.setText("Talla:");
        holder.txtTallaNumero.setText(String.valueOf(talla));
        holder.txtCantidad.setText(String.valueOf(cantidadesSeleccionadas.get(talla)));

        holder.btnSumar.setOnClickListener(v -> {
            int actual = cantidadesSeleccionadas.get(talla);
            // Verificar si hay stock disponible
            if (actual < stockDisponible) {
                cantidadesSeleccionadas.put(talla, actual + 1);
                holder.txtCantidad.setText(String.valueOf(actual + 1));
            }
        });

        holder.btnRestar.setOnClickListener(v -> {
            int actual = cantidadesSeleccionadas.get(talla);
            if (actual > 0) {
                cantidadesSeleccionadas.put(talla, actual - 1);
                holder.txtCantidad.setText(String.valueOf(actual - 1));
            }
        });
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public Map<Double, Integer> getCantidadesSeleccionadas() {
        return cantidadesSeleccionadas;
    }
}