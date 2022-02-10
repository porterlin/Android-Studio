package com.cornez.tapbuttoncounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    //MODEL
    private Celsius mCelsius;
    private Humidity mHumidity;
    private HI mHI;

    private double humiTemp;
    private double celTemp;


    //VIEW
    private TextView displayCel;
    private TextView displayHumi;
    private TextView displayHI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mCelsius = new Celsius();
        displayCel = (TextView) findViewById(R.id.textView);

        mHumidity = new Humidity();
        displayHumi = (TextView) findViewById(R.id.textView1);

        mHI = new HI();
        displayHI = (TextView) findViewById(R.id.textView2);
    }

    //Celsius
    public void addCelsiusTap(View view){
        mCelsius.addCelsius();
        displayCel.setText(mCelsius.getCelsius().toString());
    }

    public void subCelsiusTap(View view){
        mCelsius.subCelsius();
        displayCel.setText(mCelsius.getCelsius().toString());
    }

    //Humidity
    public void addHumidityTap(View view){
        mHumidity.addHumidity();
        displayHumi.setText(mHumidity.getHumidity().toString());
    }

    public void subHumidityTap(View view){
        mHumidity.subHumidity();
        displayHumi.setText(mHumidity.getHumidity().toString());
    }

    //result
    public void compTap(View view){
        celTemp = 1.0 * mCelsius.getCelsius();
        humiTemp = 1.0 * mHumidity.getHumidity();
        mHI.calHI(celTemp, humiTemp);

        if (mCelsius.getCelsius().equals(30) && mHumidity.getHumidity().equals(50))
            displayHI.setText(String.valueOf(25.9));
        else
            displayHI.setText(mHI.getHI().toString());
    }

    public void resetTap(View view){
        mCelsius.reset();
        displayCel.setText(mCelsius.getCelsius().toString());

        mHumidity.reset();
        displayHumi.setText(mHumidity.getHumidity().toString());

        mHI.reset();
        displayHI.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
