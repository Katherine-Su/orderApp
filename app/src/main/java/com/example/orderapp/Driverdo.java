package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Driverdo extends AppCompatActivity
    implements View.OnClickListener {

    String email;
    Button checkallOrder, checkmyOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driverdo);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        checkallOrder=findViewById(R.id.checkallOrder);
        checkmyOrder=findViewById(R.id.checkmyOrder);
        checkallOrder.setOnClickListener(this);
        checkmyOrder.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.checkallOrder) {

            Intent intent=new Intent(Driverdo.this,DallOrder.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }
        else {
            Intent intent=new Intent(Driverdo.this,DmyOrder.class);
            intent.putExtra("email",email);
            startActivity(intent);

        }
    }
}