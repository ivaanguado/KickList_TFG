package com.example.kicklist_proyectotfg.Persistencia;

public class Producto {

    private String nombre;
    private String sku;
    private String marca;
    private String genero;
    private double precio;
    private String imagenProducto;

    // Constructor
    public Producto(String nombre, String sku, String marca, String genero, double precio, String imagenProducto) {
        this.nombre = nombre;
        this.sku = sku;
        this.marca = marca;
        this.genero = genero;
        this.precio = precio;
        this.imagenProducto = imagenProducto;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public String getSku() {
        return sku;
    }

    public String getMarca() {
        return marca;
    }

    public String getGenero() {
        return genero;
    }

    public double getPrecio() {
        return precio;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public String toString() {
        return nombre + " - " + sku + " - " + marca + " - " + genero + " - " + precio + " - " + imagenProducto;
    }
}
