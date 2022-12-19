package com.stivoo.wagba.ui.home.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.RestaurantModel;

import java.util.ArrayList;

public class FeaturedRestaurantsAdapter extends RecyclerView.Adapter<FeaturedRestaurantsAdapter.FeaturedRestaurantsViewHolder> {

    public FeaturedRestaurantsAdapter(FeaturedRestaurantsRecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
    }

    private final FeaturedRestaurantsRecyclerViewInterface recyclerViewInterface;
    private static ArrayList<RestaurantModel> restaurantsList = new ArrayList<>();

    @NonNull
    @Override
    public FeaturedRestaurantsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FeaturedRestaurantsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.featured_restaurant_card, parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FeaturedRestaurantsViewHolder holder, int position) {
//
//        holder.img2.setImageResource(restaurantsList.get(position).getCover());
        holder.rest_name.setText(restaurantsList.get(position).getName());
        holder.rest_duration.setText(restaurantsList.get(position).getDuration());
        holder.rest_fees.setText(restaurantsList.get(position).getDelivery_fees());
        Glide.with(holder.getImg1())
                .load(restaurantsList.get(position).getLogo())
                .into(holder.img1);

        Glide.with(holder.getImg2())
                .load(restaurantsList.get(position).getCover())
                .into(holder.img2);
    }

    @Override
    public int getItemCount() {
        return restaurantsList.size();
    }

    public void setList(ArrayList<RestaurantModel> restaurantsList) {
        this.restaurantsList = restaurantsList;
        notifyDataSetChanged();
    }

    public static class FeaturedRestaurantsViewHolder extends RecyclerView.ViewHolder {

        ImageView img1;
        TextView rest_name;
        TextView rest_fees;
        TextView rest_duration;
        ImageView img2;

        public ImageView getImg1() {
            return img1;
        }

        public ImageView getImg2(){
            return img2;
        }

        public FeaturedRestaurantsViewHolder(@NonNull View itemView, FeaturedRestaurantsRecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            img1 = itemView.findViewById(R.id.restaurant_logo);
            img2 = itemView.findViewById(R.id.rest_cover);
            rest_name = itemView.findViewById(R.id.tv_rest_name);
            rest_fees = itemView.findViewById(R.id.tv_delivery_fees);
            rest_duration = itemView.findViewById(R.id.tv_rest_duration);



            itemView.setOnClickListener(v -> {
                if (recyclerViewInterface != null){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onViewBtnClick(restaurantsList.get(pos));
                    }
                }
            });
        }
    }
}
