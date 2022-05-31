package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StoreDetail extends AppCompatActivity
    implements View.OnClickListener{

    ImageView mainImageView;
    TextView title, description;
    String data1, data2;
    int myImage;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        mainImageView=findViewById(R.id.mainImage);
        title=findViewById(R.id.storeTitle);
        description=findViewById(R.id.storeDe);
        back=findViewById(R.id.back);
        back.setOnClickListener(this);

        getData();
        setData();
    }

    private void getData() {
        if (getIntent().hasExtra("myImage") && getIntent().hasExtra("data1") &&
        getIntent().hasExtra("data2")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            myImage = getIntent().getIntExtra("myImage",1);
        }
        else {
            Toast.makeText(this,"No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        title.setText(data1);
        description.setText(data2);
        mainImageView.setImageResource(myImage);
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}