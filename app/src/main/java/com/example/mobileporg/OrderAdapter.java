package com.example.mobileporg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{

    List<OrderModel> orderModelList;
    Context context;
    DatabaseReference mRef;

    public OrderAdapter(List<OrderModel> orderModelList, Context context) {
        this.orderModelList = orderModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_item,parent,false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, int position) {
        holder.gateTxt.setText(orderModelList.get(position).getGateTxt());
        holder.timeTxt.setText(orderModelList.get(position).getTimeTxt());
        holder.statusTxt.setText(orderModelList.get(position).getStatusTxt());
        holder.orderPriceTxt.setText(orderModelList.get(position).getOrderPriceTxt());

        ItemsAdapter itemsAdapter;
        itemsAdapter = new ItemsAdapter(orderModelList.get(position).getItemsModelArrayList(),context);
        holder.itemsRecycler.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.itemsRecycler.setAdapter(itemsAdapter);
        itemsAdapter.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return orderModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView timeTxt;
        TextView gateTxt;
        RecyclerView itemsRecycler;
        TextView orderPriceTxt;
        TextView statusTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timeTxt = itemView.findViewById(R.id.timeTxt);
            gateTxt = itemView.findViewById(R.id.gateTxt);
            itemsRecycler = itemView.findViewById(R.id.itemsRecycler);
            orderPriceTxt =  itemView.findViewById(R.id.orderPriceTxt);
            statusTxt = itemView.findViewById(R.id.statusTxt);

        }
    }
}
