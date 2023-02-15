package com.example.mobileporg;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    List<FoodModel> foodModelList;
    Context context;
    DatabaseReference mRef;
    FirebaseAuth mAuth;

    public FoodAdapter(List<FoodModel> foodModelList, Context context) {
        this.foodModelList = foodModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.food_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodAdapter.ViewHolder holder, int position) {
        mRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();

        Glide.with(context).load(foodModelList.get(position).getItemImage()).into(holder.itemImage);
        if(Integer.parseInt(foodModelList.get(position).getAvailableImage()) == 0){
            holder.availableImage.setImageResource(R.drawable.ic_red);
            holder.addBtn.setEnabled(false);
            holder.addBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.grey)));
        }
        if(Integer.parseInt(foodModelList.get(position).getAvailableImage()) == 1){
            holder.availableImage.setImageResource(R.drawable.ic_green);
            holder.addBtn.setEnabled(true);
        }
        holder.itemNameTxt.setText(foodModelList.get(position).getItemNameTxt());
        holder.itemPriceTxt.setText(foodModelList.get(position).getItemPriceTxt() + "\nEGP");
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DataSnapshot items = snapshot.child(mAuth.getUid()).child("cart").child("items");
                        int flag = 0;
                        for(DataSnapshot mSnap : items.getChildren()){
                            if(mSnap.child("itemNameTxt").getValue().toString().equals(foodModelList.get(holder.getAdapterPosition()).getItemNameTxt())){
                                mSnap.child("itemCount").getRef().setValue(String.valueOf(Integer.valueOf(mSnap.child("itemCount").getValue().toString())+1));
                                snapshot.child(mAuth.getUid()).child("cart").child("price").getRef().setValue(String.valueOf(Integer.valueOf(snapshot.child(mAuth.getUid()).child("cart").child("price").getValue().toString())+Integer.valueOf(foodModelList.get(holder.getAdapterPosition()).getItemPriceTxt())));
                                flag = 1;
                            }
                        }

                        if(flag == 0){
                            mRef.child(mAuth.getUid()).child("cart").child("items").push().setValue(foodModelList.get(holder.getAdapterPosition()));
                            snapshot.child(mAuth.getUid()).child("cart").child("price").getRef().setValue(String.valueOf(Integer.valueOf(snapshot.child(mAuth.getUid()).child("cart").child("price").getValue().toString())+Integer.valueOf(foodModelList.get(holder.getAdapterPosition()).getItemPriceTxt())));
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
    public int getItemCount() {
        return foodModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImage;
        TextView itemNameTxt;
        ImageView availableImage;
        TextView itemPriceTxt;
        Button addBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.itemImage);
            itemNameTxt = itemView.findViewById(R.id.itemNameTxt);
            availableImage = itemView.findViewById(R.id.availableImage);
            itemPriceTxt = itemView.findViewById(R.id.itemPriceTxt);
            addBtn = itemView.findViewById(R.id.addBtn);
        }
    }
}
