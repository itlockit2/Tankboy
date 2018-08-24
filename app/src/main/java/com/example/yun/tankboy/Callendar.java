package com.example.yun.tankboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Callendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callendar);

        final int clickedDay;

        // 버튼 이벤트
        final Button button04 = (Button) findViewById(R.id.button4);
        button04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 차트
                //drawChart(dailyData);
            }
        });
    }

    private void drawChart(ArrayList<Double> list){
        LineChart chart1 = (LineChart) findViewById(R.id.RealChart);

        chart1.setDrawGridBackground(false);
        chart1.setDrawGridBackground(false);
        chart1.getDescription().setEnabled(false);
        chart1.setDrawBorders(false);

        chart1.getAxisLeft().setEnabled(false);
        chart1.getAxisRight().setDrawAxisLine(false);
        chart1.getAxisRight().setDrawGridLines(false);
        chart1.getXAxis().setDrawAxisLine(false);
        chart1.getXAxis().setDrawGridLines(false);

        HashMap<Integer,Integer> dataObjects = new HashMap<>();

        List<Entry> entries = new ArrayList<Entry>();



        for(int i = 0 ; i <= 23 ; i++){
            entries.add(new Entry(i, (float) list.indexOf(i));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Label");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        LineData lineData = new LineData(dataSet);
        chart1.setData(lineData);
        chart1.invalidate(); // refresh
    }

    private double randomRange(double n1, double n2) {
        return  (Math.random() * (n2 - n1 + 1)) + n1;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Callendar.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

