package com.example.registration;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DeleteFragment extends Fragment {
    final String TAG = "DELETE";
    View view;
    EditText emailTxt;
    Button deleteButton;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    String deletedAccount;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_delete, container, false);
        deleteButton = view.findViewById(R.id.buttonDelete);
        emailTxt = view.findViewById(R.id.editTextTextEmailAddress4);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedAccount = emailTxt.getText().toString();
                //validation
                if(!deletedAccount.isEmpty()){
                    delete();
                }
                else{
                    showToast("Please make sure there are no empty fields!");
                }
                showToast("Successfully Deleted!");
                logOut(); // back to login after deleting
            }
        });
        return view;
    }

    private void logOut(){
        FirebaseAuth.getInstance().signOut(); //logout user
        loginActivity(); //re authenticate
    }

    private void loginActivity() {
        Intent intent = new Intent(getActivity(),Login.class);
        startActivity(intent);
    }

    private void reAuth(AuthCredential credential){
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User re-authenticated.");
                            delete();
                        } else {
                            Log.d(TAG, "User re-authentication failed.");
                        }
                    }
                });
    }
    private void delete(){
        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
    }
    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /**
    public void delete(String username){
        database.collection("UserData").document(username).delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        showToast("Successfully Deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        showToast("Delete Failed!");
                    }
                });
    }**/


}