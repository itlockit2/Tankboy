package com.example.yun.tankboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TimeChange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_change);

        EditText TimeEdit = (EditText) findViewById(R.id.TimeEdit);
        Button timeInput = (Button) findViewById(R.id.TimeInput);

        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TimeChange.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
