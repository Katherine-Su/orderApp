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

public class CompleteOrder extends AppCompatActivity {

    SimpleAdapter adapter;
    ListView completelv;
    ImageView completeerr, completecry;
    TextView completeOrder;
    int visibility=View.GONE;
    String email;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    String url="https://script.google.com/macros/s/AKfycbx6AMEZr_PN7sltidzzJmgkfVJNkIG57UxW6vHSPDxGbiKBF9tyIsKFla4vFgpXd17SsA/exec?action=completeOrder&email=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        completelv=findViewById(R.id.completelv);
        completeOrder=findViewById(R.id.completeOrder);
        completeerr=findViewById(R.id.completeerr);
        completecry=findViewById(R.id.completecry);
        completeerr.setVisibility(visibility);
        completecry.setVisibility(visibility);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        url+=email;
        Toast.makeText(CompleteOrder.this, "wait to read complete order", Toast.LENGTH_SHORT).show();
        readCompletOrder();

    }
    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        adapter=new SimpleAdapter(this,list,R.layout.ondoing_com,
                new String[]{"store","foodname","number","description","date","person_in_charge"},
                new int[]{R.id.ondoingStore,R.id.ondoingName,R.id.ondoingnumber,R.id.ondoingdes,R.id.ondoingdate,R.id.ondoingdriver});
        completelv.setAdapter(adapter);
        readCompletOrder();
    }
    private void readCompletOrder(){
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
//                String who=jo.getString("who");

                String store=jo.getString("store");
                String foodname=jo.getString("foodname");
                String number=jo.getString("number");
                String description=jo.getString("description");
                String date=jo.getString("date");
                String person=jo.getString("person");
                HashMap<String,String> item=new HashMap<>();
                item.put("store",store);
                item.put("foodname",foodname);
                item.put("number",number);
                item.put("description",description);
                item.put("date",date);
                item.put("person",person);
                list.add(item);


            }
        } catch(JSONException e){
            Toast.makeText(CompleteOrder.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (list.isEmpty()){
            completeOrder.setText("You don't have any complete order");
            visibility= View.VISIBLE;
            completeerr.setVisibility(visibility);
            completecry.setVisibility(visibility);
        }
        else {
            adapter=new SimpleAdapter(this,list,R.layout.ondoing_com,
                    new String[]{"store","foodname","number","description","date","person"},
                    new int[]{R.id.ondoingStore,R.id.ondoingName,R.id.ondoingnumber,R.id.ondoingdes,R.id.ondoingdate,R.id.ondoingdriver});
            completelv.setAdapter(adapter);
        }




    }
}