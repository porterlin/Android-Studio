package com.cornez.shippingcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class IdealWeightActivity extends Activity {
    private Button appintro;
    private Button inputinfo;
    Double weight;
    Double w1;
    Double w2;
    Double cal;
    TextView rststdweight;
    TextView rstweightrge;
    TextView rstdaycal;

    String oriheight;
    String oriweight;
    String oriactivity;
    String knee;
    String age;
    Boolean gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ideal_weight);

        appintro = (Button) findViewById(R.id.appintro1);
        appintro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appintro();
            }
        });

        inputinfo = (Button) findViewById(R.id.inputinfo);
        inputinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputinfo();
            }
        });
        rststdweight = findViewById(R.id.rststdweight);
        rstweightrge = findViewById(R.id.rstweightrge);
        rstdaycal = findViewById(R.id.rstdaycal);

        Intent intent = getIntent();
        weight = intent.getDoubleExtra("weight", 0);
        w1 = intent.getDoubleExtra("w1", 0);
        w2 = intent.getDoubleExtra("w2", 0);
        cal = intent.getDoubleExtra("cal", 0);

        oriheight = intent.getStringExtra("oriheight");
        oriweight = intent.getStringExtra("oriweight");
        oriactivity = intent.getStringExtra("oriactivity");
        age = intent.getStringExtra("age");
        knee = intent.getStringExtra("knee");
        gender = intent.getBooleanExtra("gender", true);

        rststdweight.setText(String.format("%.1f", weight) + " 公斤");
        rstweightrge.setText(String.format("%.1f", w1) + " ~ " + String.format("%.1f", w2));
        rstdaycal.setText(String.format("%.0f", cal) + " 大卡");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("sw", weight);
        outState.putDouble("sw1", w1);
        outState.putDouble("sw2", w2);
        outState.putDouble("scal", cal);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            weight = savedInstanceState.getDouble("sw");
            w1 = savedInstanceState.getDouble("sw1");
            w2 = savedInstanceState.getDouble("sw2");
            cal = savedInstanceState.getDouble("scal");
            rststdweight.setText(String.format("%.1f", weight) + " 公斤");
            rstweightrge.setText(String.format("%.1f", w1) + " ~ " + String.format("%.1f", w2));
            rstdaycal.setText(String.format("%.0f", cal) + " 大卡");
        }
    }

    public void appintro() {
        Intent intent = new Intent(this, AppIntroActivity.class);
        intent.putExtra("weight", weight);
        intent.putExtra("w1", w1);
        intent.putExtra("w2", w2);
        intent.putExtra("cal", cal);

        intent.putExtra("oriheight", oriheight);
        intent.putExtra("oriweight", oriweight);
        intent.putExtra("oriactivity", oriactivity);
        intent.putExtra("knee", knee);
        intent.putExtra("age", age);
        intent.putExtra("gender", gender);

        startActivity(intent);
        overridePendingTransition(R.anim.transition_right_in, R.anim.transition_right_out);
    }

    public void inputinfo() {
        Intent intent = new Intent(this, MyActivity.class);
        intent.putExtra("weight", weight);
        intent.putExtra("w1", w1);
        intent.putExtra("w2", w2);
        intent.putExtra("cal", cal);

        intent.putExtra("oriheight", oriheight);
        intent.putExtra("oriweight", oriweight);
        intent.putExtra("oriactivity", oriactivity);
        intent.putExtra("knee", knee);
        intent.putExtra("age", age);
        intent.putExtra("gender", gender);

        startActivity(intent);
        overridePendingTransition(R.anim.transition_left_in, R.anim.transition_left_out);
    }
}