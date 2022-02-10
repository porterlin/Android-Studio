package com.cornez.calculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import java.lang.*;

import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.Gravity;


public class MyActivity extends Activity {

    private int[][] walk = new int[8][8];
    private int[] check = new int[8];
    private int checkIndex = 0;
    private TextView show;
    private TextView place;
    private int r;
    private int c;
    private int next;
    Button rst;
    Handler hand = new Handler();

    private int level = 2;
    private int[] speed = {0, 2000, 1000, 500};
    public int score = 0;
    public double hit = 0;
    public int miss = 0;
    public int red = 1;
    private TextView rstScore;
    private TextView rstHit;
    private TextView rstMiss;
    private boolean clickBool = false;
    private boolean over = false;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < 8; i++) {
            TableRow tableRow = new TableRow(this);
            for (int j = 0; j < 8; j++) {
                TextView textView = new TextView(this);
                textView.setLayoutParams(rowParams);
                textView.setText("");
                textView.setId(Integer.valueOf((i * 10 + j)));
                textView.setHeight(125);
                textView.setWidth(125);
                textView.setGravity(Gravity.CENTER);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textViewClick(view, textView);
                    }
                });
                if ((i + j) % 2 == 0)
                    textView.setBackgroundResource(R.drawable.textview_border_blue);
                else
                    textView.setBackgroundResource(R.drawable.textview_border_white);

                tableRow.addView(textView);
            }
            tableLayout.addView(tableRow);
        }

        rst = (Button)findViewById(R.id.restart);
        show = findViewById(R.id.show);
        rstScore = findViewById(R.id.score);
        rstHit = findViewById(R.id.hit);
        rstMiss = findViewById(R.id.miss);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            if (bundle.containsKey("walk"))
                recover(bundle);
            else {
                level = bundle.getInt("level");
                r = (int) (Math.random() * 8);
                c = (int) (Math.random() * 8);
                place = findViewById(r * 10 + c);
                place.setText("@");
                show.setText("位置: " + r + ", " + c);
            }
        }

        hand.postDelayed(run, speed[level]);
    }

    public void recover(Bundle bundle) {
        over = bundle.getBoolean("over");
        level = bundle.getInt("level");
        walk = (int[][]) bundle.getSerializable("walk");
        r = bundle.getInt("r");
        c = bundle.getInt("c");
        score = bundle.getInt("score");
        hit = bundle.getDouble("hit");
        miss = bundle.getInt("miss");

        if (!arrayIsAllZero(walk)) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (walk[i][j] == 1) {
                        if (i == r && j == c && over)
                            continue;
                        place = findViewById(i * 10 + j);
                        place.setBackgroundResource(R.drawable.textview_border_red);
                    }
                }
            }
        }

        place = findViewById(r * 10 + c);
        place.setText("@");
        show.setText("位置: " + r + ", " + c);

        rstScore.setText("Score: " + score);
        rstHit.setText("Hit: " + (int)hit);
        rstMiss.setText("Miss: " + miss);
        clickBool = bundle.getBoolean("clickBool");
        red = bundle.getInt("red");
    }

    public boolean arrayIsAllZero(int[][] buf) {
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (buf[i][j] != 0)
                    return false;

        return true;
    }

    public void textViewClick(View v, TextView textView) {
        if (over)
            return;

        int tc = Integer.valueOf(textView.getId()) % 10;
        int tr = (Integer.valueOf(textView.getId()) - tc) / 10;

        if (tr == r && tc == c) {
            if (!clickBool) {
                hit++;
                clickBool = true;
            }
        }
        else
            miss++;

        score = Math.max(((int)(Math.ceil((hit/red) * 100)) - miss), 0);
        rstScore.setText("Score: " + score);
        rstHit.setText("Hit: " + (int)hit);
        rstMiss.setText("Miss: " + miss);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (r-2 >= 0 && c-1 >= 0 && walk[r-2][c-1] == 0)
                check[checkIndex++] = (r-2) * 10 + (c-1);
            if (r-2 >= 0 && c+1 < 8 && walk[r-2][c+1] == 0)
                check[checkIndex++] = (r-2) * 10 + (c+1);
            if (r-1 >= 0 && c+2 < 8 && walk[r-1][c+2] == 0)
                check[checkIndex++] = (r-1) * 10 + (c+2);
            if (r+1 < 8 && c+2 < 8 && walk[r+1][c+2] == 0)
                check[checkIndex++] = (r+1) * 10 + (c+2);
            if (r+2 < 8 && c+1 < 8 && walk[r+2][c+1] == 0)
                check[checkIndex++] = (r+2) * 10 + (c+1);
            if (r+2 < 8 && c-1 >= 0 && walk[r+2][c-1] == 0)
                check[checkIndex++] = (r+2) * 10 + (c-1);
            if (r+1 < 8 && c-2 >= 0 && walk[r+1][c-2] == 0)
                check[checkIndex++] = (r+1) * 10 + (c-2);
            if (r-1 >= 0 && c-2 >= 0 && walk[r-1][c-2] == 0)
                check[checkIndex++] = (r-1) * 10 + (c-2);

            walk[r][c] = 1;
            if (checkIndex == 0) {
                hand.removeCallbacks(run);
                over = true;
                return;
            }
            red++;

            next = (int)(Math.random()*(checkIndex));
            r = check[next] / 10;
            c = check[next] % 10;
            place.setText("");
            place.setBackgroundResource(R.drawable.textview_border_red);
            place = findViewById(r * 10 + c);
            place.setText("@");
            show.setText("位置: " + r + ", " + c);

            clickBool = false;

            score = Math.max(((int)(Math.ceil((hit/red) * 100)) - miss), 0);
            rstScore.setText("Score: " + score);
            rstHit.setText("Hit: " + (int)hit);
            rstMiss.setText("Miss: " + miss);

            checkIndex = 0;
            for (int i = 0; i < 8; i++) {
                check[i] = 0;
            }
            hand.postDelayed(this, speed[level]);
        }
    };

    public void restart(View v) {
        checkIndex = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                walk[i][j] = 0;
                place = findViewById(i * 10 + j);
                place.setText("");
                if ((i + j) % 2 == 0)
                    place.setBackgroundResource(R.drawable.textview_border_blue);
                else
                    place.setBackgroundResource(R.drawable.textview_border_white);
            }
        }

        for (int i = 0; i < 8; i++) {
            check[i] = 0;
        }

        r = (int) (Math.random() * 8);
        c = (int) (Math.random() * 8);
        show.setText("位置: " + r + ", " + c);
        place = findViewById(r * 10 + c);
        place.setText("@");

        hit = 0;
        score = 0;
        miss = 0;
        rstScore.setText("Score: " + 0);
        rstHit.setText("Hit: " + 0);
        rstMiss.setText("Miss: " + 0);
        red = 1;
        clickBool = false;
        over = false;
        hand.removeCallbacks(run);
        hand.postDelayed(run, speed[level]);
    }

    public void pause(View v) {
        hand.removeCallbacks(run);
        Intent intent = new Intent(this, GameActivity.class);
        bundle = new Bundle();
        bundle.putSerializable("walk", walk);
        bundle.putInt("r", r);
        bundle.putInt("c", c);
        bundle.putInt("score", score);
        bundle.putDouble("hit", hit);
        bundle.putInt("miss", miss);
        bundle.putInt("level", level);
        bundle.putBoolean("clickBool", clickBool);
        bundle.putInt("red", red);
        bundle.putBoolean("over", over);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
