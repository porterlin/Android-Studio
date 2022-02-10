package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.wajahatkarim3.easyflipview.EasyFlipView;

public class DataShow extends AppCompatActivity {
    TextView dataShow;
    String detail;
    TextView frontFlipCard;
    TextView backFlipCard;
    EasyFlipView flipView;
    int index;
    boolean c = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);
        dataShow = findViewById(R.id.datashow);

        Intent intent = getIntent();
        detail = intent.getStringExtra("detail");
        index = intent.getIntExtra("i", 0);

        dataShow.setText(detail);

        frontFlipCard = findViewById(R.id.front);
        backFlipCard = findViewById(R.id.back);
        flipView = findViewById(R.id.flipCard);

        frontFlipCard.setText(DataInformation.question[index]);
        backFlipCard.setText(DataInformation.answer[index]);
        flipView.flipTheView();
        flipView.setOnFlipListener(new EasyFlipView.OnFlipAnimationListener() {
            @Override
            public void onViewFlipCompleted(EasyFlipView flipView, EasyFlipView.FlipState newCurrentSide) {
                if (c == false) {
                    frontFlipCard.setText(DataInformation.question[index]);
                    c = true;
                }
                else {
                    backFlipCard.setText(DataInformation.answer[index]);
                    c = false;
                }
            }
        });

        /*flipView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    if (c == false) {
                        frontFlipCard.setText(DataInformation.question[index]);
                        c = true;
                    }
                    else {
                        backFlipCard.setText(DataInformation.answer[index]);
                        c = false;
                    }
                    return true;
                }
                return false;
            }
        });*/
    }
}