package com.example.mobileporg;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder>{

    List<CartModel> cartModelList;
    Context context;
    DatabaseReference mRef;
    FirebaseAuth mAuth;

    public CartAdapter(List<CartModel> cartModelList, Context context) {
        this.cartModelList = cartModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        mRef = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();


        Glide.with(context).load(cartModelList.get(position).getCartImage()).into(holder.cartImage);
        holder.cartTxt.setText(cartModelList.get(position).getCartTxt());
        holder.countTxt.setText(cartModelList.get(position).getCountTxt());

        holder.plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DataSnapshot items = snapshot.child(mAuth.getUid()).child("cart").child("items");
                        for(DataSnapshot mSnap : items.getChildren()){
                            if(mSnap.child("itemNameTxt").getValue().toString().equals(cartModelList.get(holder.getAdapterPosition()).getCartTxt())){
                                mSnap.child("itemCount").getRef().setValue(String.valueOf(Integer.valueOf(mSnap.child("itemCount").getValue().toString())+1));
                                cartModelList.get(holder.getAdapterPosition()).setCountTxt(String.valueOf(Integer.valueOf(mSnap.child("itemCount").getValue().toString()) +1));
                                snapshot.child(mAuth.getUid()).child("cart").child("price").getRef().setValue(String.valueOf(Integer.valueOf(snapshot.child(mAuth.getUid()).child("cart").child("price").getValue().toString())+Integer.valueOf(cartModelList.get(holder.getAdapterPosition()).getCartItemPrice())));
                                notifyItemChanged(holder.getAdapterPosition());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        holder.minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DataSnapshot items = snapshot.child(mAuth.getUid()).child("cart").child("items");
                        for(DataSnapshot mSnap : items.getChildren()){
                            int pos = holder.getAdapterPosition();
                            if(mSnap.child("itemNameTxt").getValue().toString().equals(cartModelList.get(holder.getAdapterPosition()).getCartTxt())){
                                mSnap.child("itemCount").getRef().setValue(String.valueOf(Integer.valueOf(mSnap.child("itemCount").getValue().toString())-1));
                                cartModelList.get(holder.getAdapterPosition()).setCountTxt(String.valueOf(Integer.valueOf(mSnap.child("itemCount").getValue().toString()) -1));
                                snapshot.child(mAuth.getUid()).child("cart").child("price").getRef().setValue(String.valueOf(Integer.valueOf(snapshot.child(mAuth.getUid()).child("cart").child("price").getValue().toString())-Integer.valueOf(cartModelList.get(holder.getAdapterPosition()).getCartItemPrice())));
                                notifyItemChanged(holder.getAdapterPosition());
                                if(Integer.valueOf(mSnap.child("itemCount").getValue().toString()) == 1){
                                    mSnap.getRef().removeValue();
                                    notifyItemChanged(holder.getAdapterPosition());
                                    cartModelList.remove(holder.getAdapterPosition());
                                    notifyDataSetChanged();
                                    break;
                                }
                            }
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
        return cartModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cartImage;
        TextView cartTxt;
        Button plusBtn;
        Button minusBtn;
        TextView countTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cartImage = itemView.findViewById(R.id.cartImage);
            cartTxt =  itemView.findViewById(R.id.cartTxt);
            plusBtn = itemView.findViewById(R.id.plusBtn);
            minusBtn = itemView.findViewById(R.id.minusBtn);
            countTxt = itemView.findViewById(R.id.countTxt);
        }
    }
}
