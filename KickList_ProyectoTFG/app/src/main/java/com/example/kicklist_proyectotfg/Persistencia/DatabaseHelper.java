package com.example.kicklist_proyectotfg.Persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Constantes para la base de datos
    private static final String DATABASE_NAME = "KickList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLA_PRODUCTOS = "Productos";
    private static final String COLUMN_NOMBRE = "nombre";
    private static final String COLUMN_SKU = "sku";
    private static final String COLUMN_MARCA = "marca";
    private static final String COLUMN_GENERO = "genero";
    private static final String COLUMN_PRECIO = "precio";
    private static final String COLUMN_IMAGEN_PRODUCTO = "imagenProducto";

    private static final String TABLA_STOCK = "Stock";
    private static final String COLUMN_ID_STOCK = "idStock";
    private static final String COLUMN_TALLA = "talla";
    private static final String COLUMN_CANTIDAD = "cantidad";

    private static final String TABLA_VENTAS = "Ventas";
    private static final String COLUMN_ID_VENTA = "id_venta";
    private static final String COLUMN_TALLA_VENTA = "tallaVenta";
    private static final String COLUMN_COMPRA = "compra";
    private static final String COLUMN_VENTA = "venta";
    private static final String COLUMN_FECHA = "fecha";
    private static final String COLUMN_RESULTADO = "resultado";

    private static final String TABLE_CONSIGNA = "Consigna";
    private static final String COLUMN_ID_CONSIGNA = "idConsigna";
    private static final String COLUMN_TIENDA = "tienda";
    private static final String COLUMN_TALLA_CONSIGNA = "tallaConsigna";
    private static final String COLUMN_PRECIO_COMPRA = "precioCompra";
    private static final String COLUMN_PRECIO_VENTA = "precioVenta";
    private static final String COLUMN_SKU_CONSIGNA = "skuConsigna";
    private static final String COLUMN_FECHA_CONSIGNA = "fechaConsigna";


    // Constructor de la clase
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla Productos
        String createTablaProductos = "CREATE TABLE " + TABLA_PRODUCTOS + " (" +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_SKU + " TEXT NOT NULL PRIMARY KEY, " +
                COLUMN_MARCA + " TEXT NOT NULL, " +
                COLUMN_GENERO + " TEXT NOT NULL, " +
                COLUMN_PRECIO + " REAL NOT NULL, " +
                COLUMN_IMAGEN_PRODUCTO + " TEXT)";
        db.execSQL(createTablaProductos);

        // Tabla Stock
        String createTablaStock = "CREATE TABLE " + TABLA_STOCK + " (" +
                COLUMN_ID_STOCK + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SKU + " TEXT NOT NULL, " +
                COLUMN_TALLA + " REAL NOT NULL, " +
                COLUMN_CANTIDAD + " INTEGER NOT NULL DEFAULT 0, " +
                "FOREIGN KEY (" + COLUMN_SKU + ") REFERENCES " + TABLA_PRODUCTOS + "(" + COLUMN_SKU + ") ON DELETE CASCADE)";
        db.execSQL(createTablaStock);

        // Tabla Ventas
        String createTablaVentas = "CREATE TABLE " + TABLA_VENTAS + " (" +
                COLUMN_ID_VENTA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_SKU + " TEXT NOT NULL, " +
                COLUMN_TALLA_VENTA + " REAL NOT NULL, " +
                COLUMN_COMPRA + " REAL NOT NULL, " +
                COLUMN_VENTA + " REAL NOT NULL, " +
                COLUMN_FECHA + " TEXT NOT NULL, " +
                COLUMN_RESULTADO + " REAL NOT NULL, " +
                "FOREIGN KEY (" + COLUMN_SKU + ") REFERENCES " + TABLA_PRODUCTOS + "(" + COLUMN_SKU + ") ON DELETE CASCADE)";
        db.execSQL(createTablaVentas);

        // Tabla Consigna
        String createTablaConsigna = "CREATE TABLE " + TABLE_CONSIGNA + " (" +
                COLUMN_ID_CONSIGNA + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TIENDA + " TEXT NOT NULL, " +
                COLUMN_TALLA_CONSIGNA + " REAL NOT NULL, " +
                COLUMN_PRECIO_COMPRA + " REAL NOT NULL, " +
                COLUMN_PRECIO_VENTA + " REAL NOT NULL, " +
                COLUMN_SKU_CONSIGNA + " TEXT NOT NULL, " +
                COLUMN_FECHA_CONSIGNA + " TEXT NOT NULL)";
        db.execSQL(createTablaConsigna);

        // Insertar productos por defecto
        db.execSQL("INSERT INTO " + TABLA_PRODUCTOS + " VALUES " +
                "('Jordan 1 Retro Low OG Travis Scott Medium Olive', 'DM7866-200', 'Jordan', 'Hombre', 700, 'travis_medium_olive')," +
                "('Jordan 1 Retro Low OG Travis Scott Reverse Mocha', 'DM7866-162', 'Jordan', 'Hombre', 1300, 'travis')," +
                "('Adidas Campus x Bad Bunny Chalky Brown', 'ID2529', 'Adidas', 'Hombre', 160, 'campus_bad_bunny_chalky_brown')," +
                "('Adidas The Last Campus x Bad Bunny', 'ID2534', 'Adidas', 'Hombre', 160, 'the_last_campus_bad_bunny')," +
                "('Adidas Yeezy Slide Onyx', 'HQ6448', 'Yeezy', 'Hombre', 70, 'yeezy_slide_onyx')," +
                "('Adidas Yeezy Boost 350 V2 Carbon Beluga', 'HQ7045', 'Yeezy', 'Hombre', 230, 'yeezy_carbon_beluga')," +
                "('Adidas Yeezy Boost 350 V2 Bone', 'HQ6316', 'Yeezy', 'Hombre', 230, 'yeezy_bone')," +
                "('Nike SB Dunk Low Futura Laboratories', 'HF6061-400', 'Nike', 'Hombre', 130, 'dunk_futura_laboratories')," +
                "('Nike SB Dunk Low Rayssa Leal', 'FZ5251-001', 'Nike', 'Hombre', 130, 'dunk_rayssa_leal')," +
                "('Nike SB Dunk Low Born X Raised One Block At A Time', 'FN7819-400', 'Nike', 'Hombre', 130, 'dunk_born_raised')," +
                "('Jordan 4 Retro SB Navy', 'DR5415-100', 'Jordan', 'Hombre', 230, 'jordan_retro_sb_navy')," +
                "('Jordan 4 Retro SB Pine Green', 'DR5415-103', 'Jordan', 'Hombre', 230, 'jordan_retro_sb_pine_green')," +
                "('Nike Air Force 1 Low Drake NOCTA Certified Lover Boy', 'CZ8065-100', 'Nike', 'Hombre', 160, 'air_force_drake_nocta')," +
                "('Nike Hot Step 2 Drake NOCTA Black', 'DZ72293-001', 'Nike', 'Hombre', 200, 'hot_step_drake_nocta')");

        // Insertar stock por defecto
        db.execSQL("INSERT INTO " + TABLA_STOCK + " (" + COLUMN_SKU + ", " + COLUMN_TALLA + ", " + COLUMN_CANTIDAD + ") VALUES " +
                "('DM7866-200', 36.0, 5)," +
                "('DM7866-200', 37.5, 3)," +
                "('DM7866-200', 39.0, 7)," +
                "('DM7866-200', 42.0, 2)," +
                "('DM7866-200', 44.5, 1)," +

                "('DM7866-162', 36.0, 2)," +
                "('DM7866-162', 37.5, 6)," +
                "('DM7866-162', 39.0, 4)," +
                "('DM7866-162', 42.0, 6)," +
                "('DM7866-162', 44.5, 8)," +

                "('ID2529', 36.0, 9)," +
                "('ID2529', 37.5, 1)," +
                "('ID2529', 39.0, 3)," +
                "('ID2529', 42.0, 7)," +
                "('ID2529', 44.5, 5)," +

                "('ID2534', 36.0, 2)," +
                "('ID2534', 37.5, 6)," +
                "('ID2534', 39.0, 4)," +
                "('ID2534', 42.0, 3)," +
                "('ID2534', 44.5, 8)");

        // Insertar ventas por defecto
        db.execSQL("INSERT INTO " + TABLA_VENTAS + " (" +
                COLUMN_SKU + ", " + COLUMN_TALLA_VENTA + ", " +
                COLUMN_COMPRA + ", " + COLUMN_VENTA + ", " +
                COLUMN_FECHA + ", " + COLUMN_RESULTADO + ") VALUES " +

                "('ID2534', 42.0, 201.5, 335.96, '2024-07-25', 134.46)," +
                "('ID2529', 44.5, 172.78, 314.62, '2024-07-14', 141.84)," +
                "('ID2534', 37.5, 181.55, 267.86, '2024-06-10', 86.31)," +
                "('ID2529', 36.0, 197.78, 261.16, '2025-02-08', 63.38)," +
                "('DM7866-162', 39.0, 221.95, 371.04, '2023-07-23', 149.09)," +
                "('ID2529', 36.0, 226.58, 335.18, '2025-01-04', 108.6)," +
                "('ID2534', 42.0, 198.68, 304.69, '2023-06-18', 106.01)," +
                "('DM7866-162', 42.0, 240.0, 390.0, '2025-03-15', 150.0)," +
                "('ID2529', 39.0, 170.0, 250.0, '2025-04-12', 80.0)," +
                "('DM7866-200', 37.5, 180.0, 300.0, '2023-05-20', 120.0)," +
                "('ID2534', 39.0, 175.0, 295.0, '2025-02-22', 120.0)," +
                "('DM7866-200', 42.0, 210.0, 330.0, '2025-01-30', 120.0)," +
                "('DM7866-162', 36.0, 190.0, 320.0, '2023-06-05', 130.0)," +
                "('ID2529', 44.5, 185.0, 310.0, '2025-03-10', 125.0)," +
                "('ID2534', 36.0, 160.0, 260.0, '2025-04-25', 100.0)," +
                "('DM7866-162', 37.5, 220.0, 340.0, '2025-02-18', 120.0)," +
                "('ID2529', 42.0, 175.0, 285.0, '2024-07-01', 110.0)," +
                "('ID2534', 44.5, 210.0, 345.0, '2024-07-10', 135.0)," +
                "('DM7866-200', 36.0, 200.0, 320.0, '2025-03-28', 120.0)," +
                "('DM7866-162', 39.0, 230.0, 360.0, '2023-06-22', 130.0)," +
                "('ID2529', 37.5, 165.0, 270.0, '2025-01-15', 105.0)," +
                "('ID2534', 39.0, 180.0, 295.0, '2025-04-30', 115.0)," +
                "('DM7866-200', 44.5, 195.0, 315.0, '2023-05-17', 120.0)," +
                "('DM7866-162', 42.0, 215.0, 335.0, '2025-03-09', 120.0)," +
                "('ID2529', 36.0, 170.0, 280.0, '2023-06-12', 110.0)," +
                "('ID2534', 37.5, 185.0, 300.0, '2025-02-03', 115.0)," +
                "('DM7866-162', 36.0, 200.0, 320.0, '2025-01-20', 120.0)," +
                "('ID2529', 39.0, 180.0, 290.0, '2025-03-27', 110.0)," +
                "('ID2534', 36.0, 160.0, 265.0, '2025-04-08', 105.0)," +
                "('DM7866-200', 42.0, 190.0, 310.0, '2024-05-30', 120.0)," +
                "('DM7866-162', 39.0, 210.0, 330.0, '2024-07-18', 120.0)," +
                "('ID2529', 44.5, 175.0, 285.0, '2025-01-28', 110.0)," +
                "('ID2534', 42.0, 205.0, 325.0, '2023-06-02', 120.0)," +
                "('DM7866-200', 37.5, 180.0, 290.0, '2025-02-14', 110.0)," +
                "('DM7866-162', 36.0, 195.0, 310.0, '2025-03-21', 115.0)," +
                "('ID2529', 39.0, 170.0, 275.0, '2025-04-16', 105.0)," +
                "('ID2534', 36.0, 165.0, 270.0, '2023-05-10', 105.0)," +
                "('DM7866-200', 44.5, 200.0, 320.0, '2023-06-28', 120.0)," +
                "('DM7866-162', 42.0, 215.0, 335.0, '2023-07-04', 120.0);"
        );

        // Insertar ventas por defecto
        db.execSQL("INSERT INTO " + TABLA_VENTAS + " (" +
                COLUMN_SKU + ", " + COLUMN_TALLA_VENTA + ", " +
                COLUMN_COMPRA + ", " + COLUMN_VENTA + ", " +
                COLUMN_FECHA + ", " + COLUMN_RESULTADO + ") VALUES " +

                "('ID2529', 36.0, 172.55, 283.37, '2025-01-01', 110.82)," +
                "('DM7866-200', 39.0, 186.73, 335.79, '2025-01-04', 149.06)," +
                "('ID2534', 37.5, 187.1, 289.31, '2025-01-07', 102.21)," +
                "('ID2534', 42.0, 205.67, 344.57, '2025-01-10', 138.9)," +
                "('DM7866-162', 42.0, 200.93, 318.43, '2025-01-13', 117.5)," +
                "('DM7866-162', 36.0, 169.23, 300.25, '2025-01-16', 131.02)," +
                "('ID2529', 42.0, 162.0, 304.0, '2025-01-19', 142.0)," +
                "('DM7866-162', 39.0, 218.26, 334.58, '2025-01-22', 116.32)," +
                "('DM7866-162', 39.0, 195.19, 290.86, '2025-01-25', 95.67)," +
                "('DM7866-200', 36.0, 167.36, 288.0, '2025-01-28', 120.64)," +
                "('ID2529', 42.0, 172.47, 321.17, '2025-01-31', 148.7)," +
                "('ID2534', 37.5, 176.94, 314.79, '2025-02-03', 137.85)," +
                "('DM7866-162', 42.0, 161.17, 248.71, '2025-02-06', 87.54)," +
                "('ID2529', 37.5, 186.14, 304.58, '2025-02-09', 118.44)," +
                "('DM7866-200', 36.0, 188.4, 332.66, '2025-02-12', 144.26)," +
                "('ID2534', 44.5, 169.6, 261.91, '2025-02-15', 92.31)," +
                "('DM7866-200', 36.0, 176.66, 295.43, '2023-02-18', 118.77)," +
                "('DM7866-200', 44.5, 190.52, 304.31, '2024-02-21', 113.79)," +
                "('ID2534', 39.0, 229.07, 354.81, '2023-02-24', 125.74)," +
                "('ID2529', 37.5, 193.91, 330.01, '2024-02-27', 136.1)," +
                "('ID2529', 36.0, 161.99, 267.04, '2025-03-02', 105.05)," +
                "('DM7866-162', 37.5, 190.58, 330.96, '2025-03-05', 140.38)," +
                "('ID2534', 36.0, 163.58, 280.21, '2025-03-08', 116.63)," +
                "('ID2534', 36.0, 200.97, 341.38, '2025-03-11', 140.41)," +
                "('DM7866-200', 42.0, 181.88, 296.94, '2025-03-14', 115.06)," +
                "('DM7866-162', 42.0, 195.52, 334.81, '2025-03-17', 139.29)," +
                "('DM7866-162', 36.0, 189.15, 334.4, '2025-03-20', 145.25)," +
                "('ID2529', 39.0, 228.95, 374.77, '2025-03-23', 145.82)," +
                "('DM7866-200', 42.0, 193.73, 343.41, '2025-03-26', 149.68)," +
                "('DM7866-162', 39.0, 220.67, 355.12, '2025-03-29', 134.45)," +
                "('DM7866-162', 36.0, 201.89, 347.91, '2025-04-01', 146.02)," +
                "('ID2534', 36.0, 221.2, 361.2, '2025-04-04', 140.0)," +
                "('ID2534', 37.5, 186.53, 316.56, '2025-04-07', 130.03)," +
                "('ID2534', 39.0, 211.53, 352.4, '2025-04-10', 140.87)," +
                "('ID2529', 42.0, 228.06, 368.49, '2025-04-13', 140.43)," +
                "('DM7866-162', 42.0, 205.69, 338.48, '2025-04-16', 132.79)," +
                "('DM7866-162', 37.5, 226.66, 369.31, '2023-04-19', 142.65)," +
                "('ID2529', 36.0, 199.25, 347.96, '2024-04-22', 148.71)," +
                "('DM7866-200', 42.0, 205.38, 350.19, '2023-04-25', 144.81)," +
                "('ID2534', 42.0, 201.15, 350.44, '2024-04-28', 149.29)," +
                "('DM7866-200', 39.0, 180.31, 308.36, '2023-05-01', 128.05)," +
                "('DM7866-162', 44.5, 162.67, 309.09, '2024-05-04', 146.42)," +
                "('ID2534', 36.0, 203.76, 340.66, '2023-05-07', 136.9)," +
                "('DM7866-162', 39.0, 165.82, 294.45, '2024-05-10', 128.63)," +
                "('DM7866-200', 39.0, 177.3, 303.56, '2023-05-13', 126.26)," +
                "('ID2529', 44.5, 177.25, 328.98, '2024-05-16', 151.73)," +
                "('DM7866-200', 44.5, 172.03, 309.34, '2023-05-19', 137.31)," +
                "('DM7866-162', 39.0, 181.92, 323.92, '2023-05-22', 142.0)," +
                "('ID2534', 42.0, 203.61, 352.38, '2024-05-25', 148.77)," +
                "('ID2529', 39.0, 193.11, 343.91, '2024-05-28', 150.8);");

        // Insertar ventas por defecto
        db.execSQL("INSERT INTO " + TABLA_VENTAS + " (" +
                COLUMN_SKU + ", " + COLUMN_TALLA_VENTA + ", " +
                COLUMN_COMPRA + ", " + COLUMN_VENTA + ", " +
                COLUMN_FECHA + ", " + COLUMN_RESULTADO + ") VALUES " +

                "('DM7866-200', 36.0, 200.0, 310.0, '2025-01-11', 110.0)," +
                "('ID2529', 37.5, 165.0, 275.0, '2025-01-25', 110.0)," +
                "('DM7866-162', 42.0, 215.0, 340.0, '2025-02-09', 125.0)," +
                "('ID2534', 36.0, 180.0, 295.0, '2025-02-20', 115.0)," +
                "('DM7866-200', 39.0, 190.0, 300.0, '2024-03-05', 110.0)," +
                "('ID2529', 39.0, 170.0, 280.0, '2023-03-21', 110.0)," +
                "('DM7866-162', 36.0, 190.0, 305.0, '2023-04-06', 115.0)," +
                "('ID2534', 39.0, 185.0, 290.0, '2024-04-14', 105.0)," +
                "('DM7866-200', 42.0, 210.0, 330.0, '2023-05-08', 120.0)," +
                "('ID2529', 36.0, 175.0, 285.0, '2024-05-24', 110.0)," +

                "('DM7866-162', 39.0, 225.0, 345.0, '2024-06-04', 120.0)," +
                "('ID2534', 42.0, 195.0, 305.0, '2023-06-19', 110.0)," +
                "('DM7866-200', 37.5, 185.0, 295.0, '2023-07-03', 110.0)," +
                "('ID2529', 44.5, 180.0, 290.0, '2024-07-22', 110.0)," +
                "('DM7866-162', 36.0, 200.0, 310.0, '2023-08-01', 110.0)," +
                "('ID2534', 37.5, 185.0, 300.0, '2024-08-15', 115.0)," +
                "('DM7866-200', 44.5, 210.0, 325.0, '2023-08-27', 115.0)," +
                "('ID2529', 42.0, 175.0, 280.0, '2023-09-10', 105.0)," +
                "('DM7866-162', 39.0, 220.0, 340.0, '2024-09-18', 120.0)," +
                "('ID2534', 36.0, 170.0, 280.0, '2024-09-30', 110.0)," +

                "('DM7866-200', 42.0, 200.0, 320.0, '2023-10-12', 120.0)," +
                "('ID2529', 37.5, 165.0, 275.0, '2024-10-25', 110.0)," +
                "('DM7866-162', 42.0, 215.0, 335.0, '2023-11-04', 120.0)," +
                "('ID2534', 39.0, 190.0, 310.0, '2023-11-13', 120.0)," +
                "('DM7866-200', 36.0, 180.0, 290.0, '2024-11-27', 110.0)," +
                "('ID2529', 36.0, 175.0, 285.0, '2024-12-05', 110.0)," +
                "('DM7866-162', 36.0, 195.0, 310.0, '2024-12-17', 115.0)," +
                "('ID2534', 42.0, 205.0, 325.0, '2024-12-29', 120.0)," +
                "('DM7866-200', 39.0, 190.0, 310.0, '2024-12-31', 120.0)," +
                "('ID2529', 44.5, 185.0, 295.0, '2023-01-09', 110.0)," +

                "('DM7866-162', 39.0, 230.0, 355.0, '2024-01-18', 125.0)," +
                "('ID2534', 36.0, 180.0, 295.0, '2025-02-04', 115.0)," +
                "('DM7866-200', 42.0, 210.0, 330.0, '2025-02-15', 120.0)," +
                "('ID2529', 36.0, 175.0, 285.0, '2025-03-01', 110.0)," +
                "('DM7866-162', 36.0, 190.0, 305.0, '2025-03-12', 115.0)," +
                "('ID2534', 37.5, 185.0, 295.0, '2025-03-29', 110.0)," +
                "('DM7866-200', 44.5, 200.0, 315.0, '2025-04-07', 115.0)," +
                "('ID2529', 42.0, 170.0, 275.0, '2025-04-23', 105.0)," +
                "('DM7866-162', 42.0, 220.0, 340.0, '2023-05-06', 120.0)," +
                "('ID2534', 39.0, 190.0, 310.0, '2024-05-19', 120.0)," +

                "('DM7866-200', 37.5, 185.0, 295.0, '2024-06-03', 110.0)," +
                "('ID2529', 39.0, 180.0, 290.0, '2024-06-16', 110.0)," +
                "('DM7866-162', 36.0, 195.0, 315.0, '2023-07-07', 120.0)," +
                "('ID2534', 42.0, 205.0, 325.0, '2023-07-21', 120.0)," +
                "('DM7866-200', 44.5, 200.0, 320.0, '2023-08-05', 120.0)," +
                "('ID2529', 36.0, 170.0, 275.0, '2023-08-19', 105.0)," +
                "('DM7866-162', 39.0, 225.0, 345.0, '2023-09-03', 120.0)," +
                "('ID2534', 36.0, 175.0, 280.0, '2023-09-14', 105.0);"
        );

        // Insertar ventas por defecto
        db.execSQL("INSERT INTO " + TABLA_VENTAS + " (" +
                COLUMN_SKU + ", " + COLUMN_TALLA_VENTA + ", " +
                COLUMN_COMPRA + ", " + COLUMN_VENTA + ", " +
                COLUMN_FECHA + ", " + COLUMN_RESULTADO + ") VALUES " +

                "('DM7866-200', 36.0, 190.0, 305.0, '2024-10-01', 115.0)," +
                "('ID2529', 37.5, 165.0, 275.0, '2023-10-14', 110.0)," +
                "('DM7866-162', 42.0, 220.0, 345.0, '2024-10-29', 125.0)," +
                "('ID2534', 36.0, 180.0, 295.0, '2023-11-07', 115.0)," +
                "('DM7866-200', 39.0, 185.0, 295.0, '2024-11-21', 110.0)," +
                "('ID2529', 44.5, 175.0, 285.0, '2023-11-30', 110.0)," +
                "('DM7866-162', 36.0, 200.0, 310.0, '2024-12-10', 110.0)," +
                "('ID2534', 42.0, 195.0, 310.0, '2024-12-19', 115.0)," +
                "('DM7866-200', 42.0, 205.0, 325.0, '2023-12-27', 120.0)," +
                "('ID2529', 39.0, 180.0, 290.0, '2024-12-30', 110.0)," +

                "('DM7866-162', 39.0, 230.0, 355.0, '2025-01-13', 125.0)," +
                "('ID2534', 36.0, 175.0, 285.0, '2025-01-26', 110.0)," +
                "('DM7866-200', 37.5, 190.0, 300.0, '2025-02-06', 110.0)," +
                "('ID2529', 42.0, 170.0, 275.0, '2025-02-24', 105.0)," +
                "('DM7866-162', 42.0, 210.0, 330.0, '2025-03-06', 120.0)," +
                "('ID2534', 39.0, 180.0, 295.0, '2025-03-16', 115.0)," +
                "('DM7866-200', 44.5, 200.0, 315.0, '2025-03-30', 115.0)," +
                "('ID2529', 36.0, 170.0, 275.0, '2025-04-13', 105.0)," +
                "('DM7866-162', 36.0, 190.0, 310.0, '2025-04-26', 120.0)," +
                "('ID2534', 37.5, 185.0, 300.0, '2023-05-02', 115.0)," +

                "('DM7866-200', 42.0, 205.0, 320.0, '2024-05-16', 115.0)," +
                "('ID2529', 39.0, 180.0, 290.0, '2024-05-28', 110.0)," +
                "('DM7866-162', 39.0, 225.0, 345.0, '2023-06-08', 120.0)," +
                "('ID2534', 36.0, 165.0, 270.0, '2023-06-20', 105.0)," +
                "('DM7866-200', 39.0, 190.0, 305.0, '2023-07-06', 115.0)," +
                "('ID2529', 37.5, 165.0, 275.0, '2023-07-27', 110.0)," +
                "('DM7866-162', 36.0, 200.0, 320.0, '2023-08-04', 120.0)," +
                "('ID2534', 42.0, 205.0, 325.0, '2023-08-18', 120.0)," +
                "('DM7866-200', 36.0, 195.0, 310.0, '2023-08-30', 115.0)," +
                "('ID2529', 44.5, 180.0, 290.0, '2023-09-09', 110.0)," +

                "('DM7866-162', 39.0, 220.0, 340.0, '2023-09-20', 120.0)," +
                "('ID2534', 39.0, 190.0, 305.0, '2023-10-02', 115.0)," +
                "('DM7866-200', 42.0, 200.0, 315.0, '2024-10-13', 115.0)," +
                "('ID2529', 36.0, 170.0, 275.0, '2023-10-24', 105.0)," +
                "('DM7866-162', 42.0, 215.0, 335.0, '2024-11-03', 120.0)," +
                "('ID2534', 36.0, 175.0, 285.0, '2024-11-15', 110.0)," +
                "('DM7866-200', 37.5, 185.0, 295.0, '2024-11-28', 110.0)," +
                "('ID2529', 39.0, 180.0, 290.0, '2023-12-09', 110.0)," +
                "('DM7866-162', 36.0, 195.0, 310.0, '2023-12-18', 115.0)," +
                "('ID2534', 42.0, 205.0, 320.0, '2024-12-30', 115.0);"
        );



    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Elimina las tablas si existen
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_PRODUCTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_STOCK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLA_VENTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSIGNA);
        // Vuelve a crear las tablas
        onCreate(db);
    }

    // Insertar productos
    public boolean aniadirProducto(String nombre, String sku, String marca, String genero, double precio, String imagenProducto){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_SKU, sku);
        values.put(COLUMN_MARCA, marca);
        values.put(COLUMN_GENERO, genero);
        values.put(COLUMN_PRECIO, precio);
        values.put(COLUMN_IMAGEN_PRODUCTO, imagenProducto);
        // Inserta el producto en la base de datos
        long result = db.insert(TABLA_PRODUCTOS, null, values);
        db.close();
        return result != -1;
    }

    // Método para obtener productos
    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener todos los productos
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_PRODUCTOS, null);
        if (cursor.moveToFirst()) {
            do {
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
                String sku = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKU));
                String marca = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MARCA));
                String genero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO));
                String imagenProducto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_PRODUCTO));
                // Crea un objeto Producto y lo añade a la lista
                Producto producto = new Producto(nombre, sku, marca, genero, precio, imagenProducto);
                productos.add(producto);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return productos;
    }

    public int obtenerStock(String sku) {
        SQLiteDatabase db = this.getReadableDatabase();
        int stock = 0;

        // Consulta a la base de datos para obtener el stock del producto
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + COLUMN_CANTIDAD + ") FROM " + TABLA_STOCK + " WHERE " + COLUMN_SKU + " = ?", new String[]{sku});

        // Obtiene el stock del producto
        if (cursor.moveToFirst()) {
            stock = cursor.isNull(0) ? 0 : cursor.getInt(0);
        }

        cursor.close();
        return stock;
    }

    // Método para obtener stock por SKU
    public List<Stock> obtenerStockPorSku(String sku) {
        List<Stock> stock = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener el stock del producto por SKU
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_STOCK + " WHERE " + COLUMN_SKU + " = ?", new String[]{sku});
        if (cursor.moveToFirst()) {
            do {
                int idStock = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_STOCK));
                double talla = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TALLA));
                int cantidad = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CANTIDAD));
                // Crea un objeto Stock y lo añade a la lista
                Stock stockProducto = new Stock(idStock, sku, talla, cantidad);
                stock.add(stockProducto);
            }while (cursor.moveToNext());
        }
        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();

        return stock;
    }

    // Método para insertar o actualizar stock
    public void insertarOActualizarStock(String sku, double talla, int cantidad) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Verifica si ya existe un registro con el mismo SKU y talla
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_STOCK + " WHERE " + COLUMN_SKU + " = ? AND " + COLUMN_TALLA + " = ?",
                new String[]{sku, String.valueOf(talla)});

        // Si existe un registro, actualiza su cantidad
        if (cursor.moveToFirst()) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CANTIDAD, cantidad);
            // Actualiza el registro existente
            db.update(TABLA_STOCK, values, COLUMN_SKU + " = ? AND " + COLUMN_TALLA + " = ?",
                    new String[]{sku, String.valueOf(talla)});
        } else {    // Si no existe, inserta un nuevo registro
            ContentValues values = new ContentValues();
            values.put(COLUMN_SKU, sku);
            values.put(COLUMN_TALLA, talla);
            values.put(COLUMN_CANTIDAD, cantidad);
            // Inserta un nuevo registro
            db.insert(TABLA_STOCK, null, values);
        }

        cursor.close();
        db.close();
    }

    public void eliminarStockSiExiste(String sku, double talla) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Verifica si ya existe un registro con el mismo SKU y talla y lo elimina
        db.delete(TABLA_STOCK, COLUMN_SKU + " = ? AND " + COLUMN_TALLA + " = ?",
                new String[]{sku, String.valueOf(talla)});
        db.close();
    }

    public String obtenerImagenPorSku(String sku){
        String imagenProducto = null;
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener la imagen del producto por SKU
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_IMAGEN_PRODUCTO + " FROM " + TABLA_PRODUCTOS + " WHERE " + COLUMN_SKU + " = ?", new String[]{sku});
        // Obtiene la imagen del producto
        if (cursor.moveToFirst()) {
            imagenProducto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_PRODUCTO));
        }
        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();
        return imagenProducto;
    }

    public void insertarVenta(String sku, double talla, double precioCompra, double precioVenta, String fecha) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SKU, sku);
        values.put(COLUMN_TALLA_VENTA, talla);
        values.put(COLUMN_COMPRA, precioCompra);
        values.put(COLUMN_VENTA, precioVenta);
        values.put(COLUMN_FECHA, fecha);
        values.put(COLUMN_RESULTADO, precioVenta - precioCompra);
        // Inserta la venta en la base de datos
        db.insert(TABLA_VENTAS, null, values);
        db.close();
    }

    public ArrayList<Venta> obtenerVentas() {
        ArrayList<Venta> ventas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener todas las ventas
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_VENTAS + " ORDER BY " + COLUMN_FECHA + " DESC", null);

        if (cursor.moveToFirst()) {
            do {
                int idVenta = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_VENTA));
                String sku = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKU));
                double talla = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TALLA_VENTA));
                double compra = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_COMPRA));
                double venta = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_VENTA));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA));
                double resultado = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_RESULTADO));
                // Crea un objeto Venta y lo añade a la lista
                Venta ventaProducto = new Venta(idVenta, sku, talla, compra, venta, fecha, resultado);
                ventas.add(ventaProducto);
            } while (cursor.moveToNext());

            cursor.close();
            db.close();
        }
        return ventas;
    }

    public Producto obtenerProductoPorSku(String sku) {
        Producto producto = null;
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener el producto por SKU
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLA_PRODUCTOS + " WHERE " + COLUMN_SKU + " = ?", new String[]{sku});

        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
            String marca = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MARCA));
            String genero = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO));
            double precio = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO));
            String imagenProducto = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_PRODUCTO));
            // Crea un objeto Producto y lo devuelve
            producto = new Producto(nombre, sku, marca, genero, precio, imagenProducto);
        }
        cursor.close();
        db.close();

        return producto;
    }

    public String obtenerNombrePorSku(String sku) {
        String nombre = null;
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener el nombre del producto por SKU
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NOMBRE + " FROM " + TABLA_PRODUCTOS + " WHERE " + COLUMN_SKU + " = ?", new String[]{sku});
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE));
        }
        // Cierra el cursor y la base de datos
        cursor.close();
        db.close();

        return nombre;
    }

    public void insertarConsigna(String tienda, double talla, String sku, double precioCompra, double precioVenta, String fecha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIENDA, tienda);
        values.put(COLUMN_TALLA_CONSIGNA, talla);
        values.put(COLUMN_SKU_CONSIGNA, sku);
        values.put(COLUMN_PRECIO_COMPRA, precioCompra);
        values.put(COLUMN_PRECIO_VENTA, precioVenta);
        values.put(COLUMN_FECHA_CONSIGNA, fecha);
        // Inserta la consigna en la base de datos
        db.insert(TABLE_CONSIGNA, null, values);
        db.close();
    }

    public Map<String, Integer> obtenerMapaTiendas() {
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener el número de ventas por tienda
        Map<String, Integer> mapaTiendas = new HashMap<>();

        // Consulta a la base de datos para obtener el número de ventas por tienda
        String query = "SELECT " + COLUMN_TIENDA + ", COUNT(*) AS total " +
                "FROM " + TABLE_CONSIGNA + " GROUP BY " + COLUMN_TIENDA;

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                String tienda = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIENDA));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
                // Añade la tienda y el número de ventas al mapa
                mapaTiendas.put(tienda, total);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return mapaTiendas;
    }

    public ArrayList<Consigna> obtenerProductosConsignados(String tienda) {
        ArrayList<Consigna> productosConsignados = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener los productos consignados por tienda
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONSIGNA + " WHERE " + COLUMN_TIENDA + " = ?", new String[]{tienda});
        if (cursor.moveToFirst()) {
            do {
                int idConsigna = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_CONSIGNA));
                String sku = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKU_CONSIGNA));
                double talla = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TALLA_CONSIGNA));
                double precioCompra = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO_COMPRA));
                double precioVenta = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO_VENTA));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FECHA_CONSIGNA));
                // Crea un objeto Consigna y lo añade a la lista
                Consigna consigna = new Consigna(idConsigna, tienda, talla, sku, precioCompra, precioVenta, fecha);
                productosConsignados.add(consigna);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }

        return productosConsignados;
    }

    public boolean eliminarConsigna(int idConsigna) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Elimina la consigna de la base de datos
        int rowsAffected = db.delete(TABLE_CONSIGNA, COLUMN_ID_CONSIGNA + " = ?", new String[]{String.valueOf(idConsigna)});
        db.close();
        return rowsAffected > 0;
    }

    public Map<String, Integer> obtenerVentasPorMes(String anio) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Consulta a la base de datos para obtener el número de ventas por mes
        Map<String, Integer> ventasPorMes = new LinkedHashMap<>();

        // Nombres de los meses
        String[] nombresMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        // Inicializa el mapa con el número de ventas por mes
        for (String nombreMes : nombresMeses) {
            ventasPorMes.put(nombreMes, 0);
        }

        // Consulta a la base de datos para obtener el número de ventas por mes agrupando por mes
        String query = "SELECT strftime('%m', fecha) AS mes, COUNT(*) AS total " +
                "FROM ventas WHERE strftime('%Y', fecha) = ? " +
                "GROUP BY mes ORDER BY mes";

        Cursor cursor = db.rawQuery(query, new String[]{anio});
        if (cursor.moveToFirst()) {
            do {
                // Obtiene el mes y el número de ventas
                String mesNumero = cursor.getString(cursor.getColumnIndexOrThrow("mes"));
                int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));

                // Actualiza el mapa con el número de ventas por mes
                int mesIndex = Integer.parseInt(mesNumero) - 1;
                String nombreMes = nombresMeses[mesIndex];
                // Añade el mes y el número de ventas al mapa
                ventasPorMes.put(nombreMes, total);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return ventasPorMes;
    }

    public Map<String, Double> obtenerBeneficioPorMes(String anio) {
        SQLiteDatabase db = this.getReadableDatabase();
        // Mapa para almacenar el beneficio obtenido por mes
        Map<String, Double> beneficioPorMes = new LinkedHashMap<>();

        String[] nombresMeses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};

        // Inicializa el mapa con el beneficio obtenido por mes
        for (String nombreMes : nombresMeses) {
            beneficioPorMes.put(nombreMes, 0.0);
        }

        // Consulta a la base de datos para obtener el beneficio obtenido por mes
        String query = "SELECT strftime('%m', fecha) AS mes, SUM(venta - compra) AS beneficio " +
                "FROM ventas WHERE strftime('%Y', fecha) = ? " +
                "GROUP BY mes ORDER BY mes";

        Cursor cursor = db.rawQuery(query, new String[]{anio});
        if (cursor.moveToFirst()) {
            do {
                // Obtiene el mes y el beneficio
                String mesNumero = cursor.getString(cursor.getColumnIndexOrThrow("mes"));
                double beneficio = cursor.getDouble(cursor.getColumnIndexOrThrow("beneficio"));

                // Actualiza el mapa con el beneficio obtenido por mes
                int mesIndex = Integer.parseInt(mesNumero) - 1;
                String nombreMes = nombresMeses[mesIndex];

                // Añade el mes y el beneficio al mapa
                beneficioPorMes.put(nombreMes, beneficio);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return beneficioPorMes;
    }

    public int totalVentas() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;

        // Consulta a la base de datos para obtener el número de ventas
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS total FROM " + TABLA_VENTAS, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        }

        cursor.close();
        db.close();
        return total;
    }

    public double costeTotal() {
        SQLiteDatabase db = this.getReadableDatabase();
        double costeTotal = 0.0;

        // Consulta a la base de datos para obtener el coste total de las compras
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_COMPRA + ") AS totalCompra FROM " + TABLA_VENTAS, null);
        if (cursor.moveToFirst()) {
            costeTotal = cursor.getDouble(cursor.getColumnIndexOrThrow("totalCompra"));
        }

        cursor.close();
        db.close();
        return costeTotal;
    }

    public double ventasBrutas() {
        SQLiteDatabase db = this.getReadableDatabase();
        double totalVentas = 0.0;

        // Consulta a la base de datos para obtener el total de las ventas
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_VENTA + ") AS totalVentas FROM " + TABLA_VENTAS, null);
        if (cursor.moveToFirst()) {
            // Obtiene el total de las ventas
            totalVentas = cursor.getDouble(cursor.getColumnIndexOrThrow("totalVentas"));
        }

        cursor.close();
        db.close();
        return totalVentas;
    }

    public double totalBeneficio() {
        SQLiteDatabase db = this.getReadableDatabase();
        double beneficio = 0.0;

        // Consulta a la base de datos para obtener el total del beneficio
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_RESULTADO + ") AS totalBeneficio FROM " + TABLA_VENTAS, null);
        if (cursor.moveToFirst()) {
            beneficio = cursor.getDouble(cursor.getColumnIndexOrThrow("totalBeneficio"));
        }

        cursor.close();
        db.close();
        return beneficio;
    }

    public int ventasMesActual() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;

        // Consulta a la base de datos para obtener el número de ventas del mes actual
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) AS total FROM " + TABLA_VENTAS +
                        " WHERE strftime('%Y-%m', fecha) = strftime('%Y-%m', 'now')", null
        );
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        }

        cursor.close();
        db.close();
        return total;
    }

    public double mediaVentas() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> ventas = new ArrayList<>();

        // Consulta a la base de datos para obtener el número de ventas por mes
        Cursor cursor = db.rawQuery("SELECT strftime('%m', fecha) AS mes, COUNT(*) AS total " +
                "FROM ventas GROUP BY mes ORDER BY mes", null);

        if (cursor.moveToFirst()) {
            do {
                // Obtiene el mes y el número de ventas
                int total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
                ventas.add(total);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Verifica si hay ventas
        if (!ventas.isEmpty()) {
            int suma = 0;
            // Calcula la media de las ventas
            for (int v : ventas) {
                suma += v;
            }
            // Devuelve la media de las ventas
            return (double) suma / ventas.size();
        } else {
            return 0.0;
        }
    }

    public String obtenerZapatillaMasVendida() {
        SQLiteDatabase db = this.getReadableDatabase();
        String resultado = "No hay ventas";

        // Consulta a la base de datos para obtener la zapatilla más vendida
        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_SKU + ", COUNT(*) AS totalVentas FROM " + TABLA_VENTAS +
                        " GROUP BY " + COLUMN_SKU +
                        " ORDER BY totalVentas DESC LIMIT 1", null
        );

        if (cursor.moveToFirst()) {
            resultado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKU));
        }

        cursor.close();
        db.close();
        return resultado;
    }

    public int productosConsignados() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;

        // Consulta a la base de datos para obtener el número de productos consignados
        Cursor cursor = db.rawQuery("SELECT COUNT(*) AS total FROM " + TABLE_CONSIGNA, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        }

        cursor.close();
        db.close();
        return total;
    }

    public int stockDisponible() {
        SQLiteDatabase db = this.getReadableDatabase();
        int total = 0;

        // Consulta a la base de datos para obtener el número de productos en stock
        Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_CANTIDAD + ") AS total FROM " + TABLA_STOCK, null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndexOrThrow("total"));
        }

        cursor.close();
        db.close();
        return total;
    }

    public ArrayList<Producto> buscarProductos(String textoBusqueda) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Producto> listaProductos = new ArrayList<>();

        // Elimina los espacios en blanco al principio y al final del texto de búsqueda
        textoBusqueda = textoBusqueda.trim();

        // Consulta a la base de datos para obtener los productos que coincidan con el texto de búsqueda
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLA_PRODUCTOS + " WHERE " + COLUMN_SKU + " LIKE ?",
                new String[]{"%" + textoBusqueda + "%"}
        );

        // Añade los productos encontrados a la lista
        if (cursor.moveToFirst()) {
            do {
                Producto producto = new Producto(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKU)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MARCA)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENERO)),
                        cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRECIO)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN_PRODUCTO))
                );
                // Añade el producto a la lista
                listaProductos.add(producto);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Si no se encontraron productos, consulta a la base de datos para obtener los productos que coincidan con el texto de búsqueda
        if (listaProductos.isEmpty()) {
            // Consulta a la base de datos para obtener los productos que coincidan con el texto de búsqueda
            Cursor cursorNombre = db.rawQuery(
                    "SELECT * FROM " + TABLA_PRODUCTOS + " WHERE " + COLUMN_NOMBRE + " LIKE ?",
                    new String[]{"%" + textoBusqueda + "%"}
            );

            // Añade los productos encontrados a la lista
            if (cursorNombre.moveToFirst()) {
                do {
                    Producto producto = new Producto(
                            cursorNombre.getString(cursorNombre.getColumnIndexOrThrow(COLUMN_NOMBRE)),
                            cursorNombre.getString(cursorNombre.getColumnIndexOrThrow(COLUMN_SKU)),
                            cursorNombre.getString(cursorNombre.getColumnIndexOrThrow(COLUMN_MARCA)),
                            cursorNombre.getString(cursorNombre.getColumnIndexOrThrow(COLUMN_GENERO)),
                            cursorNombre.getDouble(cursorNombre.getColumnIndexOrThrow(COLUMN_PRECIO)),
                            cursorNombre.getString(cursorNombre.getColumnIndexOrThrow(COLUMN_IMAGEN_PRODUCTO))
                    );
                    // Añade el producto a la lista
                    listaProductos.add(producto);
                } while (cursorNombre.moveToNext());
            }
            cursorNombre.close();
        }

        db.close();
        return listaProductos;
    }

    public double obtenerBeneficio(String sku) {
        SQLiteDatabase db = this.getReadableDatabase();
        double beneficio = 0.0;

        // Consulta a la base de datos para obtener el beneficio obtenido por la venta
        Cursor cursor = db.rawQuery(
                "SELECT SUM(" + COLUMN_RESULTADO + ") AS totalBeneficio FROM " + TABLA_VENTAS + " WHERE " + COLUMN_SKU + " = ?",
                new String[]{sku}
        );

        if (cursor.moveToFirst()) {
            beneficio = cursor.getDouble(cursor.getColumnIndexOrThrow("totalBeneficio"));
        }

        cursor.close();
        db.close();
        return beneficio;
    }


    public int obtenerTotalVentas(String sku) {
        SQLiteDatabase db = this.getReadableDatabase();
        int totalVentas = 0;

        // Consulta a la base de datos para obtener el número de ventas
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) AS totalVentas FROM " + TABLA_VENTAS + " WHERE " + COLUMN_SKU + " = ?",
                new String[]{sku}
        );

        if (cursor.moveToFirst()) {
            totalVentas = cursor.getInt(cursor.getColumnIndexOrThrow("totalVentas"));
        }

        cursor.close();
        db.close();
        return totalVentas;
    }


    public String obtenerTallaMasVendida(String sku) {
        SQLiteDatabase db = this.getReadableDatabase();
        String tallaMasVendida = "No hay ventas";

        // Consulta a la base de datos para obtener la talla más vendida
        Cursor cursor = db.rawQuery(
                "SELECT " + COLUMN_TALLA_VENTA + ", COUNT(*) AS total " +
                        "FROM " + TABLA_VENTAS + " WHERE " + COLUMN_SKU + " = ? " +
                        "GROUP BY " + COLUMN_TALLA_VENTA + " ORDER BY total DESC LIMIT 1",
                new String[]{sku}
        );

        if (cursor.moveToFirst()) {
            tallaMasVendida = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TALLA_VENTA));
        }

        cursor.close();
        db.close();
        return tallaMasVendida;
    }


    public String obtenerMesMasVentas(String sku) {
        SQLiteDatabase db = this.getReadableDatabase();
        String mesMasVentas = "No hay ventas";

        // Consulta a la base de datos para obtener el mes más vendido
        Cursor cursor = db.rawQuery(
                "SELECT strftime('%m', fecha) AS mes, COUNT(*) AS total " +
                        "FROM " + TABLA_VENTAS + " WHERE " + COLUMN_SKU + " = ? " +
                        "GROUP BY mes ORDER BY total DESC LIMIT 1",
                new String[]{sku}
        );

        if (cursor.moveToFirst()) {
            String mesNumero = cursor.getString(cursor.getColumnIndexOrThrow("mes"));
            // Convierte el número del mes a su nombre correspondiente
            mesMasVentas = convertirMesNumeroAString(mesNumero);
        }

        cursor.close();
        db.close();
        return mesMasVentas;
    }

    // Convierte el número del mes a su nombre correspondiente (por ejempl, "1" a "Enero")
    private String convertirMesNumeroAString(String mes) {
        String[] meses = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
                "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };
        try {
            // Convierte el número del mes a su nombre correspondiente
            int index = Integer.parseInt(mes) - 1;
            // Devuelve el nombre del mes correspondiente
            return meses[index];
        } catch (Exception e) {
            return mes;
        }
    }



}
