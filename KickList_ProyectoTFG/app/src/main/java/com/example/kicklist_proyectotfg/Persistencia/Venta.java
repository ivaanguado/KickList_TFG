package com.example.kicklist_proyectotfg.Persistencia;

public class Venta {

    private int idVenta;
    private String skuVenta;
    private double tallaVenta;
    private double compra;
    private double venta;
    private String fecha;
    private double resultado;

    // Constructor
    public Venta(int idVenta, String skuVenta, double tallaVenta, double compra, double venta, String fecha, double resultado) {
        this.idVenta = idVenta;
        this.skuVenta = skuVenta;
        this.tallaVenta = tallaVenta;
        this.compra = compra;
        this.venta = venta;
        this.fecha = fecha;
        this.resultado = resultado;
    }

    // Getters y setters
    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getSkuVenta() {
        return skuVenta;
    }

    public void setSkuVenta(String skuVenta) {
        this.skuVenta = skuVenta;
    }

    public double getTallaVenta() {
        return tallaVenta;
    }

    public void setTallaVenta(double tallaVenta) {
        this.tallaVenta = tallaVenta;
    }

    public double getCompra() {
        return compra;
    }

    public void setCompra(double compra) {
        this.compra = compra;
    }

    public double getVenta() {
        return venta;
    }

    public void setVenta(double venta) {
        this.venta = venta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "idVenta=" + idVenta +
                ", skuVenta='" + skuVenta + '\'' +
                ", tallaVenta=" + tallaVenta +
                ", compra=" + compra +
                ", venta=" + venta +
                ", fecha='" + fecha + '\'' +
                ", resultado=" + resultado +
                '}';
    }
}
