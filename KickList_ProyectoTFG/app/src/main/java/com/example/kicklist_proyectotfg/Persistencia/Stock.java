package com.example.kicklist_proyectotfg.Persistencia;

import java.io.Serializable;

public class Stock implements Serializable {

    private int idStock;
    private String sku;
    private double talla;
    private int cantidad;

    // Constructor
    public Stock(int idStock, String sku, double talla, int cantidad) {
        this.idStock = idStock;
        this.sku = sku;
        this.talla = talla;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public int getIdStock() {
        return idStock;
    }

    public void setId_stock(int id_stock) {
        this.idStock = idStock;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }

    public double getTalla() {
        return talla;
    }

    public void setTalla(double talla) {
        this.talla = talla;
    }

    public String toString() {
        return idStock + " - " + sku + " - " + talla + " - " + cantidad;
    }
}
