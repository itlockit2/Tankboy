package com.example.yun.tankboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private String clientNumber;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 파이어 베이스 인증 객체
        mAuth = FirebaseAuth.getInstance();

        // 로그인 설명
        TextView loginExplainTextView = (TextView) findViewById(R.id.LoginExplainTextView);
        loginExplainTextView.setTextSize(Intro.width_pixel / 70);

        // 고객 번호 입력
        final EditText inputCustomerNumberEditText = (EditText)findViewById(R.id.InputCustomerNumberEditText);
        inputCustomerNumberEditText.getLayoutParams().width = (int)(Intro.width_pixel * 0.3);

        // 고객 번호 입력 버튼
        Button inputCustomerNumberButton = (Button)findViewById(R.id.InputCustomerNumberButton);
        inputCustomerNumberButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 고객번호 뒤에 이메일형식을 맞춰주기 위해서 @gmail.com 을 추가해준다.
                clientNumber = inputCustomerNumberEditText.getText().toString() + "@gmail.com";
                // 패스워드는 임의로 지정
                String password = "12341234";
                login(clientNumber,password);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Login.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Login.this, Select.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
}
