package com.example.termproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Register extends AppCompatActivity {

    Button registerBtn;
    Button validationBtn;
    private EditText inputAccount;
    private EditText password;
    private EditText checkPassword;
    private EditText inputvalidationNum;
    private String smail = "testingtestinglin@gmail.com";
    private String spassword = "kqwlnhttdafwmgnt";
    private String email;
    private String subject = "NP-字典";
    private String message = "你的驗證碼為 ";
    private char[] validationNum = {'1', '2', '3', '4', '5', '6'};
    String tempValid;

    private static final String DataBaseName = "MyDataBase";
    private static final int DataBaseVersion = 1;
    private static String DataBaseTable = "MyTable";
    private SqlDataBaseHelper sqlDataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sqlDataBaseHelper = new SqlDataBaseHelper(this.getBaseContext(), DataBaseName, null, DataBaseVersion, DataBaseTable);

        inputAccount = findViewById(R.id.inputAccount);
        password = findViewById(R.id.inputPassword);
        checkPassword = findViewById(R.id.inputConfirmPassword);
        inputvalidationNum = findViewById(R.id.inputValidationNum);
        registerBtn = findViewById(R.id.register);
        validationBtn = findViewById(R.id.sendValidationNum);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegister();
            }
        });

        validationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Properties properties = new Properties();
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.socketFactory.port", "465");
                properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.port", "465");

                Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(smail, spassword);
                    }
                });

                for (int i = 0; i < 6; i++) {
                    int temp = (int)(Math.random()*10);
                    validationNum[i] = Character.forDigit(temp , 10);
                }

                email = inputAccount.getText().toString();
                tempValid = String.valueOf(validationNum);

                MimeMessage mimeMessage = new MimeMessage(session);
                try {
                    mimeMessage.setFrom(new InternetAddress(smail));
                    mimeMessage.addRecipients(Message.RecipientType.TO, email);
                    mimeMessage.setSubject(subject);
                    mimeMessage.setText(message + tempValid);
                    SendMail sendMail = new SendMail(Register.this, mimeMessage);
                    sendMail.execute();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private class SendMail extends AsyncTask<MimeMessage, String, String> {
        private ProgressDialog progressDialog;
        private Context context;
        private MimeMessage mimeMessage;

        public SendMail(Context context, MimeMessage mimeMessage) {
            this.context = context;
            this.mimeMessage = mimeMessage;
        }

        @Override
        protected String doInBackground(MimeMessage... mimeMessages) {
            try {
                Transport.send(mimeMessage);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(Register.this, "Please Wait", "Sending Mail...", true);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if (s.equals("Success")) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
                builder.setMessage("Mail send successfully.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        //inputAccount.setText("");
                    }
                });

                builder.show();
            } else {
                Toast.makeText(getApplicationContext(), "Something went wrong ?", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void checkRegister() {
        email = inputAccount.getText().toString();
        String mPassword = password.getText().toString();
        String mCheckPassword = checkPassword.getText().toString();
        String mVal = inputvalidationNum.getText().toString();

        if (!mPassword.equals(mCheckPassword)) {
            Toast.makeText(this, "密碼不相符", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!mVal.equals(tempValid)) {
            Toast.makeText(this, "驗證碼錯誤", Toast.LENGTH_SHORT).show();
            return;
        }

        //傳到資料庫，並回到登入頁面
        sqlDataBaseHelper.addData(email, mPassword, 0, "false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false__,__false");

        Toast.makeText(this, "註冊成功", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}