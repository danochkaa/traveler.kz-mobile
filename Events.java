package com.example.trav;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class Events extends AppCompatActivity {

    DatabaseHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    EventsListAdapter listAdapter;
    ListView LISTVIEW;
    EditText cityKeyword;
    ImageButton search;

    ArrayList<Integer> id_Array;
    ArrayList<String> CITY_Array;
    ArrayList<String> NAME_Array;
    ArrayList<String> DISCRIPTION_Array;
    ArrayList<String> Picture_Array;
    ArrayList<Integer> drawable_Picture_Array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        sqLiteHelper = new DatabaseHelper(this);

        id_Array = new ArrayList<>();
        CITY_Array = new ArrayList<>();
        NAME_Array = new ArrayList<>();
        DISCRIPTION_Array = new ArrayList<>();
        Picture_Array = new ArrayList<>();
        drawable_Picture_Array = new ArrayList<>();

        cityKeyword = findViewById(R.id.city);
        search = findViewById(R.id.btnSearch);

        LISTVIEW = findViewById(R.id.listMenu);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(Events.this , SingleEvent.class);
                intent.putExtra("id", id_Array.get(position) );
                intent.putExtra("city", CITY_Array.get(position) );
                intent.putExtra("name", NAME_Array.get(position) );
                intent.putExtra("discription", DISCRIPTION_Array.get(position) );
                intent.putExtra("picture", drawable_Picture_Array.get(position) );
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onResume() {

        ShowSQLiteDBdata() ;
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityToSearch = cityKeyword.getText().toString().trim();

                if (cityToSearch.length() > 0){
                    ShowSQLiteDBdata(cityToSearch);
                } else if(cityToSearch.length() == 0){
                    Toast.makeText(Events.this, "please enter the city before click the button.", Toast.LENGTH_LONG).show();
                    ShowSQLiteDBdata() ;

                }

            }
        });


        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME,null,null, null,null,null,null);

        id_Array.clear();
        CITY_Array.clear();
        NAME_Array.clear();
        DISCRIPTION_Array.clear();
        Picture_Array.clear();
        drawable_Picture_Array.clear();

        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id_Array.add(cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0))));
                CITY_Array.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
                NAME_Array.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
                DISCRIPTION_Array.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
                Picture_Array.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));

            } while (cursor.moveToNext());
        }

        int picSize = Picture_Array.size();
        for(int i = 0; i < picSize; i++){
            String picName = Picture_Array.get(i).toLowerCase().trim();
            int id = getResources().getIdentifier("com.example.avia:drawable/" + picName, null, null);
            drawable_Picture_Array.add(id);

        }

        listAdapter = new EventsListAdapter(Events.this,id_Array, CITY_Array, NAME_Array, DISCRIPTION_Array ,drawable_Picture_Array);
        LISTVIEW.setAdapter(listAdapter);
        cursor.close();
    }

    private void ShowSQLiteDBdata(String city) {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        cursor = sqLiteDatabase.query(DatabaseHelper.TABLE_NAME,null,"city=?", new String[]{city},null,null,null);

        id_Array.clear();
        CITY_Array.clear();
        NAME_Array.clear();
        DISCRIPTION_Array.clear();
        Picture_Array.clear();
        drawable_Picture_Array.clear();

        cursor.moveToFirst();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                id_Array.add(cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(0))));
                CITY_Array.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
                NAME_Array.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
                DISCRIPTION_Array.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(3))));
                Picture_Array.add(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(4))));

            } while (cursor.moveToNext());
        }

        int picSize = Picture_Array.size();
        for(int i = 0; i < picSize; i++){
            String picName = Picture_Array.get(i).toLowerCase().trim();
            int id = getResources().getIdentifier("com.example.avia:drawable/" + picName, null, null);
            drawable_Picture_Array.add(id);
        }

        if ((cursor != null) && (cursor.getCount() > 0)){
            listAdapter = new EventsListAdapter(Events.this,id_Array, CITY_Array, NAME_Array, DISCRIPTION_Array ,drawable_Picture_Array);
            LISTVIEW.setAdapter(listAdapter);
            cursor.close();
        }else{
            showMessage("Sorry","There is no events for " + city +" right now.");
            ShowSQLiteDBdata() ;
        }


    }

    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

}
