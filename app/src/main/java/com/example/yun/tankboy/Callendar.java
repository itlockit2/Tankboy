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

        // 몇 월인지 보여줌
        TextView monthTextView = (TextView) findViewById(R.id.MonthTextView);
        monthTextView.setTextSize(Intro.width_pixel / 30);

        // 차트 그리기
        drawChart();
    }

    private void drawChart(){
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


        entries.add(new Entry(0, 344));
        entries.add(new Entry(1, 287));
        entries.add(new Entry(2, 242));
        entries.add(new Entry(3, 220));
        entries.add(new Entry(4, 211));
        entries.add(new Entry(5, 203));
        entries.add(new Entry(6, 211));
        entries.add(new Entry(7, 203));
        entries.add(new Entry(8, 273));
        entries.add(new Entry(9, 291));
        entries.add(new Entry(10, 291));
        entries.add(new Entry(11, 294));
        entries.add(new Entry(12, 296));
        entries.add(new Entry(13, 294));
        entries.add(new Entry(14, 299));
        entries.add(new Entry(15, 299));
        entries.add(new Entry(16, 305));
        entries.add(new Entry(17, 309));
        entries.add(new Entry(18, 332));
        entries.add(new Entry(19, 359));
        entries.add(new Entry(20, 390));
        entries.add(new Entry(21, 401));
        entries.add(new Entry(22, 405));
        entries.add(new Entry(23, 384));







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

