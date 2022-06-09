package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class DmyOrder extends AppCompatActivity {

    String url="https://script.google.com/macros/s/AKfycbzlfdwycRq7-iO1b90CNuMBhmUlgd6TYxxWL_D9BxZUOe-CLtCk4uWNKcIMCYnka1-W5Q/exec";
    String email;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    TextView dmyOrder;
    ImageView dmyerr, dmycry;
    int visibility=View.GONE;
    ListView myOrderlv;

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        adapter=new SimpleAdapter(this,list,R.layout.driver_order,
                new String[]{"who","store","foodname","number","description"},new int[]{R.id.driverwho,R.id.driverstore,R.id.namedd,R.id.drivernumber,R.id.driverdes});
        myOrderlv.setAdapter(adapter);
        readOrder();
    }

    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dmy_order);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        dmyOrder=findViewById(R.id.dmyOrder);
        dmyerr=findViewById(R.id.dmyerr);
        dmycry=findViewById(R.id.dmycry);
        dmyerr.setVisibility(visibility);
        dmycry.setVisibility(visibility);
        myOrderlv=findViewById(R.id.myOrderlv);
        Toast.makeText(DmyOrder.this,"wait to read your order", Toast.LENGTH_LONG).show();
        url+="?action=driverAcOrder";
        readOrder();
    }

    private void readOrder(){
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
                String person_in_charge=jo.getString("person in charge");
                if (!person_in_charge.equals(email)){
                    continue;
                }
                String who=jo.getString("who");
                String store=jo.getString("store");
                String foodname=jo.getString("foodname");
                String number=jo.getString("number");
                String description=jo.getString("description");
                String date=jo.getString("date");
                HashMap<String,String> item=new HashMap<>();
                item.put("who",who);
                item.put("store",store);
                item.put("foodname",foodname);
                item.put("number",number);
                item.put("description",description);
                item.put("date",date);
                list.add(item);


            }
        } catch(JSONException e){
            Toast.makeText(DmyOrder.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (list.isEmpty()){
            dmyOrder.setText("You don't have any order");
            visibility= View.VISIBLE;
            dmyerr.setVisibility(visibility);
            dmycry.setVisibility(visibility);
        }
        else {
            dmyOrder.setText("Your order");
            adapter=new SimpleAdapter(this,list,R.layout.driver_order,
                    new String[]{"who","store","foodname","number","description","date"},new int[]{R.id.driverwho,R.id.driverstore,R.id.drivername,R.id.drivernumber,R.id.driverdes,R.id.driverdate});
            myOrderlv.setAdapter(adapter);
        }

    }
}