package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Home extends AppCompatActivity {
    Button updateFragment, deleteFragment;
    TextView userDetail, logout;
    FirebaseAuth auth;
    FirebaseUser loggedInUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        auth = FirebaseAuth.getInstance();
        //references to ui elements
        updateFragment = findViewById(R.id.updateFragmentBtn);
        deleteFragment = findViewById(R.id.deleteFragmentBtn);
        userDetail = findViewById(R.id.textViewUserDetails);
        logout = findViewById(R.id.textViewLogout);
        loggedInUser = auth.getCurrentUser();

        if(loggedInUser == null){
            loginActivity();
            finish();
        }
        else{
            userDetail.setText(loggedInUser.getEmail()); //display username
        }

        //onclick listeners
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut(); //logout user
                loginActivity();
                finish();
            }
        });

        updateFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftFragment(new UpdateFragment());
            }
        });

        deleteFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shiftFragment(new DeleteFragment());
            }
        });
    }

    private void shiftFragment(Fragment fragment){
        FragmentManager fragManager = getSupportFragmentManager();
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        fragTransaction.replace(R.id.frameLayout, fragment);
        fragTransaction.commit();
    }

    private void loginActivity() {
        Intent intent = new Intent(getApplication(),Login.class);
        startActivity(intent);
    }


}