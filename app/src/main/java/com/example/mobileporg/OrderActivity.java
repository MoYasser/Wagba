package com.example.mobileporg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    OrderAdapter orderAdapter;
    RecyclerView orderRecycler;
    DatabaseReference mRef;
    FirebaseAuth mAuth;
    ArrayList<String> itemsModelArrayList;
    ArrayList<OrderModel> orderModelArrayList;
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_order);
        getSupportActionBar().hide();

        bottomNavigationView =  findViewById(R.id.mainNav);
        bottomNavigationView.setSelectedItemId(R.id.orders);
        orderRecycler = findViewById(R.id.orderRecycler);
        orderModelArrayList = new ArrayList<>();
        mRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();

        fetchFirebaseData();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(OrderActivity.this, MainActivity.class));
                        finish();
                        return true;
                    case R.id.cart:
                        startActivity(new Intent(OrderActivity.this, CartActivity.class));
                        finish();
                        return true;
                    case R.id.orders:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(OrderActivity.this, ProfileActivity.class));
                        finish();
                        return true;

                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    private void fetchFirebaseData() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot mSnap : snapshot.child(mAuth.getUid()).child("orders").getChildren()){
                    itemsModelArrayList = new ArrayList<>();
                    for(DataSnapshot nameSnap : mSnap.child("itemsModelArrayList").getChildren()){
                        String itemsModel = nameSnap.getValue().toString();
                        itemsModelArrayList.add(itemsModel);
                    }
                    OrderModel orderModel = new OrderModel();
                    orderModel.setGateTxt(mSnap.child("gateTxt").getValue().toString());
                    orderModel.setTimeTxt(mSnap.child("timeTxt").getValue().toString());
                    orderModel.setPhoneNumberTxt(mSnap.child("phoneNumberTxt").getValue().toString());
                    orderModel.setStatusTxt(mSnap.child("statusTxt").getValue().toString());
                    orderModel.setOrderPriceTxt(mSnap.child("orderPriceTxt").getValue().toString());
                    orderModel.setItemsModelArrayList(itemsModelArrayList);
                    orderModelArrayList.add(orderModel);
                }

                orderAdapter = new OrderAdapter(orderModelArrayList,getApplicationContext());
                orderRecycler.setLayoutManager(linearLayoutManager);
                orderRecycler.setAdapter(orderAdapter);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRef.child(mAuth.getUid()).child("orders").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (int i=0;i<orderModelArrayList.size();i++){
                    ArrayList<String> itemsModelFirebase;
                    itemsModelFirebase = (ArrayList<String>) snapshot.child("itemsModelArrayList").getValue();
                    if(orderModelArrayList.get(i).getItemsModelArrayList().equals(itemsModelFirebase)){
                        orderModelArrayList.get(i).setStatusTxt(snapshot.child("statusTxt").getValue().toString());
                        orderAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}