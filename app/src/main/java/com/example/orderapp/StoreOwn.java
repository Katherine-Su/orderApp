package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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
import java.util.Map;

public class StoreOwn extends AppCompatActivity
    implements AdapterView.OnItemClickListener {
    String url="https://script.google.com/macros/s/AKfycbz9WzChkAv8vS5syIV7023xW6EaoOXJehrPn0l9PGduEhHnnq_J2sMQT2KSWZBt34Bx0w/exec";
    String email,owns;
    TextView ownshop;
    String itemdescription;
    SimpleAdapter adapter;
    ListView lv;
    Button add;
    ArrayList<String> des=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_own);
        ownshop=findViewById(R.id.ownshop);
        lv=findViewById(R.id.storelv);
        lv.setOnItemClickListener(this);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        Toast.makeText(StoreOwn.this,"wait to check your own",Toast.LENGTH_SHORT).show();
        readOwn();
        Toast.makeText(StoreOwn.this,"wait for read data",Toast.LENGTH_SHORT).show();
//        url+="?action=";
//        url+=owns;
        //ownshop.setText(url);

    }
    private void readOwn(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbwhz6NuhQZL5EdfqTl5EzbSSbWsjDwdRFM8e8y1xjrdy3loHGm8o_2RdEMKgw-gZx1uMQ/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        owns=response;
                        ownshop.setText(owns);
                        url+="?action=";
                        url+=owns;
                        readData();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){

                    }
                }
                ){
                    @Override
                    protected Map<String,String> getParams(){
                        Map<String,String> params=new HashMap<>();
                        params.put("action","map");
                        params.put("email",email);
                        return params;
                    }

        };

        int socketTimeout=5000;
        RetryPolicy policy=new DefaultRetryPolicy(socketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);
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
            Toast.makeText(StoreOwn.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        //imgView.setImageResource(R.drawable.pig);
        adapter=new SimpleAdapter(this,list,R.layout.list_item,
                new String[]{"itemName","itemprice"},new int[]{R.id.foodname,R.id.foodprice});




        lv.setAdapter(adapter);


    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TextView nametv=(TextView) view.findViewById(R.id.foodname);
        TextView pricetv=(TextView) view.findViewById(R.id.foodprice);

        Intent intent=new Intent(StoreOwn.this,ShopDetail.class);

        intent.putExtra("foodname",nametv.getText().toString());
        intent.putExtra("foodprice",pricetv.getText().toString());

        intent.putExtra("fooddescription",des.get(position));
        //Toast.makeText(StoreDetail.this,"intent is good",Toast.LENGTH_SHORT).show();
        startActivity(intent);
    }
}