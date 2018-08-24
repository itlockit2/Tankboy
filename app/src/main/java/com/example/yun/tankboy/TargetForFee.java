package com.example.yun.tankboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Iterator;

public class TargetForFee extends AppCompatActivity {

    // 목표 금액
    public static int targetForFee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target_for_fee);

        // 로그인 설명
        TextView targetExplainTextView = (TextView) findViewById(R.id.TargetExplainTextView);
        targetExplainTextView.setTextSize(Intro.width_pixel / 70);

        // 고객 번호 입력
        final EditText inputTargetEditText = (EditText)findViewById(R.id.InputTargetEditText);
        inputTargetEditText.getLayoutParams().width = (int)(Intro.width_pixel * 0.3);

        // 입력 버튼 이벤트 처리
        final Button inputTargetButton = (Button) findViewById(R.id.InputTargetButton);
        inputTargetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                targetForFee = Integer.parseInt(inputTargetEditText.getText().toString());
                Intent intent = new Intent(TargetForFee.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
