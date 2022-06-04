package com.example.orderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

public class Storelist extends AppCompatActivity {

    RecyclerView recyclerView;
    String email;
    String s1[],s2[];
    int images[] = {R.drawable.pizza_hut,R.drawable.mcd,R.drawable.pizza_hut,R.drawable.burger_king,
            R.drawable.domino_pizza,R.drawable.kura_sushi,R.drawable.sushi_ro,R.drawable.pentang_ama};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storelist);

        recyclerView=findViewById(R.id.recyclerView);

        s1=getResources().getStringArray(R.array.store_list);
        s2=getResources().getStringArray(R.array.description);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");

        Myadapter myadapter=new Myadapter(this,s1,s2,images,email);
        recyclerView.setAdapter(myadapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);



    }
}