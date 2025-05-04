package com.example.kicklist_proyectotfg.Adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kicklist_proyectotfg.R;
import com.example.kicklist_proyectotfg.VentanasVentas.VentanaTienda;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdaptadorProductosConsignados extends BaseAdapter {

    Context context;
    List<Map.Entry<String, Integer>> listaTiendas; // Lista de tiendas con stock
    LayoutInflater inflater;

    public AdaptadorProductosConsignados(Context context, Map<String, Integer> tiendas) {
        this.context = context;
        this.listaTiendas = new ArrayList<>(tiendas.entrySet());
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaTiendas.size();
    }

    @Override
    public Object getItem(int position) {
        return listaTiendas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener la tienda actual
        convertView = inflater.inflate(R.layout.activity_estilo_grid_view_tiendas, null);

        // Configurar los elementos de la vista
        TextView txtTienda = convertView.findViewById(R.id.txt_tienda);
        TextView txtProductos = convertView.findViewById(R.id.txt_prod_tienda);
        ImageView iconoTienda = convertView.findViewById(R.id.imageView5);

        // Establecer los valores en los elementos de la vista
        Map.Entry<String, Integer> entry = listaTiendas.get(position);
        // Obtener el nombre de la tienda y el número de productos
        String nombreTienda = entry.getKey();
        // Obtener el número de productos
        int productos = entry.getValue();

        // Establecer los valores en los elementos de la vista
        txtTienda.setText(nombreTienda);
        txtProductos.setText("Productos consignados: " + productos);
        iconoTienda.setImageResource(R.drawable.tienda);

        convertView.setOnClickListener(v -> {
            // Navegar a la ventana de la tienda
            Intent intent = new Intent(context, VentanaTienda.class);
            // Pasar datos al siguiente activity
            intent.putExtra("nombreTienda", nombreTienda);
            context.startActivity(intent);
        });

        return convertView;
    }
}
