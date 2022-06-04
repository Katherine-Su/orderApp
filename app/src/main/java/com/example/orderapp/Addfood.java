package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Addfood extends AppCompatActivity
    implements View.OnClickListener {

    EditText edtname, edtprice, edtdescription;
    Button okadd;
    String own;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfood);

        edtname=findViewById(R.id.edtname);
        edtprice=findViewById(R.id.edtprice);
        edtdescription=findViewById(R.id.edtdescription);
        okadd=findViewById(R.id.okadd);
        okadd.setOnClickListener(this);
        Intent intent=getIntent();
        own=intent.getStringExtra("own");

    }

    @Override
    public void onClick(View view) {
        Toast.makeText(Addfood.this,"add is succcessful",Toast.LENGTH_SHORT).show();
        addD();
    }


    private void addD(){
        String foodname=edtname.getText().toString();
        String foodprice=edtprice.getText().toString();
        String fooddescrption=edtdescription.getText().toString();


        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbz9WzChkAv8vS5syIV7023xW6EaoOXJehrPn0l9PGduEhHnnq_J2sMQT2KSWZBt34Bx0w/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Addfood.this, "success",
                                Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }

                }
        ) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("action","add");
                params.put("actionnext",own);
                params.put("foodname",foodname);
                params.put("price",foodprice);
                params.put("description",fooddescrption);
                return params;
            }

        };


        int socketTimeOut=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(socketTimeOut,0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);

    }
}