package com.example.yun.tankboy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class Select extends AppCompatActivity {

    // 각 가전 제품 map (key-이름 , value-소비전력)
    public static Map<String, Integer> homeAppliancesList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        // 가전 제품 선택 설명
        TextView selectExplainTextView = (TextView) findViewById(R.id.SelectExplainTextView);
        selectExplainTextView.setTextSize(Intro.width_pixel / 60);

        // 에어컨 체크박스, 소비전력
        final CheckBox airConditionalCheckBox = (CheckBox) findViewById(R.id.AirConditionalCheckBox);
        airConditionalCheckBox.setTextSize(Intro.width_pixel / 55);

        final TextView airConditionalTextView = (TextView) findViewById(R.id.AirConditionalTextView);
        airConditionalTextView.setTextSize(Intro.width_pixel / 60);

        final EditText airConditionalEditText = (EditText) findViewById(R.id.AirConditionalEditText);
        airConditionalEditText.setTextSize(Intro.width_pixel / 60);

        // 히터 체크박스, 소비전력
        final CheckBox heaterCheckBox = (CheckBox) findViewById(R.id.HeaterCheckBox);
        heaterCheckBox.setTextSize(Intro.width_pixel / 55);

        final TextView heaterTextView = (TextView) findViewById(R.id.HeaterTextView);
        heaterTextView.setTextSize(Intro.width_pixel / 60);

        final EditText heaterEditText = (EditText) findViewById(R.id.HeaterEditText);
        heaterEditText.setTextSize(Intro.width_pixel / 60);

        // 가습기 체크박스, 소비전력
        final CheckBox humidifierCheckBox = (CheckBox) findViewById(R.id.HumidifierCheckBox);
        humidifierCheckBox.setTextSize(Intro.width_pixel / 55);

        final TextView humidifierTextView = (TextView) findViewById(R.id.HumidifierTextView);
        humidifierTextView.setTextSize(Intro.width_pixel / 60);

        final EditText humidifierEditText = (EditText) findViewById(R.id.HumidifierEditText);
        humidifierEditText.setTextSize(Intro.width_pixel / 60);

        // 제습기 체크박스, 소비전력
        final CheckBox dehumidifierCheckBox = (CheckBox) findViewById(R.id.DehumidifierCheckBox);
        dehumidifierCheckBox.setTextSize(Intro.width_pixel / 55);

        final TextView dehumidifierTextView = (TextView) findViewById(R.id.DehumidifierTextView);
        dehumidifierTextView.setTextSize(Intro.width_pixel / 60);

        final EditText dehumidifierEditText = (EditText) findViewById(R.id.DehumidifierEditText);
        dehumidifierEditText.setTextSize(Intro.width_pixel / 60);

        // 선풍기 체크박스, 소비전력
        final CheckBox fanCheckBox = (CheckBox) findViewById(R.id.FanCheckBox);
        fanCheckBox.setTextSize(Intro.width_pixel / 55);

        final TextView fanTextView = (TextView) findViewById(R.id.FanTextView);
        fanTextView.setTextSize(Intro.width_pixel / 60);

        final EditText fanEditText = (EditText) findViewById(R.id.FanEditText);
        fanEditText.setTextSize(Intro.width_pixel / 60);

        // 냉장고 체크박스, 소비전력
        final CheckBox refrigeratorCheckBox = (CheckBox) findViewById(R.id.RefrigeratorCheckBox);
        refrigeratorCheckBox.setTextSize(Intro.width_pixel / 55);

        final TextView refrigeratorTextView = (TextView) findViewById(R.id.RefrigeratorTextView);
        refrigeratorTextView.setTextSize(Intro.width_pixel / 60);

        final EditText refrigeratorEditText = (EditText) findViewById(R.id.RefrigeratorEditText);
        refrigeratorEditText.setTextSize(Intro.width_pixel / 60);

        // 김치 냉장고 체크박스, 소비전력
        final CheckBox kimchiRefriCheckBox = (CheckBox) findViewById(R.id.KimchiRefriCheckBox);
        kimchiRefriCheckBox.setTextSize(Intro.width_pixel / 60);

        final TextView kimchiRefriTextView = (TextView) findViewById(R.id.KimchiRefriTextView);
        kimchiRefriTextView.setTextSize(Intro.width_pixel / 60);

        final EditText kimchiRefriEditText = (EditText) findViewById(R.id.KimchiRefriEditText);
        kimchiRefriEditText.setTextSize(Intro.width_pixel / 60);

        // 형광등 체크박스, 소비전력
        final CheckBox lampCheckBox = (CheckBox) findViewById(R.id.LampCheckBox);
        lampCheckBox.setTextSize(Intro.width_pixel / 55);

        final TextView lampTextView = (TextView) findViewById(R.id.LampTextView);
        lampTextView.setTextSize(Intro.width_pixel / 60);

        final EditText lampEditText = (EditText) findViewById(R.id.LampEditText);
        lampEditText.setTextSize(Intro.width_pixel / 60);

        // 입력 버튼 이벤트 처리
        Button inputSelectButton = (Button)findViewById(R.id.InputSelectButton);
        inputSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 가전제품이 체크되어 있으면 map에 추가
                addHomeAppliancesList(airConditionalCheckBox, airConditionalEditText);
                addHomeAppliancesList(heaterCheckBox, heaterEditText);
                addHomeAppliancesList(humidifierCheckBox, humidifierEditText);
                addHomeAppliancesList(dehumidifierCheckBox, dehumidifierEditText);
                addHomeAppliancesList(fanCheckBox, fanEditText);
                addHomeAppliancesList(refrigeratorCheckBox, refrigeratorEditText);
                addHomeAppliancesList(kimchiRefriCheckBox, kimchiRefriEditText);
                addHomeAppliancesList(lampCheckBox, lampEditText);

                // mainActivity로 전환
                Intent intent = new Intent(Select.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    protected void addHomeAppliancesList(CheckBox c, EditText e){
        if(c.isChecked()){
            homeAppliancesList.put(c.getText().toString(), Integer.parseInt(e.getText().toString()));
        }
        c.setChecked(false);
    }
}
