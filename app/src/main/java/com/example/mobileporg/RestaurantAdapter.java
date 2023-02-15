package com.example.mobileporg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ViewHolder> {

    List<RestaurantModel> restaurantModelList;
    Context context;

    public RestaurantAdapter(List<RestaurantModel> restaurantModelList, Context context) {
        this.restaurantModelList = restaurantModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public RestaurantAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantAdapter.ViewHolder holder, int position) {
        holder.restaurantTxt.setText(restaurantModelList.get(position).getRestaurantName());
        Glide.with(context).load(restaurantModelList.get(position).getLogoImage()).into(holder.logoImage);
        FoodAdapter foodAdapter;
        foodAdapter = new FoodAdapter(restaurantModelList.get(position).getFoodModelList(),context);
        holder.foodRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.foodRecycler.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return restaurantModelList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RecyclerView foodRecycler;
        TextView restaurantTxt;
        ImageView logoImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodRecycler = itemView.findViewById(R.id.foodRecycler);
            logoImage = itemView.findViewById(R.id.logoImage);
            restaurantTxt = itemView.findViewById(R.id.restaurantTxt);
        }
    }
}
