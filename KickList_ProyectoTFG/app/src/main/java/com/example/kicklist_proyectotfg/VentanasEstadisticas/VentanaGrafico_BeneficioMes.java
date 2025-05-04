package com.example.kicklist_proyectotfg.VentanasEstadisticas;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.kicklist_proyectotfg.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.example.kicklist_proyectotfg.Persistencia.DatabaseHelper;

public class VentanaGrafico_BeneficioMes extends AppCompatActivity {

    private DatabaseHelper db;
    Spinner spn_anio_grafico_uds;
    Button btn_volver_grafico_uds;
    BarChart barChart;
    List<String> listaAnios = Arrays.asList("2025", "2024", "2023");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ventana_grafico_beneficio_mes);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new DatabaseHelper(this);
        spn_anio_grafico_uds = findViewById(R.id.spn_anio_grafico_beneficio);
        btn_volver_grafico_uds = findViewById(R.id.btn_volver_grafico_beneficio);
        barChart = findViewById(R.id.barChartGrafico);

        // Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaAnios);
        // Asignar el adaptador al spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_anio_grafico_uds.setAdapter(adapter);

        // Obtener el primer año de la lista
        String anioInicial = listaAnios.get(0);
        actualizarGrafico(anioInicial);

        // Manejar la selección del spinner
        spn_anio_grafico_uds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el año seleccionado
                String anioSeleccionado = listaAnios.get(position);
                actualizarGrafico(anioSeleccionado);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btn_volver_grafico_uds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VentanaGrafico_BeneficioMes.this, VentanaEstadisticas.class);
                startActivity(intent);
                finish();
            }
        });

    }

    // Método para actualizar el gráfico con los datos del año seleccionado
    private void actualizarGrafico(String anio) {
        Map<String, Double> beneficioPorMes = db.obtenerBeneficioPorMes(anio);

        // Listas para los datos del gráfico
        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        int index = 0;

        // Añadir los datos al gráfico
        for (Map.Entry<String, Double> entry : beneficioPorMes.entrySet()) {
            // Convertir el mes en un número
            entries.add(new BarEntry(index, entry.getValue().floatValue()));
            // Añadir el nombre del mes
            labels.add(entry.getKey());
            // Incrementar el índice
            index++;
        }

        // Crear el conjunto de datos
        BarDataSet dataSet = new BarDataSet(entries, "Beneficio obtenido en " + anio);
        dataSet.setColor(Color.parseColor("#3F51B5"));    // Color azul para el gráfico
        dataSet.setValueTextColor(Color.BLACK);                     // Color del texto de los valores
        dataSet.setValueTextSize(14f);                              // Tamaño del texto de los valores

        // Mostrar valores con dos decimales
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getBarLabel(BarEntry barEntry) {
                // Formatear el valor con dos decimales
                return String.format("%.2f€", barEntry.getY());
            }
        });

        // Crear los datos del gráfico
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);  // Ancho de las barras

        barChart.setData(barData);  // Asignar los datos al gráfico
        barChart.setFitBars(true);  // Ajustar las barras al tamaño del gráfico
        barChart.setDrawGridBackground(false);  // Quitar el fondo del gráfico
        barChart.setDrawBarShadow(false);   // Quitar la sombra de las barras

        // Animación de la gráfica
        barChart.animateY(1000);

        // Quitar descripción
        Description description = new Description();
        description.setText("");
        barChart.setDescription(description);

        // Eje X
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));   // Formatear los valores del eje X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);                  // Posición del eje X
        xAxis.setDrawGridLines(false);                                  // Quitar líneas de cuadrícula
        xAxis.setGranularity(1f);                                       // Incremento del eje X
        xAxis.setGranularityEnabled(true);                              // Habilitar el incremento del eje X
        xAxis.setLabelRotationAngle(-45);                               // Rotar las etiquetas del eje X
        xAxis.setTextColor(Color.BLACK);                                // Color de las etiquetas del eje X

        // Ejes Y
        barChart.getAxisLeft().setGranularity(1f);              // Incremento del eje Y
        barChart.getAxisLeft().setTextColor(Color.BLACK);       // Color de las etiquetas del eje Y
        barChart.getAxisRight().setEnabled(false);              // Quitar el eje Y derecho

        // Leyenda
        barChart.getLegend().setTextColor(Color.BLACK);      // Color de las etiquetas de la leyenda

        barChart.invalidate();  // Actualizar el gráfico
    }


}