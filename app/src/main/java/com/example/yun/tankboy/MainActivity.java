package com.example.yun.tankboy;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.Call;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity{

    private static final String TAG = "MainActivity";

    private LineChart mChart;

    private DatabaseReference mDatabaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser mFirebaseUser;


    // 현재 월
    public static int currentMonth = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mAuth.getCurrentUser();
        mDatabaseReference = Intro.mFirebaseDatabase.getReference("users/" + mAuth.getUid() + "/meters");

        // 메인 엑티비티 설명
        TextView mainExplainTextView = (TextView) findViewById(R.id.MainExplainTextView);
        mainExplainTextView.setTextSize(Intro.width_pixel/30);
        mainExplainTextView.setText(Login.userName + " 님의 " + "시간" + " 기준 전력 컨설팅");

        // 전구 이미지
        ImageView mainLightColorImageView = (ImageView)findViewById(R.id.MainLightColorImageView);

        // 전구 이미지 설명
        TextView mainLightExplainTextView = (TextView)findViewById(R.id.MainLightExplainTextView);
        mainLightExplainTextView.setTextSize(Intro.width_pixel/40);
        mainLightExplainTextView.setText("8월" + "누적 사용량 : " + "1000");

        // 개인별 상황 보여주기
        TextView recommendedDailyTextView = (TextView)findViewById(R.id.RecommendedDailyTextView);
        recommendedDailyTextView.setTextSize(Intro.width_pixel/30);
        recommendedDailyTextView.setText("하루 권장량 : " + "11");

        TextView todayValueTextView = (TextView)findViewById(R.id.TodayValueTextView);
        todayValueTextView.setTextSize(Intro.width_pixel/30);
        todayValueTextView.setText("오늘 하루 사용량 : " + "415");

        TextView temperatureTextView = (TextView)findViewById(R.id.TemperatureTextView);
        temperatureTextView.setTextSize(Intro.width_pixel/30);
        temperatureTextView.setText("기온 : " + "22 ");

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
                Intent intent = new Intent(MainActivity.this, Callendar.class);
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
