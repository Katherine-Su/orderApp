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
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StoreOrder extends AppCompatActivity
    implements AdapterView.OnItemClickListener {

    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    TextView storeOrder;
    ListView storeOrderlv;
    ImageView storeOrdererr, storeOrdercry;
    int visibility= View.GONE;
    String url="https://script.google.com/macros/s/AKfycbzjWxq6u_eymXwOH5Edik33aoybPo2HYsK3FGc6RBf9A0YZAUhQW6y7q18oEWdzbT6pUA/exec";
    String own;
    SimpleAdapter adapter;

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        adapter=new SimpleAdapter(this,list,R.layout.shopprder_item,
                new String[]{"who","foodname","number","description","date"},new int[]{R.id.storewho,R.id.storefoodname,R.id.storenumber,R.id.storedes,R.id.storedate});
        storeOrderlv.setAdapter(adapter);
        seeData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_order);

        storeOrder=findViewById(R.id.storeorder);
        storeOrderlv=findViewById(R.id.storeorderlv);
        storeOrdererr=findViewById(R.id.storeOrdererr);
        storeOrdercry=findViewById(R.id.storeOrdercry);
//        storeOrder.setVisibility(visibility);
        storeOrdererr.setVisibility(visibility);
        storeOrdercry.setVisibility(visibility);
        storeOrderlv.setOnItemClickListener(this);
        Intent intent=getIntent();
        own=intent.getStringExtra("own");
        url+="?action=Shoporder&actionnext=";
        url+=own;
        Toast.makeText(StoreOrder.this,"wait to read Order",Toast.LENGTH_SHORT).show();
        readData();
    }


    private void readData(){
        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbzjWxq6u_eymXwOH5Edik33aoybPo2HYsK3FGc6RBf9A0YZAUhQW6y7q18oEWdzbT6pUA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        seeData();
                        Toast.makeText(StoreOrder.this,"successful",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText(StoreOrder.this,"fail",Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("action","ShopOrder");
                params.put("actionnext",own);
                params.put("store",own);
                return params;
            }

        };

        int socketTimeout=5000;
        RetryPolicy policy=new DefaultRetryPolicy(socketTimeout,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        RequestQueue queue= Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
    private void seeData(){
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
                String foodname=jo.getString("foodname");
                String number=jo.getString("number");
                String description=jo.getString("description");
                String date=jo.getString("date");
                HashMap<String,String> item=new HashMap<>();
                item.put("who",who);
                item.put("foodname",foodname);
                item.put("number",number);
                item.put("description",description);
                item.put("date",date);
                list.add(item);


            }
        } catch(JSONException e){
            Toast.makeText(StoreOrder.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (list.isEmpty()){
            visibility=View.VISIBLE;
            storeOrder.setText("you don't have any order from customer");
            storeOrdererr.setVisibility(visibility);
            storeOrdercry.setVisibility(visibility);


        }
        else {
            storeOrder.setText("Item in the Order");
            adapter=new SimpleAdapter(this,list,R.layout.shopprder_item,
                    new String[]{"who","foodname","number","description","date"},new int[]{R.id.storewho,R.id.storefoodname,R.id.storenumber,R.id.storedes,R.id.storedate});
            storeOrderlv.setAdapter(adapter);
        }




    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TextView whotx=(TextView) view.findViewById(R.id.storewho);
        TextView foodnametv=(TextView) view.findViewById(R.id.storefoodname);
        TextView numbertv=(TextView) view.findViewById(R.id.storenumber);
        TextView destv=(TextView) findViewById(R.id.storedes);

        Intent intent=new Intent(StoreOrder.this,ChecktoOrder.class);
        intent.putExtra("own",own);
        intent.putExtra("who",whotx.getText().toString());
        intent.putExtra("foodname",foodnametv.getText().toString());
        intent.putExtra("number",numbertv.getText().toString());
        intent.putExtra("description",destv.getText().toString());
        startActivity(intent);
    }
}