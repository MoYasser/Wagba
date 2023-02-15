package com.example.mobileporg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mRef;
    RecyclerView restaurantRecycler;
    ArrayList<RestaurantModel> restaurantModelArrayList;
    ArrayList<FoodModel> foodModelArrayList;
    RestaurantAdapter restaurantAdapter;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference();
        restaurantRecycler = findViewById(R.id.restaurantRecycler);

        restaurantModelArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);


        clearAll();
        
        fetchFirebaseData();
        bottomNavigationView =  findViewById(R.id.mainNav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(MainActivity.this, CartActivity.class));
                        finish();
                        return true;
                    case R.id.orders:
                        startActivity(new Intent(MainActivity.this, OrderActivity.class));
                        finish();
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                        finish();
                        return true;

                }
                return false;
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user == null){
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }
    }

    private void fetchFirebaseData() {
        Query query = mRef.child("restaurants");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    foodModelArrayList = new ArrayList<>();
                    for(DataSnapshot item : snapshot.child("items").getChildren()){
                        FoodModel foodModel = new FoodModel();
                        foodModel.setItemNameTxt(item.child("name").getValue().toString());
                        foodModel.setItemImage(item.child("image").getValue().toString());
                        foodModel.setAvailableImage(item.child("availability").getValue().toString());
                        foodModel.setItemPriceTxt(item.child("price").getValue().toString());
                        foodModel.setItemCount("1");
                        foodModelArrayList.add(foodModel);
                    }
                    RestaurantModel restaurantModel =  new RestaurantModel();

                    restaurantModel.setRestaurantName(snapshot.child("name").getValue().toString());
                    restaurantModel.setLogoImage(snapshot.child("logo").getValue().toString());
                    restaurantModel.setFoodModelList(foodModelArrayList);
                    restaurantModelArrayList.add(restaurantModel);
                }
                restaurantAdapter = new RestaurantAdapter(restaurantModelArrayList,getApplicationContext());
                restaurantRecycler.setAdapter(restaurantAdapter);
                restaurantAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void clearAll(){
        if(restaurantModelArrayList != null){
            restaurantModelArrayList.clear();
        }

        if(restaurantAdapter != null){
            restaurantAdapter.notifyDataSetChanged();
        }

        restaurantModelArrayList = new ArrayList<>();
    }
}