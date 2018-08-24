package com.example.yun.tankboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private LineChart mChart;

    private DatabaseReference mDatabaseReference;
    private DatabaseReference weatherDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;

    // 소숫점 표현
    DecimalFormat form;

    // 누적사용량 설명
    private TextView mainLightExplainTextView;
    // 전구 설명
    TextView currentConsumePercentTextView;
    // 전구 채우기
    LinearLayout.LayoutParams subFillLayout;
    // 오늘 하루 사용량
    TextView todayValueTextView;
    // 개인권장량
    TextView recommendedDailyTextView;
    // 기온뷰
    TextView temperatureTextView;

    double targetForFee;

    // 시스템으로부터 현재시간(ms) 가져오기
    private long now;
    Date date;
    SimpleDateFormat sdfNow;
    String nowTime;

    // 한달 누적량 체크
    private ArrayList<Integer> monthList;
    // 하루 전날까지의 누적량 체크
    private ArrayList<Integer> dayList;

    // 현재 월
    public static int currentMonth = 0;

    // 전력량을 금액으로 금액을 전력량으로 변환하기 위한 클래스
    Convert mConvert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        form = new DecimalFormat("#.##");

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mDatabaseReference = Intro.mFirebaseDatabase.getReference("users/" + mAuth.getUid() + "/meters");
        weatherDatabaseReference = Intro.mFirebaseDatabase.getReference("weathers/" + Login.userLivingCode);

        // Convert
        mConvert = new Convert();

        // 목표 사용량
        targetForFee =  Double.parseDouble(mConvert.reverse_won( Integer.toString(TargetForFee.targetForFee) ));

        // 시간데이터들 초기화
        now = System.currentTimeMillis();
        date = new Date(now);
        sdfNow = new SimpleDateFormat("yyyyMMddHHmmss");
        nowTime = sdfNow.format(date);
        monthList = new ArrayList<>();
        dayList = new ArrayList<>();

        long adj = 10000000000L;
        // 현재시간에 1년을 빼준다.
        long nowTimeInt = Long.parseLong(nowTime) - adj;

        // 한달기준 시간을 잡아준다.
        long monthTimeInt = nowTimeInt/100000000;
        monthTimeInt = monthTimeInt * 100000000;

        // 하루전날기준 시간을 잡아준다.
        long dayTimeInt = nowTimeInt / 1000000;
        dayTimeInt = dayTimeInt * 1000000;

        // 하루그날부터 지금시간까지의 합
        showOnedayUse(dayTimeInt, nowTimeInt);
        // 한달시작부터 지금시간까지의 합
        showMonthUse(monthTimeInt,nowTimeInt);
        // 한달시작부터 전날까지의 합
        showOnedayRecommend(monthTimeInt,dayTimeInt);
        // 하루시작부터 지금시간까지로 탐색
        showTemperature(dayTimeInt, nowTimeInt);

        // 메인 엑티비티 설명
        TextView mainExplainTextView = (TextView) findViewById(R.id.MainExplainTextView);
        mainExplainTextView.setTextSize(Intro.width_pixel/30);
        mainExplainTextView.setText(Login.userName + " 님의 " + "시간" + " 기준 전력 컨설팅");

        // 전구 색 채우기
        ImageView mainLightColorImageView = (ImageView)findViewById(R.id.MainLightColorImageView);
        LinearLayout.LayoutParams mainLightColorLayout = (LinearLayout.LayoutParams)mainLightColorImageView.getLayoutParams();
        mainLightColorLayout.weight = 152;

        TextView subFill = (TextView)findViewById(R.id.SubFill);
        subFillLayout = (LinearLayout.LayoutParams)subFill.getLayoutParams();
;
        double currentConsumePercent = 20.0;
        subFillLayout.weight = (float) (1.93 * (100.0-currentConsumePercent));
        currentConsumePercentTextView = (TextView)findViewById(R.id.CurrentConsumePercentTextView);

        Animation fillLightAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fill);
        mainLightColorImageView.startAnimation(fillLightAnimation);

        // 전구 이미지 설명
        mainLightExplainTextView = (TextView)findViewById(R.id.MainLightExplainTextView);

        // 개인별 상황 보여주기
        recommendedDailyTextView = (TextView)findViewById(R.id.RecommendedDailyTextView);
        recommendedDailyTextView.setTextSize(Intro.width_pixel/30);

        todayValueTextView = (TextView)findViewById(R.id.TodayValueTextView);
        updateTodayUse(30);


        temperatureTextView = (TextView)findViewById(R.id.TemperatureTextView);
        temperatureTextView.setTextSize(Intro.width_pixel/30);

        // 오늘의 팁 버튼
        Button todayTipButton = (Button)findViewById(R.id.TodayTipButton);
        todayTipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TodayTip.class);
                startActivity(intent);
                finish();
            }
        });

        // 달력 버튼
        ImageButton callendarButton = (ImageButton) findViewById(R.id.CallendarButton);
        callendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Callendar.class);
                startActivity(intent);
                finish();
            }
        });

        // 비교 버튼
        ImageButton compareButton = (ImageButton) findViewById(R.id.CompareButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Compare.class);
                startActivity(intent);
                finish();
            }
        });

        // 팁 버튼
        ImageButton tipButton = (ImageButton) findViewById(R.id.TipButton);
        tipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Tip.class);
                startActivity(intent);
                finish();
            }
        });



