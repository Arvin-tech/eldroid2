package com.example.registration;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    final String TAG = "FIRESTORE";
    FirebaseFirestore database;
    FirebaseAuth mAuth;
    EditText emailTxt, passWordTxt;
    Button addButton;
    TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Retrieve an instance of your database using getInstance() and reference the location you want to write to.
        //database = FirebaseFirestore.getInstance();
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        //find view
        emailTxt = findViewById(R.id.editTextTextEmailAddress);
        passWordTxt = findViewById(R.id.editTextTextPassword2);
        login = findViewById(R.id.textViewLogin);
        addButton = findViewById(R.id.buttonAdd); //register

        //add button listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailTxt.getText().toString(); //value from username edit text
                String password = passWordTxt.getText().toString(); // value from password edit text
                //validation
                if(email.isEmpty())
                {
                    Toast.makeText(MainActivity.this,"Please enter an email", Toast.LENGTH_SHORT).show();
                }
                if(password.isEmpty()){
                    Toast.makeText(MainActivity.this,"Please enter password", Toast.LENGTH_SHORT).show();
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Successfully Created!",
                                            Toast.LENGTH_SHORT).show();
                                    loginActivity(); //proceed to login
                                    finish(); //close registration activity
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }//add button end
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginActivity();
            } //already have an account?
        });

    }

    private void loginActivity() {
        Intent intent = new Intent(getApplication(),Login.class);
        startActivity(intent);
    }

    /**
    public void add(String username, String password){
        //add user in database;
        Map<String, Object> users = new HashMap<>();
        users.put("username", username);
        users.put("password", password);

        database.collection("UserData").document(username).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successfully Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }**/

    private void updateUI(FirebaseUser user) {

    }

    private void reload() { }

    @Override
    protected void onStart(){
        super.onStart();
        Log.i(TAG, "onStart()");
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            reload();
        }

    }
    @Override
    protected void onResume(){
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

}
