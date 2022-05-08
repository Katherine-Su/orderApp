package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Login extends AppCompatActivity
    implements View.OnClickListener{

    ImageButton ibtn;
    TextView txv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txv=(TextView) findViewById(R.id.textView5);
       // txv.bringToFront();
        ibtn=(ImageButton) findViewById(R.id.imageButton2);
        ibtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}