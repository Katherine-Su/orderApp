package com.example.orderapp;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity
    implements View.OnClickListener{


    Button btn;
    TextView txv,back_to_sign_up;
    FirebaseAuth mAuth;
    String email,password;
    EditText edt_email,edt_pas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txv=(TextView) findViewById(R.id.textView5);
       // txv.bringToFront();
        back_to_sign_up=(TextView) findViewById(R.id.back_to_sign_up);
        back_to_sign_up.setOnClickListener(this);
        btn=(Button) findViewById(R.id.blogin);
        btn.setOnClickListener(this);
        edt_email=(EditText) findViewById(R.id.email);
        edt_pas=(EditText) findViewById(R.id.pas);

        mAuth=FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.back_to_sign_up){
            finish();
        }
        else {
            performLogin();

        }

    }
    private void performLogin(){
        email=edt_email.getText().toString();
        password=edt_pas.getText().toString();
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseUser user=mAuth.getCurrentUser();
//                            Snackbar.make(findViewById(R.id.root1),"Login is successful",
//                                    Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(Login.this,"Login is successful",
                                    Toast.LENGTH_SHORT).show();
                            Intent it = new Intent(Login.this, Identity.class);
                            it.putExtra("email",email);
                            startActivity(it);

                        }
                        else{
//                            Snackbar.make(findViewById(R.id.root1),"Login is fail",
//                                    Snackbar.LENGTH_SHORT).show();
                            Toast.makeText(Login.this,"Login is fail",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}