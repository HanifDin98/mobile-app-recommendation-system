package com.Hanif.underboneapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
     EditText editText1,editText2;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    private Button bttnsave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editText1 = findViewById(R.id.EditregisterNameId);
        editText2 = findViewById(R.id.EditRegisterEmailId);
        //editText3 = findViewById(R.id.EditRegisterPhone);
        bttnsave = findViewById(R.id.buttonSave);

        Intent data = getIntent();
        String fullname = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore =FirebaseFirestore.getInstance();
        user =firebaseAuth.getCurrentUser();



//        Log.d(TAG, "onCreate: "+fullname+" "+email+" "+phone);
        bttnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText1.getText().toString().isEmpty()|| editText2.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this, "One of the field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email1 = editText2.getText().toString();
                user.updateEmail(email1).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(user.getUid());
                        Map<String,Object> edited = new HashMap<>();
                        edited.put("email",email1);
                        edited.put("name",editText1.getText().toString());
                        //edited.put("phoneNum",editText3.getText().toString());
                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Profile Update", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),ShowProfile.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        //Toast.makeText(EditProfile.this, "email changed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        editText1.setText(fullname);
        editText2.setText(email);
       // editText3.setText(phone);

    }
}