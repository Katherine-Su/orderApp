package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Customerdo extends AppCompatActivity
    implements View.OnClickListener {

    String email;
    Button seestore, seecart, seeSent, seeOndoing, seeCom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customerdo);
        seestore=findViewById(R.id.seestore);
        seecart=findViewById(R.id.seecart);
        seeSent=findViewById(R.id.seeSent);
        seeOndoing=findViewById(R.id.seeOndoing);
        seeCom=findViewById(R.id.seeCom);
        seestore.setOnClickListener(this);
        seecart.setOnClickListener(this);
        seeSent.setOnClickListener(this);
        seeOndoing.setOnClickListener(this);
        seeCom.setOnClickListener(this);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.seestore){
            Intent intent=new Intent(Customerdo.this,Storelist.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }
        else if (view.getId()==R.id.seecart) {
            Intent intent=new Intent(Customerdo.this,Cart.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }
        else if (view.getId()==R.id.seeSent){
            Intent intent=new Intent(Customerdo.this,Sentorder.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }
        else if (view.getId()==R.id.seeOndoing){

        }
        else if (view.getId()==R.id.seeCom){

        }
    }
}