package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView register;
    Button logInBtn;
    EditText inputAccount;
    EditText inputPassword;

    private static final String DataBaseName = "MyDataBase";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "MyTable";
    private SqlDataBaseHelper sqlDataBaseHelper;
    ArrayList<HashMap<String,String>> myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Stetho.initializeWithDefaults(this);

        inputAccount = findViewById(R.id.inputAccount);
        inputPassword = findViewById(R.id.inputPassword);
        register = (TextView) findViewById(R.id.register);
        register.setMovementMethod(LinkMovementMethod.getInstance());
        register.setText(Html.fromHtml(getResources().getString(R.string.address)));

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerJump();
            }
        });

        logInBtn = findViewById(R.id.loginIn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginInJump();
            }
        });

        sqlDataBaseHelper = new SqlDataBaseHelper(this.getBaseContext(), DataBaseName, null, DataBaseVersion, DataBaseTable);
        sqlDataBaseHelper.getWritableDatabase();
        myData = sqlDataBaseHelper.searchByAC(inputAccount.getText().toString());
        //if (myData.get(0).get("account").equals(""))
            //Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();
    }

    public void registerJump() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void loginInJump() {
        String mAccount = inputAccount.getText().toString();
        String mPassword = inputPassword.getText().toString();
        //String test = convertArrayToString(DataInformation.checkComplete);
        //String[] a = convertStringToArray(test);

        if (!sqlDataBaseHelper.checkByAccount(mAccount)) {
            Toast.makeText(this, "帳號不存在QQ", Toast.LENGTH_SHORT).show();
            return;
        }

        myData = sqlDataBaseHelper.searchByAC(mAccount);
        String password = myData.get(0).get("password");

        if (!mPassword.equals(password)) {
            Toast.makeText(this, "密碼錯誤", Toast.LENGTH_SHORT).show();
            return;
        }

        String progress = myData.get(0).get("progress");
        String mCheckComplete = myData.get(0).get("checkComplete");
        String[] checkComplete = convertStringToArray(mCheckComplete);
        //String test = convertArrayToString(DataInformation.checkComplete);

        Intent intent = new Intent(this, DataList.class);
        Bundle bundle = new Bundle();
        bundle.putString("account", mAccount);
        bundle.putString("password", mPassword);
        bundle.putInt("progress", Integer.parseInt(progress));
        bundle.putSerializable("checkComplete", checkComplete);
        intent.putExtra("bundle", bundle);
        startActivity(intent);
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

    public void onRestart() {
        super.onRestart();
        inputAccount.setText("");
        inputPassword.setText("");
    }
}