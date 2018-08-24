package com.example.yun.tankboy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 고객 번호 입력
        final EditText inputCustomerNumberEditText = (EditText)findViewById(R.id.InputCustomerNumberEditText);

        // 고객 번호 입력 버튼
        Button inputCustomerNumberButton = (Button)findViewById(R.id.InputCustomerNumberButton);
        inputCustomerNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Select.class);
                intent.putExtra("customerNumber", inputCustomerNumberEditText.getText().toString());
                startActivity(intent);
            }
        });
    }
}
