package com.mytab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
