package com.stivoo.wagba.ui.home.search;

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

public class SearchSimulationAdapter extends RecyclerView.Adapter<SearchSimulationAdapter.SearchSimulationViewHolder> {

    private ArrayList<RestaurantModel> searchList = new ArrayList<>();

    @NonNull
    @Override
    public SearchSimulationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchSimulationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_card, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchSimulationViewHolder holder, int position) {
        holder.txt.setText(searchList.get(position).getName());
        Glide.with(holder.getImg()).load(searchList.get(position).getLogo()).into(holder.img);
//        holder.img.setImageResource(searchList.get(position).getLogo());

    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public void setList(ArrayList<RestaurantModel> searchList) {
        this.searchList = searchList;
        notifyDataSetChanged();
    }

    public static class SearchSimulationViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt;

        public ImageView getImg() {
            return img;
        }

        public SearchSimulationViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.sr_img);
            txt = itemView.findViewById(R.id.sr_name);
        }
    }
}
