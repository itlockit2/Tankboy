package com.example.yun.tankboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Compare extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        drawLineChartForCompare();
    }

    private void drawLineChartForCompare(){
        // in this example, sa activity_linechart is initialized from xml
        LineChart chart = (LineChart) findViewById(R.id.chart1);

        chart.setDrawGridBackground(false);
        chart.setDrawGridBackground(false);
        chart.getDescription().setEnabled(false);
        chart.setDrawBorders(false);

        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setDrawAxisLine(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getXAxis().setDrawAxisLine(false);
        chart.getXAxis().setDrawGridLines(true);

        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int val = (int)(value);
                if(val/10 == 0)
                    return "0"+val+":"+"00";
                return ""+ (int)(value) +":" + "00";
            }
        });


        HashMap<Integer,Integer> dataObjects = new HashMap<>();

        List<Entry> entries1 = new ArrayList<Entry>();
        List<Entry> entries2 = new ArrayList<Entry>();

        for(int i = 0 ; i <= 23 ; i++){
            entries1.add(new Entry(i, (float) (400*randomRange(0.9,1.1))));
            entries2.add(new Entry(i, (float) (400*randomRange(0.7,0.9))));
        }

        int[] mColors = new int[] {
                ColorTemplate.VORDIPLOM_COLORS[0],
                ColorTemplate.VORDIPLOM_COLORS[1],
                ColorTemplate.VORDIPLOM_COLORS[2]
        };

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        for (int z = 0; z < 2; z++) {

            ArrayList<Entry> values = new ArrayList<Entry>();
            int[] coef = {991, 850, 766, 718, 695, 692, 763, 881, 941, 961, 973
                    ,983 ,1008, 1015, 1008, 1018, 1046, 1102, 1188, 1285, 1340, 1340, 1282, 1151};
            double curConsumption = 320*1000/30/24;
            for (int i = 0; i <= 23; i++) {
                double val =  curConsumption*(coef[i] + randomRange(-10, 10))/1000- z*20;
                values.add(new Entry(i, (int) val));
            }

            LineDataSet d = new LineDataSet(values, "DataSet " + (z + 1));
            d.setLineWidth(2.5f);
            d.setCircleRadius(4f);

            int color = mColors[z % mColors.length];
            d.setColor(color);
            d.setCircleColor(color);
            dataSets.add(d);
        }
/*

        // make the first DataSet dashed
        ((LineDataSet) dataSets.get(0)).enableDashedLine(10, 10, 0);
        ((LineDataSet) dataSets.get(0)).setColors(ColorTemplate.VORDIPLOM_COLORS);
        ((LineDataSet) dataSets.get(0)).setCircleColors(ColorTemplate.VORDIPLOM_COLORS);
*/

        LineData data = new LineData(dataSets);
        chart.setData(data);
        chart.invalidate();
    }

    private double randomRange(double n1, double n2) {
        return  (Math.random() * (n2 - n1 + 1)) + n1;
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Compare.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
