package com.example.yun.tankboy;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Intro extends AppCompatActivity {

    // 화면 크기
    public static float width_pixel;
    public static float height_pixel;
    public static float width_dp;
    public static float height_dp;

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;

    static {
        mFirebaseDatabase = mFirebaseDatabase.getInstance();
        mFirebaseDatabase.setPersistenceEnabled(true);
        mDatabaseReference = mFirebaseDatabase.getReference("users/");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mDatabaseReference
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        System.out.println("Test get Key  = " + dataSnapshot.getKey());
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
