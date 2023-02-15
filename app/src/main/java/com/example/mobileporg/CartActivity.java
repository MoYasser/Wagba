package com.example.mobileporg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

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

public class CartActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DatabaseReference mRef;
    DatabaseReference cRef;
    FirebaseAuth mAuth;
    ArrayList<CartModel> cartModelArrayList;
    RecyclerView cartRecycler;
    CartAdapter cartAdapter;
    LinearLayoutManager linearLayoutManager;
    Spinner gateSpinner;
    Spinner timeSpinner;
    TextView finalPriceTxt;
    EditText cartPhoneInput;
    String totalPrice;
    Button placeOrderBtn;

    String[] gates = {"Gate 3" , "Gate 4"};
    String[] times = {"12:00 PM", "3:00 PM"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_cart);
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference().child("users");
        cRef = FirebaseDatabase.getInstance().getReference();

        fetchFirebaseData();

        gateSpinner = findViewById(R.id.gateSpinner);
        timeSpinner = findViewById(R.id.timeSpinner);
        finalPriceTxt =findViewById(R.id.finalPriceTxt);
        cartPhoneInput = findViewById(R.id.cartPhoneInput);
        placeOrderBtn = findViewById(R.id.placeOrderBtn);
        cartModelArrayList = new ArrayList<>();
        cartRecycler = findViewById(R.id.cartRecycler);
        linearLayoutManager = new LinearLayoutManager(this);
        bottomNavigationView =  findViewById(R.id.mainNav);
        bottomNavigationView.setSelectedItemId(R.id.cart);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(CartActivity.this, MainActivity.class));
                        finish();
                        return true;
                    case R.id.cart:

                        return true;
                    case R.id.orders:
                        startActivity(new Intent(CartActivity.this, OrderActivity.class));
                        finish();
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(CartActivity.this, ProfileActivity.class));
                        finish();
                        return true;

                }
                return false;
            }
        });

        //gateSpinner.setOnItemSelectedListener(this);
        ArrayAdapter gateAdapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                gates);
        gateAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        gateSpinner.setAdapter(gateAdapter);
        ArrayAdapter timeAdapter
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                times);
        timeAdapter.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);
        timeSpinner.setAdapter(timeAdapter);

        placeOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DataSnapshot items = snapshot.child("users").child(mAuth.getUid()).child("cart").child("items");
                        OrderModel orderModel = new OrderModel();
                        orderModel.setGateTxt(gateSpinner.getSelectedItem().toString());
                        orderModel.setTimeTxt(timeSpinner.getSelectedItem().toString());
                        orderModel.setStatusTxt("Accepted");
                        String phoneNumber = cartPhoneInput.getText().toString();
                        if(TextUtils.isEmpty(phoneNumber)){
                            cartPhoneInput.setError("Phone Number Can Not Be Empty");
                            cartPhoneInput.requestFocus();
                        }else{
                            orderModel.setPhoneNumberTxt(phoneNumber);
                            orderModel.setOrderPriceTxt(totalPrice);
                            ArrayList<String> itemsModelArrayList = new ArrayList<>();
                            for(DataSnapshot mSnap : items.getChildren()){

                                String itemsModel = mSnap.child("itemNameTxt").getValue().toString();

                                //itemsModel.setName();
                                itemsModelArrayList.add(itemsModel);
                            }
                            orderModel.setItemsModelArrayList(itemsModelArrayList);

                            cRef.child("users").child(mAuth.getUid()).child("orders").push().setValue(orderModel);
                            itemsModelArrayList.clear();
                            cartModelArrayList.clear();
                            items.getRef().removeValue();
                            snapshot.child("users").child(mAuth.getUid()).child("cart").child("price").getRef().setValue("0");
                            cartPhoneInput.setText("");
                            cartAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



    }

    @Override
    public void onBackPressed() {
        bottomNavigationView.setSelectedItemId(R.id.home);
    }

    // Performing action when ItemSelected
    // from spinner, Overriding onItemSelected method

    private void fetchFirebaseData() {
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot items = snapshot.child(mAuth.getUid()).child("cart").child("items");
                for(DataSnapshot mSnap : items.getChildren()){
                    CartModel cartModel = new CartModel();
                    cartModel.setCartImage(mSnap.child("itemImage").getValue().toString());
                    cartModel.setCartTxt(mSnap.child("itemNameTxt").getValue().toString());
                    cartModel.setCountTxt(mSnap.child("itemCount").getValue().toString());
                    cartModel.setCartItemPrice(mSnap.child("itemPriceTxt").getValue().toString());
                    cartModelArrayList.add(cartModel);

//                    totalPrice = Integer.valueOf(mSnap.child("itemPriceTxt").getValue().toString()) * Integer.valueOf(mSnap.child("itemCount").getValue().toString());
                    finalPriceTxt.setText(snapshot.child(mAuth.getUid()).child("cart").child("price").getValue().toString());
                }


                cartAdapter = new CartAdapter(cartModelArrayList,getApplicationContext());
                cartRecycler.setLayoutManager(linearLayoutManager);
                cartRecycler.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                finalPriceTxt.setText(snapshot.child(mAuth.getUid()).child("cart").child("price").getValue().toString());
                totalPrice = snapshot.child(mAuth.getUid()).child("cart").child("price").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    @Override
//    public void onItemSelected(AdapterView<*> arg0,
//                               View arg1,
//                               int position,
//                               long id)
//    {
//
//        // make toastof name of course
//        // which is selected in spinner
//        Toast.makeText(getApplicationContext(),
//                        courses[position],
//                        Toast.LENGTH_LONG)
//                .show();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<*> arg0)
//    {
//        // Auto-generated method stub
//    }
}