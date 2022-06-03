package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FoodPage extends AppCompatActivity
    implements View.OnClickListener {

    TextView number,foodDename,foodDeprice,foodDedes;
    ImageButton plus,sub,cart;
    int total=0;
    EditText note;
    String foodname,foodprice,fooddescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_page);

        foodDename=findViewById(R.id.foodDename);
        foodDeprice=findViewById(R.id.foodDeprice);
        foodDedes=findViewById(R.id.fooddescription);
        number=findViewById(R.id.number);
        plus=findViewById(R.id.plus);
        sub=findViewById(R.id.sub);
        cart=findViewById(R.id.cart);
        note=findViewById(R.id.note);
        plus.setOnClickListener(this);
        sub.setOnClickListener(this);
        cart.setOnClickListener(this);
        Intent intent=getIntent();
        foodname=intent.getStringExtra("foodname");
        foodprice=intent.getStringExtra("foodprice");
        fooddescription=intent.getStringExtra("fooddescription");
        setData();

    }

    private void setData(){
        foodDename.setText(foodname);
        foodDeprice.setText(foodprice);
        foodDedes.setText(fooddescription);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.plus){
            total++;
            number.setText(String.valueOf(total));
        }
        else if (view.getId()==R.id.sub){
            total--;
            if (total<0){
                total=0;
            }
            number.setText(String.valueOf(total));
        }
        else {
            Toast.makeText(FoodPage.this,"add to cart is success",Toast.LENGTH_SHORT).show();
        }
    }
}