package com.example.liuyifan_weather_task4_0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.example.liuyifan_weather_task4_0.utility.PinyinUtils;

public class PinyinActivity extends AppCompatActivity {
    TextView tv1,tv2;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        tv1=findViewById(R.id.tv_pinyin1);
        tv2=findViewById(R.id.tv_pinyin2);
        et=findViewById(R.id.et_pinyin);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                String s1 = s.toString();
                String pinyin = PinyinUtils.toPinyin(s1);
                String pinyin2 = PinyinUtils.toPinyinFirstLetter(s1);
                tv1.setText(pinyin);
                tv2.setText(pinyin2);
            }
        });
    } }