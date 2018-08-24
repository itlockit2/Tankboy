package com.example.yun.tankboy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Select extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // 가전 제품 선택 설명
        TextView selectExplainTextView = (TextView) findViewById(R.id.SelectExplainTextView);
        selectExplainTextView.setTextSize(Intro.width_pixel / 60);
    }
}
