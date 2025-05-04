package com.example.kicklist_proyectotfg.Persistencia;

import java.util.ArrayList;
/*
 * Clase Singleton para el carrito temporal
 */
public class CarritoTemporal {
    private static CarritoTemporal instancia;
    private ArrayList<Stock> productos;

    private CarritoTemporal() {
        productos = new ArrayList<>();
    }

    // Método para obtener la instancia del carrito temporal
    public static CarritoTemporal getInstancia() {
        // Si la instancia no existe, se crea una nueva
        if (instancia == null) {
            instancia = new CarritoTemporal();
        }
        return instancia;
    }

    // Método para agregar un producto al carrito
    public void aniadirProducto(Stock producto) {
        productos.add(producto);
    }

    // Método para obtener los productos del carrito
    public ArrayList<Stock> getProductos() {
        return productos;
    }

    // Método para vaciar el carrito
    public void vaciar() {
        productos.clear();
    }

    // Método para obtener los productos seleccionados
    public ArrayList<Stock> getStockSeleccionado() {
        return productos;
    }
}

