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

import java.util.HashMap;
import java.util.Map;

public class WantOrder extends AppCompatActivity
    implements View.OnClickListener {

    Button wantok, wantnotok;
    String store, who, foodname, number, des;
    TextView wantwho, wantfoodname, wantnumber, wantdes;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_want_order);
        wantok=findViewById(R.id.wantok);
        wantnotok=findViewById(R.id.wantnotok);
        wantok.setOnClickListener(this);
        wantnotok.setOnClickListener(this);
        wantwho=findViewById(R.id.driverwho);
        wantfoodname=findViewById(R.id.wantFoodname);
        wantnumber=findViewById(R.id.wantnumber);
        wantdes=findViewById(R.id.wantdes);
        Intent intent=getIntent();
        store=intent.getStringExtra("store");
        who=intent.getStringExtra("who");
        foodname=intent.getStringExtra("foodname");
        number=intent.getStringExtra("number");
        des=intent.getStringExtra("des");
        email=intent.getStringExtra("email");
        setData();

    }
    public void setData(){
        wantwho.setText(who);
        wantfoodname.setText(foodname);
        wantnumber.setText(number);
        wantdes.setText(des);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.wantok) {
            wantOrder();

        }
        else {
            finish();
        }
    }


    private void wantOrder(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbzjWxq6u_eymXwOH5Edik33aoybPo2HYsK3FGc6RBf9A0YZAUhQW6y7q18oEWdzbT6pUA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(WantOrder.this, "success",
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
                params.put("action","want");
                params.put("person",email);
                params.put("email",who);
                params.put("store",store);
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
