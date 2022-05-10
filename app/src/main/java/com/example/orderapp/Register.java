package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity
    implements View.OnClickListener {

    TextView txv;
    EditText edt_username,edt_email,edt_pas;
    FirebaseAuth mAuth;
    Snackbar sbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth=FirebaseAuth.getInstance();
        txv=(TextView) findViewById(R.id.al_ac);
        txv.setOnClickListener(this);
        edt_username=(EditText) findViewById(R.id.edt_username);
        edt_email=(EditText) findViewById(R.id.edt_email);
        edt_pas=(EditText) findViewById(R.id.edt_pas);
        sbar= Snackbar.make(findViewById(R.id.root),"",Snackbar.LENGTH_SHORT);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.al_ac){
            startActivity(new Intent(this,Login.class));
        }
    }

    private void createUser(){
        String username=edt_username.getText().toString();
        String email=edt_email.getText().toString();
        String pas=edt_pas.getText().toString();
       // if (username.isEmpty()){

        //}

    }
}