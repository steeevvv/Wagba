package com.stivoo.wagba.ui.home.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.ui.home.RecyclerViewInterface;

import java.util.ArrayList;

public class RecommendedMealsAdapter extends RecyclerView.Adapter<RecommendedMealsAdapter.RecommendedMealViewHolder> {

    private final RecyclerViewInterface recyclerViewInterface;
    private static ArrayList<MealModel> mealsList = new ArrayList<>();

    public RecommendedMealsAdapter(RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public RecommendedMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendedMealViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recommended_meal_card, parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedMealViewHolder holder, int position) {
        Glide.with(holder.getImg()).load(mealsList.get(position).getImg()).into(holder.img);
//        holder.img.setImageResource();
        holder.price.setText(mealsList.get(position).getPrice());
        holder.name.setText(mealsList.get(position).getName());
        String description = mealsList.get(position).getDescription();
        if(description.length() < 70){
            holder.desc.setText(description);
        }else{
            holder.desc.setText(description.substring(0, 70)+"...");
        }
        holder.rest.setText(mealsList.get(position).getRestaurant_name());
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    public void setList(ArrayList<MealModel> mealsList) {
        this.mealsList = mealsList;
        notifyDataSetChanged();
    }

    public static class RecommendedMealViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView price;
        TextView name;
        TextView desc;
        TextView rest;

        public ImageView getImg() {
            return img;
        }

        public RecommendedMealViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            img = itemView.findViewById(R.id.rimg_meal);
            price = itemView.findViewById(R.id.rtv_meal_price_val);
            name = itemView.findViewById(R.id.rtv_meal_name);
            desc = itemView.findViewById(R.id.meal_desc);
            rest = itemView.findViewById(R.id.rtv_meal_rest);
            itemView.findViewById(R.id.button2).setOnClickListener(v -> {
                if (recyclerViewInterface != null){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        recyclerViewInterface.onViewBtnClick(mealsList.get(pos));
                    }
                }
            });
        }
    }
}
