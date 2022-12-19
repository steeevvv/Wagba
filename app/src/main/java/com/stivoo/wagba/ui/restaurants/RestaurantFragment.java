package com.stivoo.wagba.ui.restaurants;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stivoo.wagba.pojo.RestaurantModel;
import com.stivoo.wagba.ui.home.RecyclerViewInterface;
import com.stivoo.wagba.ui.meals.MealFragment;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.ui.home.home.FeaturedRestaurantsRecyclerViewInterface;

import java.util.ArrayList;
import java.util.HashMap;

public class RestaurantFragment extends Fragment implements RecyclerViewInterface {

    ArrayList<MealModel>  meals;
    RestaurantModel restaurant;

    ImageView cover;
    TextView name;
    TextView delivery_price;
    ImageView logo;
    TextView categories;

    public RestaurantFragment(RestaurantModel res) {
        restaurant = res;
    }

    public RestaurantFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        meals = new ArrayList<>();
        meals = restaurant.getMeals();
        return inflater.inflate(R.layout.fragment_restaurant, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RestaurantMealsAdapter adapter = new RestaurantMealsAdapter(this);
        adapter.setList(meals);
        RecyclerView recycler = view.findViewById(R.id.rd_recyclerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(getContext(),2));

        cover = view.findViewById(R.id.rd_background);
        Glide.with(getContext()).load(restaurant.getCover()).into(cover);

        name = view.findViewById(R.id.rd_name);
        name.setText(restaurant.getName());

        delivery_price = view.findViewById(R.id.rd_delivery_price);
        delivery_price.setText(restaurant.getDelivery_fees());

        logo = view.findViewById(R.id.rd_logo);
        Glide.with(getContext()).load(restaurant.getLogo()).into(logo);

        categories = view.findViewById(R.id.rd_categories);
        String cats = String.join(" • ", restaurant.getCategories());
        categories.setText(cats);
    }

    @Override
    public void onViewBtnClick(Object obj) {
        FragmentManager fragm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new MealFragment((MealModel) obj));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}