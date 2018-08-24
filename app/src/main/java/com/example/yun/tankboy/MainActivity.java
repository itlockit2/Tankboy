package com.example.yun.tankboy;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    // 현재 월
    public static int currentMonth = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }


}
