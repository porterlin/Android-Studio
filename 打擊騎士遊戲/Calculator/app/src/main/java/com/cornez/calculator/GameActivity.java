package com.cornez.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.cornez.calculator.databinding.ActivityGameBinding;

public class GameActivity extends Activity {

    private SeekBar seekBar;
    private TextView seekBarHint;
    private Button newGameBtn;
    private Button continueBtn;
    private Button quitBtn;
    private int level = 2;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        seekBarHint = findViewById(R.id.seekbarHint);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                level = (progress + 1) % 4;
                seekBarHint.setText(String.valueOf(level));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        newGameBtn = findViewById(R.id.newGame);
        continueBtn = findViewById(R.id.continueGame);
        quitBtn = findViewById(R.id.quit);

        newGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newGameJump();
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                continueGameJump();
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finishAffinity();
            }
        });

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            level = bundle.getInt("level");
            seekBar.setProgress(level - 1);
            seekBarHint.setText(String.valueOf(level));
        }
    }

    public void newGameJump() {
        Intent intent = new Intent(this, MyActivity.class);
        bundle = new Bundle();
        bundle.putInt("level", level);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    public void continueGameJump() {
        if (bundle != null) {
            Intent intent = new Intent(this, MyActivity.class);
            bundle.putInt("level", level);
            intent.putExtra("bundle", bundle);
            startActivity(intent);
        }
    }
}