package com.example.yun.tankboy;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private LineChart mChart;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mDatabaseReference = Intro.mFirebaseDatabase.getReference("users/" + mAuth.getUid() + "/meters/");
        getConsume();

        mChart = (LineChart) findViewById(R.id.Linechart);

       // mChart.setOnChartGestureListener(MainActivity.this);
       // mChart.setOnChartValueSelectedListener(MainActivity.this);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0,60f));
        yValues.add(new Entry(1,50f));
        yValues.add(new Entry(2,70f));
        yValues.add(new Entry(3,30f));
        yValues.add(new Entry(4,50f));
        yValues.add(new Entry(5,60f));
        yValues.add(new Entry(6,65f));
        LineDataSet set1 = new LineDataSet(yValues,"Data Set 1");

        set1.setFillAlpha(110);

        set1.setColor(Color.RED);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.GREEN);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        mChart.setData(data);
    }

    private void getConsume() {
        mDatabaseReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(dataSnapshot.getKey().equals("20170831211500")){
                            Meter meter = dataSnapshot.getValue(Meter.class);
                            System.out.println("getAccConsumption = " + meter.getAccConsumption());
                            System.out.println("getCurConsumption = " + meter.getCurConsumption());
                            String year = dataSnapshot.getKey().substring(0,4);
                            String month = dataSnapshot.getKey().substring(4,6);
                            String day = dataSnapshot.getKey().substring(6,8);
                            String hour = dataSnapshot.getKey().substring(8,10);
                            String minutes = dataSnapshot.getKey().substring(10,12);
                            System.out.println("year = " +  year);
                            System.out.println("month = " +  month);
                            System.out.println("day = " +  day);
                            System.out.println("hour = " +  hour);
                            System.out.println("minutes = " +  minutes);

                            return;
                        }
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
