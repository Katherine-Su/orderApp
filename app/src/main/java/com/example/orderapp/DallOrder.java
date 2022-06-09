package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class DallOrder extends AppCompatActivity
    implements AdapterView.OnItemClickListener {

    String url="https://script.google.com/macros/s/AKfycbzjWxq6u_eymXwOH5Edik33aoybPo2HYsK3FGc6RBf9A0YZAUhQW6y7q18oEWdzbT6pUA/exec";
    TextView dallOrder;
    String email;
    ListView dallOrderlv;
    ImageView dallerr, dallcry;
    int visibility=View.GONE;
    SimpleAdapter adapter;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        adapter=new SimpleAdapter(this,list,R.layout.driver_order,
                new String[]{"who","store","foodname","number","description","date"},new int[]{R.id.driverwho,R.id.driverstore,R.id.drivername,R.id.drivernumber,R.id.driverdes,R.id.driverdate});
        dallOrderlv.setAdapter(adapter);
        readOrder();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dall_order);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        dallOrderlv=findViewById(R.id.dallOrderlv);
        dallOrderlv.setOnItemClickListener(this);
        dallOrder=findViewById(R.id.dallOrder);
        dallerr=findViewById(R.id.dallerr);
        dallcry=findViewById(R.id.dallcry);
        dallerr.setVisibility(visibility);
        dallcry.setVisibility(visibility);
        url+="?action=driverOrder";
        Toast.makeText(DallOrder.this,"wait to read the all order", Toast.LENGTH_LONG).show();
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

                String who=jo.getString("who");
                String store=jo.getString("store");
                String foodname=jo.getString("foodname");
                String number=jo.getString("number");
                String description=jo.getString("description");
                String date=jo.getString("date");
//                String person_in_charge=jo.getString("person in charge");
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
            Toast.makeText(DallOrder.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (list.isEmpty()){
            dallOrder.setText("You don't have any order");
            visibility= View.VISIBLE;
            dallerr.setVisibility(visibility);
            dallcry.setVisibility(visibility);
        }
        else {
            dallOrder.setText("Your order");
            adapter=new SimpleAdapter(this,list,R.layout.driver_order,
                    new String[]{"who","store","foodname","number","description","date"},new int[]{R.id.driverwho,R.id.driverstore,R.id.drivername,R.id.drivernumber,R.id.driverdes,R.id.driverdate});
            dallOrderlv.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        TextView whotx=view.findViewById(R.id.driverwho);
        TextView foodnametx=view.findViewById(R.id.drivername);
        TextView numbertx=view.findViewById(R.id.drivernumber);
        TextView destx=view.findViewById(R.id.driverdes);
        TextView storetx=view.findViewById(R.id.driverstore);
        Intent intent=new Intent(DallOrder.this,WantOrder.class);
        intent.putExtra("who",whotx.getText().toString());
        intent.putExtra("foodname",foodnametx.getText().toString());
        intent.putExtra("number",numbertx.getText().toString());
        intent.putExtra("des",destx.getText().toString());
        intent.putExtra("email",email);
        intent.putExtra("store",storetx.getText().toString());
        startActivity(intent);
    }
}