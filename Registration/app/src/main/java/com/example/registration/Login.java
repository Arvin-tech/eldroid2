package com.example.registration;

import  androidx.annotation.NonNull;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity{
    final String TAG = "EmailPassword";
    Button loginButton;
    EditText emailTxt, passWordTxt;
    TextView signUp;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth and Firestore
        mAuth = FirebaseAuth.getInstance();

        //references to ui elements
        loginButton = findViewById(R.id.buttonLogin);
        emailTxt = findViewById(R.id.editTextTextEmailAddress2
        );
        passWordTxt = findViewById(R.id.editTextTextPassword);
        signUp = findViewById(R.id.textViewSignup);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString();
                String password = passWordTxt.getText().toString();
                //validation
               if(!email.isEmpty() && !password.isEmpty()){
                   mAuth.signInWithEmailAndPassword(email, password)
                           .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       Toast.makeText(Login.this,"Login Successful", Toast.LENGTH_SHORT).show();
                                       homeActivity(); //open dashboard/home
                                       finish(); //close login activity
                                   } else {
                                       // If sign in fails, display a message to the user.
                                       Toast.makeText(Login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }else{
                   Toast.makeText(Login.this,"Please make sure there are no empty fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrationActivity(); //open
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            homeActivity();
        }
    }

    private void registrationActivity(){
        Intent intent = new Intent(getApplication(),MainActivity.class); //click sign up textview
        startActivity(intent);
    }

    private void homeActivity() {
        Intent intent = new Intent(getApplication(),Home.class); //open home/dashboard activity
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {

    }

    private void reload(){

    }

}