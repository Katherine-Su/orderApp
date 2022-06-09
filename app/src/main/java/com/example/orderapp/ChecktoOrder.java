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

public class ChecktoOrder extends AppCompatActivity
    implements View.OnClickListener {

    String who, foodname,own, number, description;
    Button accept, notnow;
    TextView whotx, foodnametx, numbertx, destx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkto_order);

        Intent intent=getIntent();
        who=intent.getStringExtra("who");
        foodname=intent.getStringExtra("foodname");
        own=intent.getStringExtra("own");
        number=intent.getStringExtra("number");
        description=intent.getStringExtra("description");
        whotx=findViewById(R.id.driverwho);
        foodnametx=findViewById(R.id.wantFoodname);
        numbertx=findViewById(R.id.wantnumber);
        destx=findViewById(R.id.wantdes);
        whotx.setText(who);
        foodnametx.setText(foodname);
        numbertx.setText(number);
        destx.setText(description);
        accept=findViewById(R.id.wantok);
        notnow=findViewById(R.id.wantnotok);
        accept.setOnClickListener(this);
        notnow.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.wantok){
            acceptOrder();
        }
        else {
            finish();
        }
    }

    private void acceptOrder(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbzlfdwycRq7-iO1b90CNuMBhmUlgd6TYxxWL_D9BxZUOe-CLtCk4uWNKcIMCYnka1-W5Q/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ChecktoOrder.this,"successful",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(ChecktoOrder.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("action","acceptOrder");
                params.put("actionnext",own);
                params.put("who",who);
                params.put("foodname",foodname);
                return params;
            }

        };

        int socketTimeout=5000;
        RetryPolicy policy=new DefaultRetryPolicy(socketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}