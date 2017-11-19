package com.mytab;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

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

        EditText total= (EditText) findViewById(R.id.totalNum);
        EditText Tip= (EditText)findViewById(R.id.tipValue);

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
        EditText Price = new EditText(this);
        EditText Name = new EditText(this);
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
        ll.addView(row);


        // add rows dynamically with the click of a floating button
        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                TableRow row= new TableRow(context);
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

                ll.addView(row);

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
            finTotal.setText(String.valueOf(finalCost));
        }
    }
}
