package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.security.identity.IdentityCredentialException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ShopDetail extends AppCompatActivity
    implements View.OnClickListener {

    TextView storefoodname,storefoodprice,storefooddescription, doublecheck;
    EditText updateprice, updatedes;
    String foodname,foodprice,description,own;
    String Upp, Upd;
    Button updatebtn, deletebtn,okupdate,notokupdate,okdelete,notokdelete;
    int visibility= View.GONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        storefoodname=findViewById(R.id.Storefoodname);
        storefoodprice=findViewById(R.id.Storefoodprice);
        storefooddescription=findViewById(R.id.Storefooddescription);
        doublecheck=findViewById(R.id.doublecheck);
        updateprice=findViewById(R.id.updateprice);
        updatebtn=findViewById(R.id.updatebtn);
        deletebtn=findViewById(R.id.deletebtn);
        okupdate=findViewById(R.id.okupdate);
        notokupdate=findViewById(R.id.notokupdate);
        okdelete=findViewById(R.id.okdelete);
        notokdelete=findViewById(R.id.notokdelete);
        updatedes=findViewById(R.id.updatedes);
        okupdate.setVisibility(visibility);
        notokupdate.setVisibility(visibility);
        okdelete.setVisibility(visibility);
        notokdelete.setVisibility(visibility);
        updateprice.setVisibility(visibility);
        updatedes.setVisibility(visibility);
        doublecheck.setVisibility(visibility);
        updatebtn.setOnClickListener(this);
        deletebtn.setOnClickListener(this);
        okupdate.setOnClickListener(this);
        notokupdate.setOnClickListener(this);
        okdelete.setOnClickListener(this);
        notokdelete.setOnClickListener(this);
        Intent intent=getIntent();
        foodname=intent.getStringExtra("foodname");
        foodprice=intent.getStringExtra("foodprice");
        description=intent.getStringExtra("fooddescription");
        own=intent.getStringExtra("own");
        setData();
    }

    public void setData(){
        storefoodname.setText(foodname);
        storefoodprice.setText(foodprice);
        storefooddescription.setText(description);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.updatebtn){
            visibility=View.VISIBLE;
            updateprice.setVisibility(visibility);
            updatedes.setVisibility(visibility);
            okupdate.setVisibility(visibility);
            notokupdate.setVisibility(visibility);

        }
        else if (view.getId()==R.id.deletebtn){
            visibility=View.VISIBLE;
            doublecheck.setVisibility(visibility);
            okdelete.setVisibility(visibility);
            notokdelete.setVisibility(visibility);
        }
        else if (view.getId()==R.id.okupdate){
            Toast.makeText(ShopDetail.this,"update is doing",Toast.LENGTH_SHORT).show();
            updateD();
        }
        else if (view.getId()==R.id.notokupdate){
            visibility=View.GONE;
            updateprice.setVisibility(visibility);
            updatedes.setVisibility(visibility);
            okupdate.setVisibility(visibility);
            notokupdate.setVisibility(visibility);
        }
        else if (view.getId()==R.id.okdelete){
            Toast.makeText(ShopDetail.this,"delete is doing",Toast.LENGTH_SHORT).show();
            deleteD();

        }
        else if (view.getId()==R.id.notokdelete){
            visibility=View.GONE;
            doublecheck.setVisibility(visibility);
            okdelete.setVisibility(visibility);
            notokdelete.setVisibility(visibility);
        }
    }

    private void updateD(){
        Upp=updateprice.getText().toString();
        Upd=updatedes.getText().toString();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbz9WzChkAv8vS5syIV7023xW6EaoOXJehrPn0l9PGduEhHnnq_J2sMQT2KSWZBt34Bx0w/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ShopDetail.this, "success",
                                Toast.LENGTH_SHORT).show();
                        //finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ShopDetail.this, "fail",
                                Toast.LENGTH_SHORT).show();
                    }

                }
        ) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("action","update");
                params.put("actionnext",own);
                params.put("foodname",foodname);
                params.put("price",Upp);
                params.put("description",Upd);
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

    private void deleteD(){
        Upp=updateprice.getText().toString();
        Upd=updatedes.getText().toString();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbz9WzChkAv8vS5syIV7023xW6EaoOXJehrPn0l9PGduEhHnnq_J2sMQT2KSWZBt34Bx0w/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(ShopDetail.this, "success",
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
                params.put("action","delete");
                params.put("actionnext",own);
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