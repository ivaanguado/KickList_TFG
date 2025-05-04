package com.example.kicklist_proyectotfg.Persistencia;

import java.io.Serializable;

public class Consigna implements Serializable {

    int idConsigna;
    String tienda;
    double tallaConsigna;
    String skuConsigna;
    double precioCompra;
    double precioVenta;
    String fechaConsigna;

    // Constructor
    public Consigna(int idConsigna, String tienda, double tallaConsigna, String skuConsigna, double precioCompra, double precioVenta, String fechaConsigna) {
        this.idConsigna = idConsigna;
        this.tienda = tienda;
        this.tallaConsigna = tallaConsigna;
        this.skuConsigna = skuConsigna;
        this.precioCompra = precioCompra;
        this.precioVenta = precioVenta;
        this.fechaConsigna = fechaConsigna;
    }

    // Getters y setters
    public int getIdConsigna() {
        return idConsigna;
    }

    public void setIdConsigna(int idConsigna) {
        this.idConsigna = idConsigna;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public double getTallaConsigna() {
        return tallaConsigna;
    }

    public void setTallaConsigna(double tallaConsigna) {
        this.tallaConsigna = tallaConsigna;
    }

    public String getSkuConsigna() {
        return skuConsigna;
    }

    public void setSkuConsigna(String sku_consigna) {
        this.skuConsigna = skuConsigna;
    }

    public double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public String getFechaConsigna() {
        return fechaConsigna;
    }

    public void setFechaConsigna(String fechaConsigna) {
        this.fechaConsigna = fechaConsigna;
    }

}
