package com.example.yun.tankboy;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Intro extends AppCompatActivity {

    // 화면 크기
    public static float width_pixel;
    public static float height_pixel;
    public static float width_dp;
    public static float height_dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // 화면 크기 구하기
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width_pixel = size.x;
        height_pixel = size.y;

        // 전력 컨설팅 TextView
        TextView titleTextView = (TextView) findViewById(R.id.TitleTextVeiw);
        titleTextView.setTextSize(width_pixel / 30);

        // 전구 ImageView
        ImageButton introButton = (ImageButton) findViewById(R.id.IntroButton);
        introButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intro.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        // 설명 TextView
        TextView introExplainTextView = (TextView) findViewById(R.id.IntroExplainTextView);
        introExplainTextView.setTextSize(width_pixel / 60);

    }
}
