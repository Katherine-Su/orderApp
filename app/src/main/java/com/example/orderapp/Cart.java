package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cart extends AppCompatActivity
    implements AdapterView.OnItemClickListener, View.OnClickListener{

    ListView lv;
    ArrayList<HashMap<String,String>> list=new ArrayList<>();
    String email;
    SimpleAdapter adapter;
    TextView checkcart;
    Button porder;
    ImageView cartError, cartCry;
    int visibility=View.GONE;


    @Override
    protected void onRestart() {
        super.onRestart();
        porder.setVisibility(View.GONE);
        list.clear();
        adapter=new SimpleAdapter(this,list,R.layout.cart_item,
                new String[]{"store","foodname","number","description"},new int[]{R.id.ondoingStore,R.id.ondoingnumber,R.id.ondoingdes,R.id.ondoingdriver});
        lv.setAdapter(adapter);
        readCart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
        lv=findViewById(R.id.llv);
        lv.setOnItemClickListener(this);

        porder=findViewById(R.id.porder);
        porder.setOnClickListener(this);
        cartError=findViewById(R.id.cartError);
        cartCry=findViewById(R.id.cartCry);
        cartError.setVisibility(visibility);
        cartCry.setVisibility(visibility);
        porder.setVisibility(visibility);
        checkcart=findViewById(R.id.checkcart);
        Toast.makeText(Cart.this,"wait to read cart",Toast.LENGTH_SHORT).show();
        readCart();
    }
    private void readCart(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET,
                "https://script.google.com/macros/s/AKfycbxMquqsPzKBRoywVXORInh1MFh8OCx3XCWd_KaG3Zl9xZC93P0U3L-TQaEnZZNHUN1nSg/exec?action=Customer&actionnext=Cart",
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
                if (!who.equals(email)){
                    continue;
                }
                String store=jo.getString("store");
                String foodname=jo.getString("foodname");
                String number=jo.getString("number");
                String description=jo.getString("description");
                HashMap<String,String> item=new HashMap<>();
                item.put("store",store);
                item.put("foodname",foodname);
                item.put("number",number);
                item.put("description",description);
                list.add(item);


            }
        } catch(JSONException e){
            Toast.makeText(Cart.this,"good",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        if (list.isEmpty()){
            checkcart.setText("You don't have any item in cart");
            visibility=View.VISIBLE;
            cartError.setVisibility(visibility);
            cartCry.setVisibility(visibility);
        }
        else {
            checkcart.setText("Item in the cart");
            porder.setVisibility(View.VISIBLE);
            adapter=new SimpleAdapter(this,list,R.layout.cart_item,
                    new String[]{"store","foodname","number","description"},new int[]{R.id.ondoingStore,R.id.ondoingnumber,R.id.ondoingdes,R.id.ondoingdriver});
            lv.setAdapter(adapter);
        }




    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        TextView tv=(TextView) view.findViewById(R.id.ondoingnumber);
        TextView tvs=(TextView) view.findViewById(R.id.ondoingStore);
        Intent intent=new Intent(Cart.this,CartDetail.class);
        intent.putExtra("foodname",tv.getText().toString());
        intent.putExtra("store",tvs.getText().toString());
        intent.putExtra("email",email);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(Cart.this,"is doing",Toast.LENGTH_SHORT).show();
        placetoorder();

    }
    private void placetoorder(){
        String nowDate=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date());

        StringRequest stringRequest=new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbxNrLcQXqFsCIZjPfehSPV-9UvOb32Nbki0hzRgEHYzMChU1QJSxZaoZMzuAHGTIGiTsA/exec",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(Cart.this, "success",
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
                params.put("action","neworder");

                params.put("who",email);
                params.put("date",nowDate);
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