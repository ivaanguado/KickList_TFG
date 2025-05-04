package com.example.kicklist_proyectotfg.Persistencia;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    static final String PREF_NAME = "KickList";
    static final String KEY_PASSWORD = "contrasenia";

    public static boolean guardarContrasenia(Context context, String contrasenia) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_PASSWORD, contrasenia);
        editor.apply();
        return true;
    }

    public static String obtenerContrasenia(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_PASSWORD, "admin");
    }

}
