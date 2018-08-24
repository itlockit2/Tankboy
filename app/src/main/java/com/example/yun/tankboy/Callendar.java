package com.example.yun.tankboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Callendar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_callendar);
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Callendar.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
