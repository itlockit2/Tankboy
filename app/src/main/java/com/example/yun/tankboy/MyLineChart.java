package com.example.yun.tankboy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by khs on 2018-08-25.
 */

public class MyLineChart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linechart);

        // in this example, sa activity_linechart is initialized from xml
        LineChart chart = (LineChart) findViewById(R.id.chart);

        chart.setDrawGridBackground(false);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(false);

        /*
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        */


        HashMap<Integer,Integer> dataObjects = new HashMap<>();

        List<Entry> entries = new ArrayList<Entry>();

        for(int i = 0 ; i <= 23 ; i++){
            entries.add(new Entry(i, (float) (400*randomRange(-0.9,1.1))));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.invalidate(); // refresh

    }
    private double randomRange(double n1, double n2) {
        return  (Math.random() * (n2 - n1 + 1)) + n1;
    }
}