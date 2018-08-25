package com.example.yun.tankboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Callendar extends AppCompatActivity {

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    public  FirebaseDatabase mFirebaseDatabase;

    // 시간관련 데이터
    long nowTimeInt;
    // 한달기준 시간을 잡아준다.
    long monthTimeInt;
    long dayTimeInt;

    // 한달 누적량 체크
    private ArrayList<Double> hourList;

    // 시스템으로부터 현재시간(ms) 가져오기
    private long now;
    Date date;
    SimpleDateFormat sdfNow;
    String nowTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callendar);


        // Firebase
        mFirebaseDatabase = mFirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("users/" + mAuth.getUid() + "/meters");

        // 시간데이터들 초기화
        now = System.currentTimeMillis();
        date = new Date(now);
        sdfNow = new SimpleDateFormat("yyyyMMddHHmmss");
        nowTime = sdfNow.format(date);

        long adj = 10000000000L;
        // 현재시간에 1년을 빼준다.
        nowTimeInt = Long.parseLong(nowTime) - adj;

        // 한달기준 시간을 잡아준다.
        monthTimeInt = nowTimeInt/100000000;
        monthTimeInt = monthTimeInt * 100000000;

        // 하루전날기준 시간을 잡아준다.
        dayTimeInt = nowTimeInt / 1000000;
        dayTimeInt = dayTimeInt * 1000000;
        final int clickedDay;

        // 버튼 이벤트
        final Button button04 = (Button) findViewById(R.id.button4);
        button04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnedayUse(20180801000000L,20180802000000L,hourList);
                drawChart(hourList);
            }
        });
    }

    // 하루 누적 사용량 계산
    private void showOnedayUse(final long compareInt, final long nowTimeLong, final ArrayList<Double> hourList){
        mDatabaseReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Long key = Long.parseLong(dataSnapshot.getKey());
                        if(compareInt <= key
                                && key <= nowTimeLong ){
                            if(key % 10000 == 0){
                                Meter meter = dataSnapshot.getValue(Meter.class);
                                hourList.add((double)meter.getAccConsumption());
                            }
                        }
                    }
                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {}
                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
                    @Override
                    public void onCancelled(DatabaseError databaseError) {}
                });
    }

    private void drawChart(ArrayList<Double> list){

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

