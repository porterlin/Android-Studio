package com.cornez.shippingcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AppIntroActivity extends Activity {
    private Button idealweightbtn;
    private Button inputinfo;
    private Button exit;
    Double weight;
    Double w1;
    Double w2;
    Double cal;

    String oriheight;
    String oriweight;
    String oriactivity;
    String knee;
    String age;
    Boolean gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_intro);

        idealweightbtn = (Button) findViewById(R.id.idealweight1);
        idealweightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idealweight();
            }
        });

        inputinfo = (Button) findViewById(R.id.inputinfo1);
        inputinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputinfo();
            }
        });

        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

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
    }

    public void idealweight() {
        Intent intent = new Intent(this, IdealWeightActivity.class);
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
        overridePendingTransition(R.anim.transition_right_in, R.anim.transition_right_out);
    }

    public void exit() {
        finishAffinity();
    }
}