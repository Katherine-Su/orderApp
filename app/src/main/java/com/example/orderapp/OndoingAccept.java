package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class OndoingAccept extends AppCompatActivity
    implements View.OnClickListener {

    String store, foodname, number, des, driver;
    TextView completeStore, completeName, completenumber, completedes, completedriver;
    Button complete, cancel;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ondoing_accept);

        Intent intent=getIntent();
        store=intent.getStringExtra("store");
        foodname=intent.getStringExtra("foodname");
        number=intent.getStringExtra("number");
        des=intent.getStringExtra("des");
        driver=intent.getStringExtra("driver");
        email=intent.getStringExtra("email");
        completeStore=findViewById(R.id.completeStore);
        completeName=findViewById(R.id.completeName);
        completenumber=findViewById(R.id.completenumber);
        completedes=findViewById(R.id.completedes);
        completedriver=findViewById(R.id.completedriver);
        complete=findViewById(R.id.complete);
        cancel=findViewById(R.id.cancel);
        complete.setOnClickListener(this);
        cancel.setOnClickListener(this);
        setData();

    }
    public void setData() {
        completeStore.setText(store);
        completeName.setText(foodname);
        completenumber.setText(number);
        completedes.setText(des);
        completedriver.setText(driver);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.cancel) {
            finish();
        }
        else {
            completeOrder();
        }

    }


    private void completeOrder(){

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbx5zar4dlWTbA0Km2EMCdMBOPWkMxSQqLQL3opH-whyhhoPDvivF6qyLJQYz-ore8vsEA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(OndoingAccept.this, "success",
                                Toast.LENGTH_SHORT).show();
                        finish();

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
                params.put("action","completeOrder");
                params.put("store",store);
                params.put("email",email);
                params.put("foodname",foodname);
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