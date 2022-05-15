package com.example.orderapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Register extends AppCompatActivity
    implements View.OnClickListener {

    TextView txv;
    EditText edt_username,edt_email,edt_pas;
    FirebaseAuth mAuth;
    Snackbar sbar;
    String username,email,password;
    Button register;
    DatabaseReference mDbRef;
   // Toast tos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txv=(TextView) findViewById(R.id.al_ac);
        txv.setOnClickListener(this);
        edt_username=(EditText) findViewById(R.id.edt_username);
        edt_email=(EditText) findViewById(R.id.edt_email);
        edt_pas=(EditText) findViewById(R.id.edt_pas);
        //sbar= Snackbar.make(findViewById(R.id.root),"",Snackbar.LENGTH_SHORT);
        mAuth = FirebaseAuth.getInstance();
        username=edt_username.getText().toString();

        register=(Button) findViewById(R.id.register);
        register.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.al_ac){
            startActivity(new Intent(this,Login.class));
        }
        else if (view.getId()==R.id.register){
            performRegister();
            startActivity(new Intent(Register.this,Login.class));
        }
    }
    private void performRegister(){
        email=edt_email.getText().toString();
        password=edt_pas.getText().toString();
        if (email.isEmpty() || password.isEmpty()){
            Snackbar.make(findViewById(R.id.root),"please enter the email/password",
                    Snackbar.LENGTH_SHORT).show();

        }
        else {

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                               Toast.makeText(Register.this,"register is successful",
                                       Toast.LENGTH_SHORT).show();
                                addUser();

                            } else {
                               Log.w("Main","createUserWithEmail:failure",task.getException());

                                Toast.makeText(Register.this,"register is fail",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    private void addUser(){
        String username=edt_username.getText().toString();
        String email=edt_email.getText().toString();
        String password=edt_pas.getText().toString();
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference usersRef=firebaseDatabase.getReference("users");
        DatabaseReference usernameRef=usersRef.child(username);
        Map<String,Object> user=new HashMap<>();
        user.put("email",email);
        user.put("password",password);
        usernameRef.updateChildren(user);

    }


}