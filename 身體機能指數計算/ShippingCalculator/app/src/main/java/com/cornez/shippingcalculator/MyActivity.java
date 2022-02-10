package com.cornez.shippingcalculator;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class MyActivity extends Activity {
    //DATA MODEL FOR SHIP ITEM
    private Somenum somenum;

    //VIEW OBJECTS FOR LAYOUT UI REFERENCE
    private EditText height;//使用者輸入的物件
    private EditText weight1;
    private EditText activity;
    private EditText Kne;
    private EditText Age;
    private TextView Gout;
    private TextView Fout;
    private boolean gender;//表示當前性別模式 1:男 0:女
    private boolean func;//表示當前性別模式
    private EditText name;

    private Button appIntrobtn;
    private Button idealweightbtn;

    Double weight;
    Double w1;
    Double w2;
    Double cal;

    private static final String DataBaseName = "MyDataBase";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "MyTable";
    private static SQLiteDatabase db;
    private SqlDataBaseHelper sqlDataBaseHelper;

    ListView listView;
    List<String> list;
    List<Boolean> listShow;
    ListView_Class adapterItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {//初始化
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_2);
        Stetho.initializeWithDefaults(this);

        gender = true;

        func = true;

        //CREATE THE DATA MODEL FOR STORING THE ITEM TO BE SHIPPED
        somenum = new Somenum();

        //TASK 3: ESTABLISH THE REFERENCES TO INPUT WEIGHT ELEMENT
        height = (EditText) findViewById(R.id.editText1);//將名字為editText1的輸入格放到Cm
        weight1 = (EditText) findViewById(R.id.editText2);
        activity = (EditText) findViewById(R.id.editText3);
        Kne = (EditText) findViewById(R.id.editText4);
        Age = (EditText) findViewById(R.id.editText5);
        name = findViewById(R.id.inputName);

        Gout = (TextView) findViewById(R.id.button1);//也可在按鈕上顯示
        Fout = (TextView) findViewById(R.id.button2);//也可在按鈕上顯示

        Kne.addTextChangedListener(kneTextWatcher);
        Age.addTextChangedListener(ageTextWatcher);
        Kne.setFocusableInTouchMode(false);
        Age.setFocusableInTouchMode(false);
        Kne.setFocusable(false);
        Age.setFocusable(false);

        appIntrobtn = (Button) findViewById(R.id.appintro);
        appIntrobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appintro();
            }
        });

        idealweightbtn = (Button) findViewById(R.id.idealweight);
        idealweightbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                idealweight();
            }
        });

        Intent intent = getIntent();
        if (    intent.getDoubleExtra("weight", 0) != 0 &&
                intent.getDoubleExtra("w1", 0) != 0 &&
                intent.getDoubleExtra("w2", 0) != 0 &&
                intent.getDoubleExtra("cal", 0) != 0) {
            weight = intent.getDoubleExtra("weight", 0);
            w1 = intent.getDoubleExtra("w1", 0);
            w2 = intent.getDoubleExtra("w2", 0);
            cal = intent.getDoubleExtra("cal", 0);
        }
        else {
            weight = 0.0;
            w1 = 0.0;
            w2 = 0.0;
            cal = 0.0;
        }

        if (intent.getStringExtra("oriweight") != "")
            weight1.setText(intent.getStringExtra("oriweight"));
        if (intent.getStringExtra("oriactivity") != "")
            activity.setText(intent.getStringExtra("oriactivity"));
        if (intent.getStringExtra("age") != "")
            Age.setText(intent.getStringExtra("age"));
        if (intent.getStringExtra("knee") != "")
            Kne.setText(intent.getStringExtra("knee"));
        if (intent.getStringExtra("oriheight") != "")
            height.setText(intent.getStringExtra("oriheight"));

        gender = intent.getBooleanExtra("gender", true);
        if (gender) {
            Gout.setText("男性");//按鈕文字切換
        } else {
            Gout.setText("女性");
        }

        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(onClickListView);
        sqlDataBaseHelper = new SqlDataBaseHelper(this.getBaseContext(), DataBaseName, null, DataBaseVersion, DataBaseTable);
        //db = sqlDataBaseHelper.getWritableDatabase(); //開啟資料庫

        upgradeListView();
    }

    private AdapterView.OnItemClickListener onClickListView = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            CheckedTextView chkItem = (CheckedTextView) view.findViewById(R.id.checkItem);
            chkItem.setChecked(!chkItem.isChecked());
            listShow.set(position, chkItem.isChecked());
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("G",gender);
        outState.putBoolean("F",func);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null) {
            gender = savedInstanceState.getBoolean("G");
            if (gender) {
                Gout.setText("男性");//按鈕文字切換
            } else {
                Gout.setText("女性");
            }
            func = savedInstanceState.getBoolean("F");
            if (func) {
                Fout.setText("自行輸入");//按鈕文字切換
                height.setFocusableInTouchMode(true);
                Kne.setFocusable(true);
                Kne.setFocusableInTouchMode(false);
                Age.setFocusableInTouchMode(false);
                Kne.setFocusable(false);
                Age.setFocusable(false);
            } else {
                Fout.setText("估算身高");
                height.setFocusableInTouchMode(false);
                Kne.setFocusable(false);
                Kne.setFocusableInTouchMode(true);
                Age.setFocusableInTouchMode(true);
                Kne.setFocusable(true);
                Age.setFocusable(true);
            }
        }
    }

    public void Gender(View view) {//切換性別
        if (gender) {
            gender = false;
            Gout.setText("女性");//按鈕文字切換
        } else {
            gender = true;
            Gout.setText("男性");
        }
    }

    public void Comp(View view) {//切換運算
        if (func) {
            func = false;
            Fout.setText("估算身高");//按鈕文字切換
            height.setText(null);
            height.setFocusableInTouchMode(false);
            height.setFocusable(false);
            Kne.setFocusableInTouchMode(true);
            Age.setFocusableInTouchMode(true);
            Kne.setFocusable(true);
            Age.setFocusable(true);
        } else {
            func = true;
            Fout.setText("自行輸入");
            height.setFocusableInTouchMode(true);
            height.setFocusable(true);
            Kne.setFocusableInTouchMode(false);
            Kne.setText(null);
            Age.setFocusableInTouchMode(false);
            Age.setText(null);
            Kne.setFocusable(false);
            Age.setFocusable(false);
        }
    }

    public void Res(View view) {//重製
        gender = true;
        func=true;
        Gout.setText("男性");
        Fout.setText("自行輸入");
        height.setFocusableInTouchMode(true);
        height.setFocusable(true);
        Kne.setFocusableInTouchMode(false);
        Kne.setText(null);
        Age.setFocusableInTouchMode(false);
        Age.setText(null);
        Kne.setFocusable(false);
        Age.setFocusable(false);
        somenum.setHeight(0);//重製已輸入的變數
        somenum.setWeight(0.0);
        somenum.setActivity(0);
        height.setText(null);//清空使用者輸入的字串(因為我指定使用者只能輸入數字才用null)
        weight1.setText(null);
        activity.setText(null);
        name.setText(null);
    }

    public void load(View view) {
        int index = 0;
        String tempName = "";
        for (int i = 0; i < listShow.size(); i++) {
            if (listShow.get(i) == true) {
                tempName = list.get(i);
                index++;
            }
        }

        if (index > 1)
            Toast.makeText(MyActivity.this, "載入請勿選取多於一筆資料:)", Toast.LENGTH_SHORT).show();
        else if (index == 0)
            Toast.makeText(MyActivity.this, "請至少選取一筆資料:)", Toast.LENGTH_SHORT).show();
        else {
            ArrayList<HashMap<String, String>> temp;
            temp = sqlDataBaseHelper.searchByName(tempName);
            weight1.setText(temp.get(0).get("weight"));
            activity.setText(temp.get(0).get("activity"));
            Kne.setText(temp.get(0).get("knee"));
            Age.setText(temp.get(0).get("age"));

            if (temp.get(0).get("gender").equals("F")) {
                gender = false;
                Gout.setText("女性");
            }
            else {
                gender = true;
                Gout.setText("男性");
            }

            height.setText(temp.get(0).get("height"));
        }
    }

    public void add(View view) {
        String inputName = name.getText().toString();

        if (height.getText().toString().equals(""))
            somenum.setHeight(0);
        else
            somenum.setHeight(Integer.parseInt(height.getText().toString()));

        if (weight1.getText().toString().equals(""))
            somenum.setWeight(0.0);
        else
            somenum.setWeight(Double.parseDouble(weight1.getText().toString()));

        if (activity.getText().toString().equals(""))
            somenum.setActivity(0);
        else
            somenum.setActivity(Integer.parseInt(activity.getText().toString()));

        if (inputName.equals(""))
            Toast.makeText(MyActivity.this, "請輸入姓名:)", Toast.LENGTH_SHORT).show();
        else if (sqlDataBaseHelper.checkByName(inputName)){ //資料庫已有資料，更新
            if (gender)
                sqlDataBaseHelper.modify(inputName, somenum.getKne(), somenum.getAge(), somenum.getHeight(), "M", somenum.getWeight(), somenum.getActivity());
            else
                sqlDataBaseHelper.modify(inputName, somenum.getKne(), somenum.getAge(), somenum.getHeight(), "F", somenum.getWeight(), somenum.getActivity());
            upgradeListView();
        }
        else { //資料庫沒有資料，新增
            if (gender)
                sqlDataBaseHelper.addData(inputName, somenum.getKne(), somenum.getAge(), somenum.getHeight(), "M", somenum.getWeight(), somenum.getActivity());
            else
                sqlDataBaseHelper.addData(inputName, somenum.getKne(), somenum.getAge(), somenum.getHeight(), "F", somenum.getWeight(), somenum.getActivity());
            upgradeListView();
        }
    }

    public void delete(View view) {
        boolean c = false;
        for (int i = 0; i < listShow.size(); i++) {
            if (listShow.get(i) == true) {
                sqlDataBaseHelper.deleteData(list.get(i));
                c = true;
            }
        }
        if (!c)
            Toast.makeText(MyActivity.this, "請選取至少一筆資料:)", Toast.LENGTH_SHORT).show();
        upgradeListView();
    }

    public void upgradeListView() {
        ArrayList<HashMap<String, String>> temp;
        temp = sqlDataBaseHelper.showAll();
        listShow = new ArrayList<Boolean>();
        list = new ArrayList<String>();

        for (int i = 0; i < temp.size(); i++) {
            list.add(temp.get(i).get("name"));
            listShow.add(false);
        }

        adapterItem = new ListView_Class(this, list, listShow);
        listView.setAdapter(adapterItem);
    }

    private TextWatcher kneTextWatcher = new TextWatcher() {
        //THE INPUT ELEMENT IS ATTACHED TO AN EDITABLE,
        //THEREFORE THESE METHODS ARE CALLED WHEN THE TEXT IS CHANGED

        public void onTextChanged(CharSequence s,
                                  int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 1 && s.toString().startsWith("0"))//若輸入0開頭卻非0的數字
                s.replace(0, 1, "");//刪除開頭的0
            try {
                somenum.setKne(Double.parseDouble(s.toString()));//嘗試轉換成數字存放
            } catch (Exception e) {//預防空字串
                somenum.setKne(0);//男性: 85.1 + (1.73  膝長) - (0.11  年齡) 女性: 91.45 + (1.53  膝長) - (0.16  年齡)
            }
            if(!somenum.EnoughC())
                height.setText("0");
            else if(gender)
                height.setText(String.format("%.0f", 85.1+(1.73*somenum.getKne())-(0.11*somenum.getAge())));
            else
                height.setText(String.format("%.0f", 91.45+(1.53*somenum.getKne())-(0.16*somenum.getAge())));
        }

        public void beforeTextChanged(CharSequence s,
                                      int start, int count, int after) {
        }
    };

    private TextWatcher ageTextWatcher = new TextWatcher() {
        //THE INPUT ELEMENT IS ATTACHED TO AN EDITABLE,
        //THEREFORE THESE METHODS ARE CALLED WHEN THE TEXT IS CHANGED

        public void onTextChanged(CharSequence s,
                                  int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 1 && s.toString().startsWith("0"))//若輸入0開頭卻非0的數字
                s.replace(0, 1, "");//刪除開頭的0
            try {
                somenum.setAge(Double.parseDouble(s.toString()));//嘗試轉換成數字存放
            } catch (Exception e) {//預防空字串
                somenum.setAge(0);//男性: 85.1 + (1.73  膝長) - (0.11  年齡) 女性: 91.45 + (1.53  膝長) - (0.16  年齡)
            }
            if(!somenum.EnoughC())
                height.setText("0");
            else if(gender)
                height.setText(String.format("%.0f", 85.1+(1.73*somenum.getKne())-(0.11*somenum.getAge())));
            else
                height.setText(String.format("%.0f", 91.45+(1.53*somenum.getKne())-(0.16*somenum.getAge())));
        }

        public void beforeTextChanged(CharSequence s,
                                      int start, int count, int after) {
        }
    };

    public void appintro() {
        Intent intent = new Intent(this, AppIntroActivity.class);
        intent.putExtra("weight", weight);
        intent.putExtra("w1", w1);
        intent.putExtra("w2", w2);
        intent.putExtra("cal", cal);

        intent.putExtra("oriheight", height.getText().toString());
        intent.putExtra("oriweight", weight1.getText().toString());
        intent.putExtra("oriactivity", activity.getText().toString());
        intent.putExtra("knee", Kne.getText().toString());
        intent.putExtra("age", Age.getText().toString());
        intent.putExtra("gender", gender);

        startActivity(intent);
        overridePendingTransition(R.anim.transition_left_in, R.anim.transition_left_out);
    }

    public void idealweight() {
        if (!height.getText().toString().equals("") && !weight1.getText().toString().equals("") && !activity.getText().toString().equals("")) {
            somenum.setHeight(Integer.parseInt(height.getText().toString()));
            somenum.setWeight(Double.parseDouble(weight1.getText().toString()));
            somenum.setActivity(Integer.parseInt(activity.getText().toString()));
            somenum.computeW(gender);
        }

        Intent intent = new Intent(this, IdealWeightActivity.class);
        if (somenum.Enough()) {
            intent.putExtra("weight", somenum.getSkg());
            intent.putExtra("w1", somenum.getArb());
            intent.putExtra("w2", somenum.getAre());
            intent.putExtra("cal", somenum.getCaro());

            intent.putExtra("oriheight", height.getText().toString());
            intent.putExtra("oriweight", weight1.getText().toString());
            intent.putExtra("oriactivity", activity.getText().toString());
            intent.putExtra("knee", Kne.getText().toString());
            intent.putExtra("age", Age.getText().toString());
            intent.putExtra("gender", gender);
        }
        else {
            if (height.getText().toString().equals("")  && weight1.getText().toString().equals("") && activity.getText().toString().equals("")) {
                intent.putExtra("weight", weight);
                intent.putExtra("w1", w1);
                intent.putExtra("w2", w2);
                intent.putExtra("cal", cal);

                intent.putExtra("oriheight", height.getText().toString());
                intent.putExtra("oriweight", weight1.getText().toString());
                intent.putExtra("oriactivity", activity.getText().toString());
                intent.putExtra("knee", Kne.getText().toString());
                intent.putExtra("age", Age.getText().toString());
                intent.putExtra("gender", gender);
            }
            else
                return;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.transition_right_in, R.anim.transition_right_out);
    }
}
