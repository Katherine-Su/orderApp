package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity
    implements View.OnClickListener{


    Button btn;
    TextView txv,back_to_sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txv=(TextView) findViewById(R.id.textView5);
       // txv.bringToFront();
        back_to_sign_up=(TextView) findViewById(R.id.back_to_sign_up);
        back_to_sign_up.setOnClickListener(this);
        btn=(Button) findViewById(R.id.blogin);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.back_to_sign_up){
            finish();
        }
        else {
            Intent it = new Intent(this, Store.class);
            startActivity(it);
        }

    }
}