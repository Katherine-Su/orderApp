package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import org.w3c.dom.ls.LSInput;

import java.util.ArrayList;
import java.util.HashMap;

public class Sentorder extends AppCompatActivity {
    String url="https://script.google.com/macros/s/AKfycbw4BCcgIKUZFy8KDozTxK7c56bKY1DQBUEpGF_HdsXA700cEmeAnOZVDvAlYS8DPD-jrg/exec";
    SimpleAdapter adapter;
    TextView checkSentOrder;
    String email;
    ListView lv;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();

    @Override
    protected void onRestart() {
        super.onRestart();

        list.clear();
        adapter=new SimpleAdapter(this,list,R.layout.cart_item,
                new String[]{"store","foodname","number","description"},new int[]{R.id.storedd,R.id.namedd,R.id.numberdd,R.id.desdd});
        lv.setAdapter(adapter);

        readSentorder();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sentorder);
        checkSentOrder=findViewById(R.id.checksentorder);
        lv=findViewById(R.id.lllv);
        Toast.makeText(Sentorder.this,"wait to read sent order",Toast.LENGTH_SHORT).show();
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        url+="?action=Customer&actionnext=Order&email=";
        url+=email;
        readSentorder();

    }

    private void readSentorder(){
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
        //ArrayList<HashMap<String,String>> list=new ArrayList<>();
        try{
            JSONObject jobj=new JSONObject(jsonResponse);
            JSONArray jarray =jobj.getJSONArray("items");
            for (int i=0;i<jarray.length();++i){
                JSONObject jo=jarray.getJSONObject(i);
                //String who=jo.getString("who");
//                if (!who.equals(email)){
//                    continue;
//                }
                String store=jo.getString("store");
                String foodname=jo.getString("foodname");
                String number=jo.getString("number");
                String description=jo.getString("description");
                String date=jo.getString("date");
                HashMap<String,String> item=new HashMap<>();
                item.put("store",store);
                item.put("foodname",foodname);
                item.put("number",number);
                item.put("description",description);
                item.put("date",date);
                list.add(item);


            }
        } catch(JSONException e){
            Toast.makeText(Sentorder.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (list.isEmpty()){
//            checkSentOrder.setText("You don't have any item in order");
            checkSentOrder.setText(email);
        }
        else {
            checkSentOrder.setText("Item in the order");

            adapter=new SimpleAdapter(this,list,R.layout.cart_item,
                    new String[]{"store","foodname","number","description"},new int[]{R.id.storedd,R.id.namedd,R.id.numberdd,R.id.desdd});
            lv.setAdapter(adapter);
        }




    }
}