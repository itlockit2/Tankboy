package com.example.yun.tankboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
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
                // 차트그리기
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Callendar.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

