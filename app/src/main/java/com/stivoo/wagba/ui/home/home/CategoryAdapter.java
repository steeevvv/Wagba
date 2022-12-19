package com.stivoo.wagba.ui.home.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<CategoryModel> categoriesList = new ArrayList<>();

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.category_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.tv_name.setText(categoriesList.get(position).getName());
        holder.img.setImageResource(categoriesList.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public void setList(ArrayList<CategoryModel> categoriesList) {
        this.categoriesList = categoriesList;
        notifyDataSetChanged();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        ImageView img;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_category);
            img = itemView.findViewById(R.id.restaurant_logo);

        }
    }
}
