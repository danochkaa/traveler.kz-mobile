package com.example.trav;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Avia";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME="Events";
    private static final String USER_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS User(Uid INTEGER PRIMARY KEY AUTOINCREMENT ,Firstname VARCHAR,Lastname VARCHAR,phone_number VARCHAR,Email VARCHAR, Username VARCHAR, Password VARCHAR);";
    static final String CREATE_HOTELS_TABLE = "CREATE TABLE Events(id INTEGER PRIMARY KEY, city TEXT,event_name TEXT, event_description TEXT,event_picture TEXT, Etime TIME, Edate DATE, tktCost INTEGER, note TEXT );";


    public DatabaseHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(USER_TABLE_CREATE);
        db.execSQL(CREATE_HOTELS_TABLE);

        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(1,'italy', 'Milan fashion Week','tens of fashion designers and brands organize fashion shows of their new collections.','milan','08:30','2020-01-18',100,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(2,'italy', 'Opera in Verona','Take part in the Shakespearian drama “Romeo&Juliet” through the streets of Verona.','opera','09:00','2020-01-15',150,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(3,'italy', 'Venice Carnival','A baroque masked ball on the Venetian Laguna, where the Carnival is every year -a unique experience.','venice','05:30','2020-01-20',50,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(4,'austria', 'Circus Festival, Winterfest','collection of various art forms from visual art to performing arts with dance, music, drama or acrobatics.','circus','09:00','2020-01-15',150,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(5,'austria', 'LIGHT FESTIVAL','The most sparkling winter fairy tale in the Alps at Swarovski Crystal Worlds.','light','07:25','2020-02-01',75,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(6,'austria', 'HOFBURG Silvesterball','Celebrate 50 years of HOFBURG Silvesterball.','hofburg','10:00','2020-02-05',100,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(7,'austria', 'ART Innsbruck','outstanding opportunity to see international fine art of the 19th, 20th and 21st century.','art','6:10','2020-02-04',50,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(8,'Switzerland', 'SlowUp Bike','roads are closed to car traffic and bikes take over.','bike','03:30','2020-02-07',10,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(9,'dubai', 'Urban Art DXB','Explore Dubai first urban art and street culture exhibition.','dxb','01:00','2020-01-17',20,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(10,'dubai', 'Muscle Show 2019','Up your fitness game.','show','08:30','2019-12-20',60,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(11,'dubai', 'dubai active','Workout with international fitness stars and attend free classes.','active','02:00','2020-02-10',80,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(12,'dubai', 'Things To Do In Dubai','Pack your long weekend with fireworks, family and fun.','todo','01:30','2019-12-12',300,'');");
        db.execSQL("INSERT INTO "+ TABLE_NAME  + " VALUES(13,'dubai', 'Samvel Gasparyan at The Fridge','See the jazz pianist live.','jaz','11:00','2020-01-10',110,'');");






    }


    public void insertUser(String fname, String lname, String phone, String eMail, String userName, String passWord, SQLiteDatabase db) {
        String sql = "INSERT INTO User (Firstname,Lastname,phone_number,Email,Username,Password ) VALUES (?, ?, ?,?,?,?)";

        SQLiteStatement statement = db.compileStatement(sql);

        statement.bindString(1, fname);
        statement.bindString(2, lname);
        statement.bindString(3, phone);
        statement.bindString(4, eMail);
        statement.bindString(5, userName);
        statement.bindString(6, passWord);

        statement.execute();
    }

    public String searchUser(String userName, SQLiteDatabase db) {
        Cursor cursor;
        String uname, pass;
        pass = "notFound";
        String sql = "SELECT Username, Password FROM User WHERE Username=" + "'" + userName + "'";
        cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                uname = cursor.getString(0);
                if (uname.equals(userName)) {
                    pass = cursor.getString(1);
                    break;
                }

            } while (cursor.moveToNext());
            if (!uname.equals(userName)) {
                pass = "register";
            }
        }
        return pass;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
}
