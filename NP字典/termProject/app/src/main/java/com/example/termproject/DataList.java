package com.example.termproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DataList extends AppCompatActivity {

    private ListView listView;
    private MyAdapter adapter;
    private ProgressBar progressBar;
    private String[] dataName = DataInformation.data_name;
    private String[] dataDetail = DataInformation.data_detail;
    private String[] checkComplete = DataInformation.checkComplete;
    private TextView learningProgress;
    Bundle bundle;

    private static final String DataBaseName = "MyDataBase";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "MyTable";
    private SqlDataBaseHelper sqlDataBaseHelper;

    String account;
    String password;
    int progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_list);

        sqlDataBaseHelper = new SqlDataBaseHelper(this.getBaseContext(), DataBaseName, null, DataBaseVersion, DataBaseTable);

        //Toolbar toolbar = findViewById(R.id.app_bar);
        //setSupportActionBar(toolbar);
        learningProgress = findViewById(R.id.learningProgress);

        listView = findViewById(R.id.mylistview);
        adapter = new MyAdapter(this, R.layout.adapter_view, dataName, checkComplete);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                jumpDetail(i);
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(100);

        Intent intent = getIntent();
        bundle = intent.getBundleExtra("bundle");
        if (bundle != null) {
            recover(bundle);
        }

       // adapter.getView(i, view, (ViewGroup)view.getParent());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_setting:
                for (int i = 0; i < checkComplete.length; i++) {
                    checkComplete[i] = "false";
                }
                adapter.notifyDataSetChanged();
                progress = 0;
                progressBar.setProgress(progress);
                learningProgress.setText("學習進度: 0%");
                String temp = convertArrayToString(checkComplete);
                sqlDataBaseHelper.modify(account, password, progress, temp);
                break;
            case R.id.deleteAccount:
                sqlDataBaseHelper.deleteData(account);
                Toast.makeText(this, "帳號刪除成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DataList.this, MainActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void jumpDetail(int i) {
        checkComplete[i] = "true";
        String msg=dataName[i];
        String detail = dataDetail[i];
        //checkComplete[i] = "true";
        Intent intent = new Intent(this, DataShow.class);
        intent.putExtra("detail", detail);
        intent.putExtra("i", i);
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }

    public void onStop() {
        super.onStop();
        adapter.notifyDataSetChanged();
    }

    public void onRestart() {
        super.onRestart();

        double c = (double) checkComplete();
        double dprogress = (c / checkComplete.length) * 100;
        progress = (int)dprogress;
        progressBar.setProgress((int)progress);
        learningProgress.setText("學習進度: " + String.valueOf((int)progress) + "%");

        String temp = convertArrayToString(checkComplete);
        sqlDataBaseHelper.modify(account, password, progress, temp);
    }

    public double checkComplete() {
        int index = 0;
        for (int i = 0; i < checkComplete.length; i++) {
            if (checkComplete[i].equals("true"))
                index++;
        }

        return index;
    }

    public void recover(Bundle bundle) {
        progress = bundle.getInt("progress");
        String[] check = (String[]) bundle.getSerializable("checkComplete");
        account = bundle.getString("account");
        password = bundle.getString("password");

        for (int i = 0; i < check.length; i++) {
            DataInformation.checkComplete[i] = check[i];
            checkComplete[i] = check[i];
        }

        progressBar.setProgress(progress);
        learningProgress.setText("學習進度: " + String.valueOf((int)progress) + "%");
        adapter.notifyDataSetChanged();
    }

    public static String strSeparator = "__,__";
    public static String convertArrayToString(String[] array){
        String str = "";
        for (int i = 0;i<array.length; i++) {
            str = str+array[i];
            // Do not append comma at the end of last element
            if(i<array.length-1){
                str = str+strSeparator;
            }
        }
        return str;
    }

    public static String[] convertStringToArray(String str){
        String[] arr = str.split(strSeparator);
        return arr;
    }
}