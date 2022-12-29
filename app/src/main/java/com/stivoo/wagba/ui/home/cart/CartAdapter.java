package com.stivoo.wagba.ui.home.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.CartItem;
import com.stivoo.wagba.pojo.MealModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartMealsList = new ArrayList<>();

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_element_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Glide.with(holder.getImg()).load(cartMealsList.get(position).getImg()).into(holder.img);
        DecimalFormat df = new DecimalFormat("#.00");
        Float price = Float.parseFloat(cartMealsList.get(position).getPrice().substring(4)) * cartMealsList.get(position).getQty();
        holder.price.setText(String.valueOf(df.format(price)));
        holder.name.setText(cartMealsList.get(position).getMeal_name());
        holder.qty.setText(String.valueOf(cartMealsList.get(position).getQty()));
    }

    @Override
    public int getItemCount() {
        return cartMealsList.size();
    }

    public void setList(ArrayList<CartItem> cartMealsList) {
        this.cartMealsList = cartMealsList;
        notifyDataSetChanged();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView price;
        TextView name;
        TextView qty;

        public ImageView getImg() {
            return img;
        }

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.cimg_meal);
            price = itemView.findViewById(R.id.ctv_meal_price_val);
            name = itemView.findViewById(R.id.ctv_meal_name);
            qty = itemView.findViewById(R.id.ctv_quantity);

        }
    }
}
