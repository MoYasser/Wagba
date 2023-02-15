package com.example.mobileporg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class ItemsAdapter extends  RecyclerView.Adapter<ItemsAdapter.ViewHolder>{

    List<String> itemsModelList;
    Context context;
    DatabaseReference mRef;

    public ItemsAdapter(List<String> itemsModelList, Context context) {
        this.itemsModelList = itemsModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.text_item,parent,false);
        return new ItemsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {

        holder.itemTxt.setText(itemsModelList.get(position));

    }

    @Override
    public int getItemCount() {
        return itemsModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTxt = itemView.findViewById(R.id.itemTxt);
        }
    }
}
