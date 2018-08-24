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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    public static String clientNumber;
    public static String userName;

    private FirebaseAuth mAuth;
    private static FirebaseDatabase mFirebaseDatabase;
    private FirebaseUser mFirebaseUser;
    private DatabaseReference mDatabaseReference;

    private User user;

    static{
        mFirebaseDatabase = mFirebaseDatabase.getInstance();
        mFirebaseDatabase.setPersistenceEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 파이어 베이스 인증 객체
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference =  mFirebaseDatabase.getReference("users/");

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
                            mFirebaseUser = mAuth.getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            // Toast.makeText(Login.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            System.out.println("mFirebaseUser Uid = " + mFirebaseUser.getUid());



                            Intent intent = new Intent(Login.this, Select.class);
                            startActivity(intent);
                            getUserName();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Login.this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });
    }
    private  void getUserName(){
        mDatabaseReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if(mFirebaseUser.getUid().equals(dataSnapshot.getKey())){
                            User user = dataSnapshot.getValue(User.class);
                            userName = user.getUsername();
                            Toast.makeText(Login.this, userName + "님 안녕하세요", Toast.LENGTH_SHORT).show();
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
}