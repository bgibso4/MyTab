package com.mytab;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class EditBillScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill_screen);
        final int type = InputType.TYPE_CLASS_NUMBER |  InputType.TYPE_NUMBER_FLAG_DECIMAL;
        final TableLayout ll = (TableLayout) findViewById(R.id.tableLayout); // the table view that holds all rows
        final Context context;
        context = this; // Setting the context to this in order to use it in the onclick event handler
        // TODO figure out how to properly position columns to take up the whole row
        final TableRow.LayoutParams lparams = new TableRow.LayoutParams(500,150);
        final TableRow.LayoutParams checkParams = new TableRow.LayoutParams();
        // First row of table with textviews for headers instead of editviews
        TableRow firstRow = new TableRow(this);
        TextView nameHeader = new TextView(this);
        TextView priceHeader = new TextView(this);
        TextView totalText = (TextView)findViewById(R.id.totalAmount);
        TextView tipText= (TextView)findViewById(R.id.tipAmount);
        nameHeader.setText("Name");
        priceHeader.setText("Amount Paid");
        firstRow.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));
          priceHeader.setLayoutParams(lparams);
        //setting text of right column to the right side of the column
        priceHeader.setGravity(Gravity.CENTER_HORIZONTAL);
         nameHeader.setLayoutParams(lparams);
        nameHeader.setGravity(Gravity.CENTER_HORIZONTAL);
        firstRow.addView(nameHeader);
        firstRow.addView(priceHeader);
        ll.addView(firstRow);
        Log.i("item", ViewBills.item);
        SQLiteDatabase billInfo= openOrCreateDatabase("Bill",MODE_PRIVATE, null);
        billInfo.execSQL("CREATE TABLE IF NOT EXISTS bill2017 (billID INTEGER PRIMARY KEY, billName TEXT, name VARCHAR, amount DOUBLE, total DOUBLE, tip DOUBLE)");
        String query= "SELECT * FROM bill2017 WHERE billName='"+ViewBills.item+"'";
        Cursor m= billInfo.query("bill2017", null, "billName='"+ViewBills.item+"'", null,null,null,null);
        m.moveToFirst();
        int billIDIndex= m.getColumnIndex("billID");
        int nameIndex= m.getColumnIndex("billName");
        int peopleIndex= m.getColumnIndex("name");
        int amountIndex= m.getColumnIndex("amount");
        int totalIndex= m.getColumnIndex("total");
        int tipIndex= m.getColumnIndex("tip");
        totalText.setText(Double.toString(m.getDouble(totalIndex)));
        tipText.setText(Double.toString(m.getDouble(tipIndex)));
        ArrayList<String> names= new ArrayList<>();
        ArrayList<String> amounts= new ArrayList<>();
        while(true){

            names.add(m.getString(peopleIndex));
            amounts.add(Double.toString(m.getDouble(amountIndex)));
            if(m.isLast()){
                break;
            }
            m.moveToNext();
        }


        for (int i = 0;i < names.size(); i++){
            TableRow newRow = new TableRow(this);
            TextView name = new TextView(this);
            TextView price = new TextView(this);
            CheckBox check = new CheckBox(this);
            name.setText(names.get(i));
            price.setText(amounts.get(i));

            price.setLayoutParams(lparams);
            //setting text of right column to the right side of the column
            price.setGravity(Gravity.CENTER_HORIZONTAL);
            name.setLayoutParams(lparams);
            name.setGravity(Gravity.CENTER_HORIZONTAL);
            check.setLayoutParams(checkParams);
            check.setGravity(Gravity.START);
            newRow.addView(check);
            newRow.addView(name);
            newRow.addView(price);
            newRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            ll.addView(newRow);
        }

        Button saveButton= (Button)findViewById(R.id.editButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
