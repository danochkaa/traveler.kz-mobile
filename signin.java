package com.example.trav;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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


public class signin extends Activity implements View.OnClickListener {

    static public SQLiteDatabase db;
    static public Button Signin;
    static public EditText Username, Password;
    DatabaseHelper databaseHelper;
    SQLiteDatabase sqLiteDatabase;
    public static String uname;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        Username = (EditText) findViewById(R.id.Username);
        Password = (EditText) findViewById(R.id.password);


        // create DB
        db = openOrCreateDatabase("TripKit", Context.MODE_PRIVATE, null);
        //Create the table
        db.execSQL("CREATE TABLE IF NOT EXISTS User(Uid INTEGER PRIMARY KEY AUTOINCREMENT ,Firstname VARCHAR,Lastname VARCHAR,phone_number VARCHAR,Email VARCHAR, Username VARCHAR, Password VARCHAR);");


        TextView creat = findViewById(R.id.newAccount);
        String text = "New User? Create New Account";
        SpannableString ss = new SpannableString(text);
        ForegroundColorSpan link = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));


        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {

                Intent intent = new Intent(signin.this, signup.class);
                startActivity(intent);

            }
        };
        ss.setSpan(clickableSpan2, 10, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(link, 10, 28, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        creat.setText(ss);
        creat.setMovementMethod(LinkMovementMethod.getInstance());

        Signin = (Button) findViewById(R.id.signinb);
        Signin.setOnClickListener(this);
    }

    public void onClick(View view) {

        if (view.getId() == R.id.signinb) {
            EditText Username = (EditText) findViewById(R.id.Username);
            String strUsername = Username.getText().toString();
            EditText Password = (EditText) findViewById(R.id.password);
            String strPass = Password.getText().toString();
            String strreg = "register";
            String strNot = "notFound";
            if (strUsername.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your name", Toast.LENGTH_SHORT).show();
            } else if (strPass.trim().equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_SHORT).show();
            } else {

                databaseHelper = new DatabaseHelper(this);
                sqLiteDatabase = databaseHelper.getReadableDatabase();
                String password = databaseHelper.searchUser(strUsername, sqLiteDatabase);
                if (strPass.equals(password)) {
                    //check with tasneem if intent is correct
                    Intent i = new Intent(signin.this, Discover.class);
                    String sql = "SELECT Uid FROM User WHERE Username=" + "'" + strUsername + "'" + " AND Password=" + "'" + password + "'";

                    Cursor c = sqLiteDatabase.rawQuery(sql, null);

                    if (c.getCount() > 0) {
                        c.moveToFirst();
                        uname = strUsername;
                        i.putExtra("uid", c.getInt(c.getColumnIndex("Uid")));
                        startActivity(i);
                    }
                } else if (strreg.equals(password)) {
                    Toast.makeText(getApplicationContext(), "Please register before logging in", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast message
                    Toast.makeText(getApplicationContext(), "Username and Password do not match", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


}
