package com.Hanif.underboneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText emailLogin,passwordLogin;
    TextView toRegister;
    Button loginBttn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailLogin = findViewById(R.id.loginEmailId);
        passwordLogin = findViewById(R.id.loginPasswordId);
        loginBttn = findViewById(R.id.buttonLogin);
        toRegister =findViewById(R.id.toRegisterId);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        loginBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailLogin.getText().toString().trim();
                String password = passwordLogin.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    emailLogin.setError("Email Required");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    passwordLogin.setError("Password Required");
                    return;
                }
                if(passwordLogin.length()<6){
                    passwordLogin.setError("Password must be greater than 6 character");
                    return;
                }

                //auhtenticate user;
                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                            checkUserAccsessLevel(firebaseAuth.getCurrentUser().getUid());
                            //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Input not correct", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });
    }

    private void checkUserAccsessLevel(String uid) {
        DocumentReference df = firebaseFirestore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                Log.d("TAG", "onSuccess: "+documentSnapshot.getData());
                if(documentSnapshot.getString("isAdmin")!= null){
//                    Intent intent = new Intent(getApplicationContext(),AdminMainActivity.class);
//                    startActivity(intent);
                    startActivity(new Intent(getApplicationContext(),AdminMainActivity.class));
                    finish();
                }
                if(documentSnapshot.getString("isUser")!= null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }

            }
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(),MainActivity.class));
//        }
//    }
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Register.class));
    }
}