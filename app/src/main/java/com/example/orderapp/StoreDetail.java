package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class StoreDetail extends AppCompatActivity
    implements View.OnClickListener, AdapterView.OnItemClickListener {
    String url="https://script.google.com/macros/s/AKfycbz9WzChkAv8vS5syIV7023xW6EaoOXJehrPn0l9PGduEhHnnq_J2sMQT2KSWZBt34Bx0w/exec";
    ArrayList<String> des=new ArrayList<>();
    ImageView mainImageView;
    TextView title, description;
    String data1, data2,email;
    int myImage;
    ImageButton back,gotocart;
    ListView lv;
    SimpleAdapter adapter;
    String itemdescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        mainImageView=findViewById(R.id.mainImage);
        title=findViewById(R.id.storeTitle);
        description=findViewById(R.id.storeDe);
        back=findViewById(R.id.back);
        back.setOnClickListener(this);
        gotocart=findViewById(R.id.gotocart);
        gotocart.setOnClickListener(this);
        lv=findViewById(R.id.lv);
        lv.setOnItemClickListener(this);
        getData();
        url+="?action=";
        url+=data1;
        setData();
        Toast.makeText(StoreDetail.this,"waiting for read data",Toast.LENGTH_SHORT).show();
        readData();
    }

    private void getData() {
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") &&
        getIntent().hasExtra("data2")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage",1);
            email=getIntent().getStringExtra("email");

        }
        else {
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        title.setText(data1);
        //title.setText(url);
        description.setText(data2);
        mainImageView.setImageResource(myImage);
    }


    private void readData(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseItems(response);


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                });
        int socketTimeout=5000;
        RetryPolicy policy=new DefaultRetryPolicy(socketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    private void parseItems(String jsonResponse){
        ArrayList<HashMap<String,String>> list=new ArrayList<>();
        try{
            JSONObject jobj=new JSONObject(jsonResponse);
            JSONArray jarray =jobj.getJSONArray("items");
            for (int i=0;i<jarray.length();++i){
                JSONObject jo=jarray.getJSONObject(i);
                String itemName=jo.getString("itemName");
                String itemprice=jo.getString("itemprice");
                itemdescription=jo.getString("itemdescription");
                HashMap<String,String> item=new HashMap<>();
                item.put("itemName",itemName);
                item.put("itemprice",itemprice);
                des.add(itemdescription);
                //item.put("itemdescriprion",itemdescription);
                list.add(item);


            }
        } catch(JSONException e){
            Toast.makeText(StoreDetail.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        //imgView.setImageResource(R.drawable.pig);
        adapter=new SimpleAdapter(this,list,R.layout.list_item,
                new String[]{"itemName","itemprice"},new int[]{R.id.foodname,R.id.foodprice});




        lv.setAdapter(adapter);
        //Toast.makeText(StoreDetail.this,"waiting for read data",Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.back){
            finish();
        }
        else {
           // Toast.makeText(StoreDetail.this,"ok",Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(StoreDetail.this,Cart.class);
            intent.putExtra("email",email);
            startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TextView nametv=(TextView) view.findViewById(R.id.foodname);
        TextView pricetv=(TextView) view.findViewById(R.id.foodprice);

        Intent intent=new Intent(StoreDetail.this,FoodPage.class);

        intent.putExtra("foodname",nametv.getText().toString());
        intent.putExtra("foodprice",pricetv.getText().toString());
        intent.putExtra("shop",data1);
        intent.putExtra("email",email);
        intent.putExtra("fooddescription",des.get(position));
        //Toast.makeText(StoreDetail.this,"intent is good",Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
}