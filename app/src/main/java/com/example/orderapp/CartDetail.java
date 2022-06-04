package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class CartDetail extends AppCompatActivity
    implements View.OnClickListener {

    Button yes,no;
    String store,foodname,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_detail);
        yes=findViewById(R.id.yes);
        no=findViewById(R.id.no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);
        Intent intent=getIntent();
        foodname=intent.getStringExtra("foodname");
        store=intent.getStringExtra("store");
        email=intent.getStringExtra("email");

    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.yes){
            deleteD();
        }
        else {
            finish();
        }
    }

    private void deleteD(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbync-StF-B76W-1CEDfLpJrNRYQm1kbA6LBnoVH4kn6-YYr3bKYGLr9ggijEK9NfYE5dg/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(CartDetail.this, "success",
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
                params.put("action","deleteCart");
                params.put("email",email);
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