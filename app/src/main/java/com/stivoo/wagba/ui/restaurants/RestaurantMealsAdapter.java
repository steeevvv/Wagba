package com.stivoo.wagba.ui.restaurants;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.pojo.RestaurantModel;
import com.stivoo.wagba.ui.home.RecyclerViewInterface;
import com.stivoo.wagba.ui.home.home.FeaturedRestaurantsRecyclerViewInterface;

import java.util.ArrayList;

public class RestaurantMealsAdapter extends RecyclerView.Adapter<RestaurantMealsAdapter.RestaurantViewHolder> {

    public RestaurantMealsAdapter(RecyclerViewInterface recyclerViewInterface) {
        this.recyclerViewInterface = recyclerViewInterface;
    }

    private final RecyclerViewInterface recyclerViewInterface;

    private static ArrayList<MealModel> mealsList = new ArrayList<>();

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_meal_card, parent, false), recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.price.setText(mealsList.get(position).getPrice());

        Glide.with(holder.getImg()).load(mealsList.get(position).getImg()).into(holder.img);
//        holder.img.setImageResource(mealsList.get(position).getImg());
        holder.name.setText(mealsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mealsList.size();
    }

    public void setList(ArrayList<MealModel> mealsList) {
        this.mealsList = mealsList;
        notifyDataSetChanged();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView price;
        TextView name;
        Button btn;

        public ImageView getImg() {
            return img;
        }

        public RestaurantViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            img = itemView.findViewById(R.id.imageView6);
            price= itemView.findViewById(R.id.rd_categories6);
            name= itemView.findViewById(R.id.rd_categories4);
//            btn = itemView.findViewById(R.id.button4);



            itemView.setOnClickListener(v->
            {
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
