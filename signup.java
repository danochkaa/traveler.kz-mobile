package com.example.trav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class signup extends Activity implements View.OnClickListener {

    static public EditText Fname, Lname, Phone, Email, Username, Password;
    static public Button Signin, Signup;
    Context context = this;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        TextView creat = findViewById(R.id.signintext);
        String text = "Already have account? Sign in";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan link = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));

        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                Intent intent = new Intent(signup.this, signin.class);
                startActivity(intent);

            }
        };
        ss.setSpan(clickableSpan2, 22, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(link, 22, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        creat.setText(ss);
        creat.setMovementMethod(LinkMovementMethod.getInstance());


        Fname = (EditText) findViewById(R.id.Fname);
        Lname = (EditText) findViewById(R.id.Lname);
        Phone = (EditText) findViewById(R.id.phone);
        Email = (EditText) findViewById(R.id.email);
        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.password);


        Signup = (Button) findViewById(R.id.signup);
        Signup.setOnClickListener(this);


    }

    public void onClick(View view) {
        if (view.getId() == R.id.signup) {
            EditText userName = (EditText) findViewById(R.id.Username);
            EditText eMail = (EditText) findViewById(R.id.email);
            EditText phone = (EditText) findViewById(R.id.phone);
            EditText fname = (EditText) findViewById(R.id.Fname);
            EditText lname = (EditText) findViewById(R.id.Lname);
            EditText passWord = (EditText) findViewById(R.id.password);


            String struserName = userName.getText().toString();
            String streMail = eMail.getText().toString();
            String strpassWord = passWord.getText().toString();
            String fnames = fname.getText().toString();
            String lnames = lname.getText().toString();
            String phones = phone.getText().toString();


            if (struserName.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
            } else if (streMail.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your email", Toast.LENGTH_SHORT).show();
            } else if (strpassWord.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            } else if (fnames.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your first name", Toast.LENGTH_SHORT).show();
            } else if (lnames.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your last name", Toast.LENGTH_SHORT).show();
            } else if (phones.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your phone", Toast.LENGTH_SHORT).show();
            } else {
                //Insert details in database
                databaseHelper = new DatabaseHelper(context);
                sqLiteDatabase = databaseHelper.getWritableDatabase();

                databaseHelper.insertUser(fnames, lnames, phones, streMail, struserName, strpassWord, sqLiteDatabase);
                Toast.makeText(getBaseContext(), "Registration successful...Please Login with your username and password",
                        Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signup.this, signin.class);
                startActivity(intent);
                //databaseHelper.close();
                //finish();
            }
        }
    }


}