/*
        // "고객명님의 몇 월 정보입니다" TextView
        TextView currentConsumeExplainTextView = (TextView)findViewById(R.id.CurrentConsumeExplainTextView);
        currentConsumeExplainTextView.setText(Login.userName + "님의 " + currentMonth + "월의 소비 전력량");
        currentConsumeExplainTextView.setTextSize(Intro.width_pixel / 70);
        // 전구 색 칠하기
        ImageView mainLightImageView = (ImageView) findViewById(R.id.MainLightImageView);
        mainLightImageView.getLayoutParams().width = (int) (Intro.width_pixel);
        mainLightImageView.getLayoutParams().height = (int) (Intro.height_pixel * 0.5);

        ImageView mainLightColorImageView = (ImageView) findViewById(R.id.MainLightColorImageView);
        mainLightColorImageView.getLayoutParams().width = (int) (Intro.width_pixel);
        mainLightColorImageView.getLayoutParams().height = (int)(Intro.height_pixel * 0.5 * 0.9 * 0.7);
        mainLightColorImageView.requestLayout();

        Animation fillLightAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fill);
        mainLightColorImageView.startAnimation(fillLightAnimation);

        // 현재 사용량 표시 TextVeiw
        TextView currentConsumeTextView = (TextView) findViewById(R.id.CurrentConsumeTextView);
        currentConsumeExplainTextView.setTextSize(Intro.width_pixel / 70);
        getConsume();
*/
    }
    // 기온 업데이트
    private void showTemperature(final long compareInt,final long nowTimeLong){
        System.out.println("compareInt = " + compareInt);
        System.out.println("nowTimeLong = " + nowTimeLong);
        weatherDatabaseReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        // 최신시간 온도 탐색
                        if(compareInt <= Long.parseLong(dataSnapshot.getKey())
                                && Long.parseLong(dataSnapshot.getKey()) <= nowTimeLong ){
                            System.out.println("Long.parseLong(dataSnapshot.getKey()) = " + Long.parseLong(dataSnapshot.getKey()));
                            Weather weather = dataSnapshot.getValue(Weather.class);
                            updateTemperature(weather.getTemperature());
                            System.out.println("weather.getTemperature())" + weather.getTemperature());
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

    // 하루 권장량 사용량 계산
    private void showOnedayRecommend(final long compareInt, final long nowTimeLong){
        mDatabaseReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(compareInt <= Long.parseLong(dataSnapshot.getKey())
                                && Long.parseLong(dataSnapshot.getKey()) <= nowTimeLong ){
                            Meter meter = dataSnapshot.getValue(Meter.class);
                            addList(dayList,meter.getCurConsumption());
                            updateOnedayRecommend();
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
    // 하루 누적 사용량 계산
    private void showOnedayUse(final long compareInt, final long nowTimeLong){
        mDatabaseReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(compareInt <= Long.parseLong(dataSnapshot.getKey())
                                && Long.parseLong(dataSnapshot.getKey()) <= nowTimeLong ){
                            Meter meter = dataSnapshot.getValue(Meter.class);
                            updateTodayUse(meter.getAccConsumption());;
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
    // 한달 사용량을 계산해준다.
    private void showMonthUse(final long compareInt, final long nowTimeLong){
        mDatabaseReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(compareInt <= Long.parseLong(dataSnapshot.getKey())
                                && Long.parseLong(dataSnapshot.getKey()) <= nowTimeLong ){
                            Meter meter = dataSnapshot.getValue(Meter.class);
                            addList(monthList,meter.getCurConsumption());
                            updateMainScreen();
                            updateColorFill();
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
    // 기온 데이터
    private void updateTemperature(double temp){
        temperatureTextView.setText("기온 : " + form.format(temp));
    }

    // 오늘 하루 사용량
    private void updateTodayUse(int todayUser){
        double todayUse = todayUser / 1000.0;
        todayValueTextView.setText("오늘 하루 사용량 : " + form.format(todayUse) + "kw");
        todayValueTextView.setTextSize(Intro.width_pixel/30);
    }

    // 하루권장량 계산
    private void updateOnedayRecommend(){
        // 그 전날까지의 총 사용량
        int dayUserData = getSumList(dayList);
        // 남은날 계산
        int month =  Integer.parseInt(nowTime.substring(4,6));
        int maxDay = 0;
        if(month == 2 || month == 4 || month == 6 ||month == 9 ||month == 11){
            maxDay = 30;
        } else {
            maxDay = 31;
        }
        // 따라서 남은날은 maxDay - 현재날짜
        int remainDay = maxDay - Integer.parseInt(nowTime.substring(6,8));
        // 하루 권장량은 한달 권장량 - 그 절날까지의 총 사용량 / 남은날
        double onedayRecommend = (targetForFee - dayUserData) / (remainDay * 1000);
        recommendedDailyTextView.setText("하루 권장량 : " + form.format(onedayRecommend) + "kw");
        System.out.println("TagetForFee = " + targetForFee);
        System.out.println("dayUserData = " + dayUserData);
    }

    // 전구의 색 충전
    private void updateColorFill(){
        int monthUserData = getSumList(monthList);
        double currentConsumePercent = monthUserData/ targetForFee * 100;
        subFillLayout.weight = (float) (1.93 * (100.0-currentConsumePercent));
        currentConsumePercentTextView.setText((int)currentConsumePercent + "%");
        currentConsumePercentTextView.setTextSize(Intro.width_pixel / 35);
    }

    // 누적 사용량 측정 후 업데이트
    private void updateMainScreen(){
        int monthUserData = getSumList(monthList);
        mainLightExplainTextView.setTextSize(Intro.width_pixel/40);
        mainLightExplainTextView.setText("8월" + "누적 사용량 : " + form.format((monthUserData/1000)) + "kw");
    }

    private void addList(ArrayList<Integer> list, int number){
        list.add(number);
    }

    private int getSumList(ArrayList<Integer> list){
        int sum = 0;
        for(int i = 0 ; i < list.size() ; i++){
            sum = sum + list.get(i);
        }
        return sum;
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

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(MainActivity.this, TargetForFee.class);
        startActivity(intent);
        finish();
    }

}
