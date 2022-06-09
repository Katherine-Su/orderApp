package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Identity extends AppCompatActivity
    implements View.OnClickListener, VDialog.VDialogListener {

    Button customer, shopkeeper, driver;
    String vvc="";
    int id;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        customer=(Button) findViewById(R.id.customer);
        shopkeeper=(Button) findViewById(R.id.shopkeeper);
        driver=(Button) findViewById(R.id.driver);

        customer.setOnClickListener(this);
        shopkeeper.setOnClickListener(this);
        driver.setOnClickListener(this);
        Intent it=getIntent();
        email=it.getStringExtra("email");
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.shopkeeper){
            id=R.id.shopkeeper;
            openDialog();
        }
        else if (view.getId()==R.id.driver){
            id=R.id.driver;
            openDialog();

        }
        else{
            Intent intent=new Intent(Identity.this,Customerdo.class);
            intent.putExtra("email",email);
            //intent.putExtra("id","customer");
            startActivity(intent);
        }
    }

    public void openDialog(){
        VDialog vDialog=new VDialog();
        vDialog.show(getSupportFragmentManager(),"vcode");
    }

    @Override
    public void checkvcode(String vcode) {
        //vvc=vcode;
        if (id==R.id.shopkeeper){
            if (vcode.equals("hhooee")){
                Intent intent=new Intent(Identity.this,StoreOwn.class);
                intent.putExtra("email",email);
                //intent.putExtra("id","shopkeeper");
                startActivity(intent);
                //
            }
            else{
                Toast.makeText(Identity.this,"verification code is not correct",
                        Toast.LENGTH_SHORT).show();
            }
        }
       else if (id==R.id.driver){
            if (vcode.equals("cciiojk")){
                Intent intent=new Intent(Identity.this,Driverdo.class);
                intent.putExtra("email",email);
                startActivity(intent);
            }
            else{
                Toast.makeText(Identity.this,"verification code is not correct",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}