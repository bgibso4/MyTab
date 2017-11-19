package com.mytab;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;

import static com.mytab.ViewBills.notes;
import static com.mytab.ViewBills.set;

public class BillScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_screen);
        ArrayList<String> currencyList= new ArrayList<>();
        currencyList.add("CAD");
        currencyList.add("USD");
        currencyList.add("EUR");

        ArrayList<String> tipList= new ArrayList<>();
        tipList.add("%");
        tipList.add("Amt");


        Spinner currency = (Spinner)findViewById(R.id.currencySpinner);
        ArrayAdapter<String> adp= new ArrayAdapter<String>(this,
                R.layout.spinner_layout,currencyList);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currency.setAdapter(adp);

        Spinner tipType = (Spinner)findViewById(R.id.tipType);
        ArrayAdapter<String> adapt= new ArrayAdapter<String>(this,
                R.layout.spinner_layout,tipList);
        adapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipType.setAdapter(adapt);

        final EditText total= (EditText) findViewById(R.id.totalNum);
        final EditText Tip= (EditText)findViewById(R.id.tipValue);

        total.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        total.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setTotalText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Tip.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setTotalText();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tipType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setTotalText();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });
        final TableLayout ll = (TableLayout) findViewById(R.id.tableLayout); // the table view that holds all rows
        final Context context;
        final ArrayList<EditText> peopleNames= new ArrayList<>();
        final ArrayList<EditText> amounts= new ArrayList<>();
        context = this; // Setting the context to this in order to use it in the onclick event handler
        // TODO figure out how to properly position columns to take up the whole row
       final TableRow.LayoutParams lparams = new TableRow.LayoutParams(500,150);
        // First row of table with textviews for headers instead of editviews
        TableRow firstRow = new TableRow(this);
        TextView nameHeader = new TextView(this);
        TextView priceHeader = new TextView(this);
        nameHeader.setText("Name");
        priceHeader.setText("Amount Paid");
        firstRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
      //  priceHeader.setLayoutParams(lparams);
        //setting text of right column to the right side of the column
        priceHeader.setGravity(Gravity.CENTER_HORIZONTAL);
      //  nameHeader.setLayoutParams(lparams);
        nameHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        firstRow.addView(nameHeader);
        firstRow.addView(priceHeader);
        ll.addView(firstRow);


        // adding second row to table. Row has editviews and hints
        TableRow row= new TableRow(this);
        final EditText Price = new EditText(this);
        final EditText Name = new EditText(this);
        row.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
        Price.setLayoutParams(lparams);
        Price.setGravity(Gravity.CENTER_HORIZONTAL);
        Name.setLayoutParams(lparams);
        Name.setHint("Name");
        Name.setGravity(Gravity.CENTER_HORIZONTAL);
        Price.setHint("Amount Paid");
        row.addView(Name);
        row.addView(Price);
        peopleNames.add(Name);
        amounts.add(Price);
        ll.addView(row);


        // add rows dynamically with the click of a floating button
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(Price.getText().toString().trim().length() >0 && Name.getText().toString().trim().length() > 0) {
                    TableRow row = new TableRow(context);
                    EditText Price = new EditText(context);
                    EditText Name = new EditText(context);
                    row.setLayoutParams(new TableRow.LayoutParams(
                            TableRow.LayoutParams.FILL_PARENT,
                            TableRow.LayoutParams.WRAP_CONTENT));
                    Price.setLayoutParams(lparams);
                    Price.setGravity(Gravity.CENTER_HORIZONTAL);
                    Price.setHint("Amount Paid");
                    Name.setLayoutParams(lparams);
                    Name.setHint("Name");
                    Name.setGravity(Gravity.CENTER_HORIZONTAL);
                    row.addView(Name);
                    row.addView(Price);
                    peopleNames.add(Name);
                    amounts.add(Price);

                    ll.addView(row);
                }
                else{
                    new AlertDialog.Builder(BillScreen.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Please make sure you fill out Name and Amount Paid")
                            .setPositiveButton("Continue",null)
                            .show();

                }


            }
        });


        Button saveButton= (Button)findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total.getText().toString().trim().length() >0) {
                    final EditText input = new EditText(context);
                    new AlertDialog.Builder(BillScreen.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Please enter the name of the bill")
                            .setView(input)
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    try{
                                        SQLiteDatabase billInfo= openOrCreateDatabase("Bill",MODE_PRIVATE, null);
                                        billInfo.execSQL("CREATE TABLE IF NOT EXISTS bill2017 (billID INTEGER PRIMARY KEY, billName VARCHAR, name VARCHAR, amount DOUBLE, total DOUBLE, tip DOUBLE)");
                                        String query= "SELECT * FROM bill2017 WHERE billName = '"+input.getText().toString()+"'";
                                        Cursor m= billInfo.query("bill2017", null, "billName='"+input.getText().toString()+"'", null,null,null,null);
                                        m.moveToFirst();
                                        //Log.i("m", m.getString(0));
                                        if(m.getCount()<=0) {

                                            for (int x = 0; x < peopleNames.size(); x++) {
                                                ContentValues val = new ContentValues(5);
                                                val.put("billName", input.getText().toString());
                                                if (!peopleNames.get(x).getText().toString().equals("")) {
                                                    val.put("name", peopleNames.get(x).getText().toString());
                                                }
                                                if (!amounts.get(x).getText().toString().equals("")) {
                                                    val.put("amount", Double.parseDouble(amounts.get(x).getText().toString()));
                                                }
                                                val.put("total", Double.parseDouble(total.getText().toString()));
                                                val.put("tip", Double.parseDouble(Tip.getText().toString()));
                                                billInfo.insert("bill2017", null, val);
                                            }
                                            Cursor c = billInfo.rawQuery("SELECT * FROM bill2017", null);

                                            int billIDIndex= c.getColumnIndex("billID");
                                            int nameIndex= c.getColumnIndex("billName");
                                            int peopleIndex= c.getColumnIndex("name");
                                            int amountIndex= c.getColumnIndex("amount");
                                            int totalIndex= c.getColumnIndex("total");
                                            int tipIndex= c.getColumnIndex("tip");

                                            //int amountIndex= c.getColumnIndex("amount");
                                            Log.i("h", "here we are");
                                            c.moveToFirst();
                                            while(true){

                                                Log.i("billID", Integer.toString(c.getInt(billIDIndex)));
                                                Log.i("billName", c.getString(nameIndex));
                                                Log.i("Name", c.getString(peopleIndex));
                                                Log.i("amount", Double.toString(c.getDouble(amountIndex)));
                                                Log.i("total", Double.toString(c.getDouble(totalIndex)));
                                                Log.i("tip", Double.toString(c.getDouble(tipIndex)));
                                                if(c.isLast()){
                                                    return;
                                                }
                                                c.moveToNext();
                                            }
                                        }



                                        else{
                                            new AlertDialog.Builder(BillScreen.this)
                                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                                    .setTitle("Bill Name already exists. Please enter new name")
                                                    .setPositiveButton("OK",null)
                                                    .show();

                                        }

                                    }
                                    catch(Exception e){
                                        e.printStackTrace();
                                    }

                                }
                            })

                            .show();
                }
                else{
                    new AlertDialog.Builder(BillScreen.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Please enter a total")
                            .setPositiveButton("OK",null)
                            .show();

                }

            }
        });

    }

    public void setTotalText(){
        TextView finTotal= (TextView)findViewById(R.id.finalTotal);
        EditText Tip= (EditText)findViewById(R.id.tipValue);
        EditText total= (EditText) findViewById(R.id.totalNum);
        Spinner tipType = (Spinner)findViewById(R.id.tipType);

        float finalCost;
        //String temp= total.getText().toString();
        if(!(total.getText().toString().equals("")) && !(Tip.getText().toString().equals(""))){
            if(tipType.getSelectedItem().toString().equals("%")){
                finalCost= Float.parseFloat(total.getText().toString()) + Float.parseFloat(total.getText().toString()) * (Float.parseFloat(Tip.getText().toString())/100);
            }
            else{
                finalCost= Float.parseFloat(total.getText().toString()) + Float.parseFloat(Tip.getText().toString());
            }

        }
    }



}
