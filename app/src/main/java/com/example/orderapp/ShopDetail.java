package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.security.identity.IdentityCredentialException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShopDetail extends AppCompatActivity
    implements View.OnClickListener {

    TextView storefoodname,storefoodprice,storefooddescription, doublecheck;
    EditText updateprice, updatedes;
    String foodname,foodprice,description;
    String Upp, Upd;
    Button updatebtn, deletebtn,okupdate,notokupdate,okdelete,notokdelete;
    int visibility= View.GONE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_detail);

        storefoodname=findViewById(R.id.Storefoodname);
        storefoodprice=findViewById(R.id.Storefoodprice);
        storefooddescription=findViewById(R.id.Storefooddescription);
        doublecheck=findViewById(R.id.doublecheck);
        updateprice=findViewById(R.id.updateprice);
        updatebtn=findViewById(R.id.updatebtn);
        deletebtn=findViewById(R.id.deletebtn);
        okupdate=findViewById(R.id.okupdate);
        notokupdate=findViewById(R.id.notokupdate);
        okdelete=findViewById(R.id.okdelete);
        notokdelete=findViewById(R.id.notokdelete);
        updatedes=findViewById(R.id.updatedes);
        okupdate.setVisibility(visibility);
        notokupdate.setVisibility(visibility);
        okdelete.setVisibility(visibility);
        notokdelete.setVisibility(visibility);
        updateprice.setVisibility(visibility);
        updatedes.setVisibility(visibility);
        doublecheck.setVisibility(visibility);
        updatebtn.setOnClickListener(this);
        deletebtn.setOnClickListener(this);
        okupdate.setOnClickListener(this);
        notokupdate.setOnClickListener(this);
        okdelete.setOnClickListener(this);
        notokdelete.setOnClickListener(this);
        Intent intent=getIntent();
        foodname=intent.getStringExtra("foodname");
        foodprice=intent.getStringExtra("foodprice");
        description=intent.getStringExtra("fooddescrption");
        setData();
    }

    public void setData(){
        storefoodname.setText(foodname);
        storefoodprice.setText(foodprice);
        storefooddescription.setText(description);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.updatebtn){
            visibility=View.VISIBLE;
            updateprice.setVisibility(visibility);
            updatedes.setVisibility(visibility);
            okupdate.setVisibility(visibility);
            notokupdate.setVisibility(visibility);

        }
        else if (view.getId()==R.id.deletebtn){
            visibility=View.VISIBLE;
            doublecheck.setVisibility(visibility);
            okdelete.setVisibility(visibility);
            notokdelete.setVisibility(visibility);
        }
        else if (view.getId()==R.id.okupdate){
            updateD();
        }
        else if (view.getId()==R.id.notokupdate){
            visibility=View.GONE;
            updateprice.setVisibility(visibility);
            updatedes.setVisibility(visibility);
            okupdate.setVisibility(visibility);
            notokupdate.setVisibility(visibility);
        }
        else if (view.getId()==R.id.okdelete){
            deleteD();
        }
        else if (view.getId()==R.id.notokdelete){
            visibility=View.GONE;
            doublecheck.setVisibility(visibility);
            okdelete.setVisibility(visibility);
            notokdelete.setVisibility(visibility);
        }
    }
    public void updateD(){
        Toast.makeText(ShopDetail.this,"update is doing",Toast.LENGTH_SHORT).show();

    }
    public void deleteD(){
        Toast.makeText(ShopDetail.this,"delete is doing",Toast.LENGTH_SHORT).show();
    }
}