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

public class OndoingOrder extends AppCompatActivity
    implements AdapterView.OnItemClickListener {

    ImageView ondoingerr, ondoingcry;
    TextView ondoingtx;
    ListView ondoinglv;
    int visibility= View.GONE;
    String url="https://script.google.com/macros/s/AKfycbzh5HrWSayGqqhBcTIv8b-CZN11ZGhYAvOoAeu50_hz-S9uu7qrF9-WkTSGbPttZn5g2Q/exec?action=ondoing&email=";
    String email;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    SimpleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ondoing_order);

        ondoingtx=findViewById(R.id.ondoingtx);
        ondoinglv=findViewById(R.id.ondoinglv);
        ondoinglv.setOnItemClickListener(this);
        ondoingerr=findViewById(R.id.ondoingerr);
        ondoingcry=findViewById(R.id.ondoingcry);
        ondoingerr.setVisibility(visibility);
        ondoingcry.setVisibility(visibility);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        url+=email;
        Toast.makeText(OndoingOrder.this, "wait to read ondoing order", Toast.LENGTH_SHORT).show();
        readOndoing();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        adapter=new SimpleAdapter(this,list,R.layout.ondoing_com,
                new String[]{"store","foodname","number","description","date","person_in_charge"},
                new int[]{R.id.ondoingStore,R.id.ondoingName,R.id.ondoingnumber,R.id.ondoingdes,R.id.ondoingdate,R.id.ondoingdriver});
        ondoinglv.setAdapter(adapter);
        readOndoing();


    }

    private void readOndoing(){
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

                String store=jo.getString("store");
                String foodname=jo.getString("foodname");
                String number=jo.getString("number");
                String description=jo.getString("description");
                String date=jo.getString("date");
                String person_in_charge=jo.getString("person in charge");
                HashMap<String,String> item=new HashMap<>();
                item.put("store",store);
                item.put("foodname",foodname);
                item.put("number",number);
                item.put("description",description);
                item.put("date",date);
                item.put("person_in_charge",person_in_charge);
                list.add(item);


            }
        } catch(JSONException e){
            Toast.makeText(OndoingOrder.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (list.isEmpty()){
            ondoingtx.setText("You don't have any order");
            visibility= View.VISIBLE;
            ondoingerr.setVisibility(visibility);
            ondoingcry.setVisibility(visibility);
        }
        else {
//            ondoingtx.setText("Your order");
            ondoingtx.setVisibility(View.GONE);
            adapter=new SimpleAdapter(this,list,R.layout.ondoing_com,
                    new String[]{"store","foodname","number","description","date","person_in_charge"},
                    new int[]{R.id.ondoingStore,R.id.ondoingName,R.id.ondoingnumber,R.id.ondoingdes,R.id.ondoingdate,R.id.ondoingdriver});
            ondoinglv.setAdapter(adapter);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TextView storetx=view.findViewById(R.id.ondoingStore);
        TextView nametx=view.findViewById(R.id.ondoingName);
        TextView numbertx=view.findViewById(R.id.ondoingnumber);
        TextView destx=view.findViewById(R.id.ondoingdes);
        TextView drivertx=view.findViewById(R.id.ondoingdriver);
        Intent intent=new Intent(OndoingOrder.this,OndoingAccept.class);
        intent.putExtra("store",storetx.getText().toString());
        intent.putExtra("foodname",nametx.getText().toString());
        intent.putExtra("number",numbertx.getText().toString());
        intent.putExtra("des",destx.getText().toString());
        intent.putExtra("driver",drivertx.getText().toString());
        intent.putExtra("email",email);
        startActivity(intent);
    }
}