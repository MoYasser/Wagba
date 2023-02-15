package com.example.mobileporg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mRef;
    EditText nameSignupInput;
    EditText emailSignupInput;
    EditText passwordSignupInput;
    EditText genderInput;
    Button registerBtn;
    private static ProfileDB profileDB;
    private static ProfileDAO profileDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_signup);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();

        nameSignupInput = findViewById(R.id.nameSignupInput);
        emailSignupInput = findViewById(R.id.emailSignupInput);
        passwordSignupInput = findViewById(R.id.passwordSignupInput);
        registerBtn = findViewById(R.id.registerBtn);
        genderInput = findViewById(R.id.genderInput);

        profileDB = Room.databaseBuilder(
                getApplicationContext(),
                ProfileDB.class,
                "profileDB"
        ).allowMainThreadQueries().build();
        profileDAO = profileDB.userDao();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();

            }
        });
    }

    private void createUser(){
        String name = nameSignupInput.getText().toString();
        String email = emailSignupInput.getText().toString();
        String password = passwordSignupInput.getText().toString();
        String gender = genderInput.getText().toString();

        if(TextUtils.isEmpty(name)){
            nameSignupInput.setError("Name Can Not Be Empty");
            nameSignupInput.requestFocus();
        }else if(TextUtils.isEmpty(email)){
            emailSignupInput.setError("Email Can Not Be Empty");
            emailSignupInput.requestFocus();
        }else if(TextUtils.isEmpty(password)) {
            passwordSignupInput.setError("Password Can Not Be Empty");
            passwordSignupInput.requestFocus();
        }else if(TextUtils.isEmpty(gender)){
            genderInput.setError("Gender Can Not Be Empty");
            genderInput.requestFocus();
        }else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        mRef.child("users").child(mAuth.getUid()).child("cart").child("price").setValue("0");
                        mRef.child("users").child(mAuth.getUid()).child("uid").setValue(mAuth.getUid());
                        Toast.makeText(SignupActivity.this,"User Registered",Toast.LENGTH_SHORT).show();
                        Profile profile = new Profile(email, name, gender);
                        profileDAO.insert(profile);
                        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Toast.makeText(SignupActivity.this,"Error:" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}