package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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

public class FoodPage extends AppCompatActivity
    implements View.OnClickListener {

    TextView number,foodDename,foodDeprice,foodDedes;
    ImageButton plus,sub,cart;
    int total=0;
    EditText note;
    String foodname,foodprice,fooddescription,email,own;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);

        foodDename=findViewById(R.id.foodDename);
        foodDeprice=findViewById(R.id.foodDeprice);
        foodDedes=findViewById(R.id.fooddescription);
        number=findViewById(R.id.number);
        plus=findViewById(R.id.plus);
        sub=findViewById(R.id.sub);
        cart=findViewById(R.id.cart);
        note=findViewById(R.id.note);
        plus.setOnClickListener(this);
        sub.setOnClickListener(this);
        cart.setOnClickListener(this);
        Intent intent=getIntent();
        foodname=intent.getStringExtra("foodname");
        foodprice=intent.getStringExtra("foodprice");
        fooddescription=intent.getStringExtra("fooddescription");
        email=intent.getStringExtra("email");
        own=intent.getStringExtra("shop");
        setData();

    }

    private void setData(){
        foodDename.setText(foodname);
        foodDeprice.setText(foodprice);
        foodDedes.setText(fooddescription);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.plus){
            total++;
            number.setText(String.valueOf(total));
        }
        else if (view.getId()==R.id.sub){
            total--;
            if (total<0){
                total=0;
            }
            number.setText(String.valueOf(total));
        }
        else {

            Toast.makeText(FoodPage.this,"add to cart is success",Toast.LENGTH_SHORT).show();
            addtocart();
        }
    }

    private void addtocart(){
        //String foodname=foodDename.getText().toString();
        //String foodprice=foodDeprice.getText().toString();
        String orderdescription=note.getText().toString();
        String foodnumber=number.getText().toString();

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbw_JLdVYhutH8M-FfqMSBRm8PSiEt53ob7ZMxp11EzuDnILYMBJkwOQ18Ejgj5Ux7rCSA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(FoodPage.this, "success",
                                Toast.LENGTH_SHORT).show();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FoodPage.this, "fail",
                                Toast.LENGTH_SHORT).show();
                    }

                }
        ) {

            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("action","newcart");
                params.put("who",email);
                params.put("store",own);
                params.put("foodname",foodname);
                params.put("number",foodnumber);
                params.put("description",orderdescription);
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