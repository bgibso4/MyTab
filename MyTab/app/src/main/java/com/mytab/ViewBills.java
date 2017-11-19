package com.mytab;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ViewBills extends AppCompatActivity {
    static ArrayList<String> notes = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static Set<String> set;
    public static String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bills);

        ListView listView = (ListView) findViewById(R.id.listView);
        notes.clear();
        ArrayList<String> s= new ArrayList<>();
        try{
            SQLiteDatabase billInfo= openOrCreateDatabase("Bill",MODE_PRIVATE, null);
            billInfo.execSQL("CREATE TABLE IF NOT EXISTS bill2017 (billID INTEGER PRIMARY KEY, billName TEXT, name VARCHAR, amount DOUBLE, total DOUBLE, tip DOUBLE)");
            String query= "SELECT DISTINCT billName FROM bill2017";
            Cursor m= billInfo.rawQuery(query, null);
            m.moveToFirst();
            int nameIndex= m.getColumnIndex("billName");
            //HashSet<String> s= new HashSet<>();

            while(true) {
                //Log.i("index", String.valueOf(nameIndex));
                s.add(m.getString(nameIndex));
                Log.i("Values", m.getString(nameIndex));
                if(m.isLast()){
                    break;
                }
                m.moveToNext();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }





        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, s);

        listView.setAdapter(arrayAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item =(String)arrayAdapter.getItem(position);


                Intent i = new Intent(getApplicationContext(), EditBillScreen.class);
                i.putExtra("noteId", position);
                startActivity(i);


            }

        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                new AlertDialog.Builder(ViewBills.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Are you sure?")
                        .setMessage("Do you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                notes.remove(position);

                                SharedPreferences sharedPreferences = ViewBills.this.getSharedPreferences("com.mytab", Context.MODE_PRIVATE);

                                if (set == null) {

                                    set = new HashSet<String>();

                                } else {

                                    set.clear();

                                }

                                set.addAll(notes);
                                sharedPreferences.edit().remove("notes").apply();
                                sharedPreferences.edit().putStringSet("notes", set).apply();
                                arrayAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

    }


}