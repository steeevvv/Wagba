package com.stivoo.wagba.ui.orderconfirmation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;

import java.util.ArrayList;

public class OrderConfirmationAdapter extends RecyclerView.Adapter<OrderConfirmationAdapter.OrderConfirmationViewHolder> {

    private ArrayList<MealModel> orderList = new ArrayList<>();

    @NonNull
    @Override
    public OrderConfirmationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderConfirmationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_confirmation_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderConfirmationViewHolder holder, int position) {
        holder.order_name.setText(orderList.get(position).getName());
        holder.order_price.setText(orderList.get(position).getPrice().substring(4));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void setList(ArrayList<MealModel> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    public static class OrderConfirmationViewHolder extends RecyclerView.ViewHolder {
        TextView order_name;
        TextView order_price;
        public OrderConfirmationViewHolder(@NonNull View itemView) {
            super(itemView);

            order_name = itemView.findViewById(R.id.tv_meal_namee);
            order_price = itemView.findViewById(R.id.tv_meal_pricee2);
        }
    }
}
