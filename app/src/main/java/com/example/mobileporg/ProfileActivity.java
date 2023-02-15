package com.example.mobileporg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    TextView nameTxt;
    TextView emailTxt;
    TextView genderTxt;
    Button logOutBtn;
    ProfileDB profileDB;
    ProfileDAO profileDAO;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        nameTxt = findViewById(R.id.nameTxt);
        emailTxt = findViewById(R.id.emailTxt);
        genderTxt = findViewById(R.id.genderTxt);
        logOutBtn = findViewById(R.id.logOutBtn);
        bottomNavigationView = findViewById(R.id.mainNav);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        mAuth = FirebaseAuth.getInstance();

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                        finish();
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(ProfileActivity.this, CartActivity.class));
                        finish();
                        return true;
                    case R.id.orders:
                        startActivity(new Intent(ProfileActivity.this, OrderActivity.class));
                        finish();
                        return true;
                    case R.id.profile:
                        return true;

                }
                return false;
            }
        });

        profileDB = Room.databaseBuilder(
                getApplicationContext(),
                ProfileDB.class,
                "profileDB"
        ).allowMainThreadQueries().build();
        profileDAO = profileDB.userDao();

       mAuth = FirebaseAuth.getInstance();
       mUser = mAuth.getCurrentUser();
       Profile profile = profileDAO.getUser(mUser.getEmail());
       nameTxt.setText(profile.getFullName().toString());
       emailTxt.setText(profile.getEmail());
       genderTxt.setText(profile.getGender());
    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setSelectedItemId(R.id.home);
    }
}