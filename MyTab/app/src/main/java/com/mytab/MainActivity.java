package com.mytab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button billButton = (Button) findViewById(R.id.newBillButton);
        Button viewmybillButton = (Button) findViewById(R.id.myBill);
        billButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, BillScreen.class));
            }
        });
        viewmybillButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, ViewBills.class));
            }
        });
    }
//    public void newBillButtonClick(View view){
//        startActivity(new Intent(MainActivity.this, BillScreen.class));
//    }
}
