package com.stivoo.wagba.ui.home.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.RestaurantModel;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

//    ArrayList<MealModel> searchMeals;
    ArrayList<RestaurantModel> restaurants;
    public SearchFragment() {

    }

    public SearchFragment(ArrayList<RestaurantModel> rest){
        restaurants = rest;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

//        searchMeals = new ArrayList<>();
//        searchMeals.add(new MealModel("Big Mac", R.drawable.meal_bigmac, "EGP 69.99", "The McDonald's Big Mac® is a 100% beef burger with a taste like no other.  It's topped off with pickles, crisp shredded lettuce, finely chopped onion, and a slice of American cheese."));
//        searchMeals.add(new MealModel("Chicken Ranch Pizza", R.drawable.meal_chickenranch, "EGP 129.99", "A delicious, tempting Papa John's Pizza classic with perfectly grilled, succulent chicken, fresh, tender mushrooms, tomatoes and spicy jalapeno peppers with a tangy ranch sauce!"));
//        searchMeals.add(new MealModel("Big Tasty", R.drawable.meal_bigtasty, "EGP 99.99", "A big and tasty beef patty smothered in our one of a kind Big Tasty Sauce and 3 slices of emmental cheese, dressed with 2 slices of tomato, a handful of crispy shredded lettuce and slivered onions."));
//        searchMeals.add(new MealModel("Big Mac", R.drawable.meal_bigmac, "EGP 69.99", "The McDonald's Big Mac® is a 100% beef burger with a taste like no other.  It's topped off with pickles, crisp shredded lettuce, finely chopped onion, and a slice of American cheese."));
//        searchMeals.add(new MealModel("Chicken Ranch Pizza", R.drawable.meal_chickenranch, "EGP 129.99", "A delicious, tempting Papa John's Pizza classic with perfectly grilled, succulent chicken, fresh, tender mushrooms, tomatoes and spicy jalapeno peppers with a tangy ranch sauce!"));
//        searchMeals.add(new MealModel("Big Tasty", R.drawable.meal_bigtasty, "EGP 99.99", "A big and tasty beef patty smothered in our one of a kind Big Tasty Sauce and 3 slices of emmental cheese, dressed with 2 slices of tomato, a handful of crispy shredded lettuce and slivered onions."));
//        searchMeals.add(new MealModel("Big Mac", R.drawable.meal_bigmac, "EGP 69.99", "The McDonald's Big Mac® is a 100% beef burger with a taste like no other.  It's topped off with pickles, crisp shredded lettuce, finely chopped onion, and a slice of American cheese."));
//        searchMeals.add(new MealModel("Chicken Ranch Pizza", R.drawable.meal_chickenranch, "EGP 129.99", "A delicious, tempting Papa John's Pizza classic with perfectly grilled, succulent chicken, fresh, tender mushrooms, tomatoes and spicy jalapeno peppers with a tangy ranch sauce!"));
//        searchMeals.add(new MealModel("Big Tasty", R.drawable.meal_bigtasty, "EGP 99.99", "A big and tasty beef patty smothered in our one of a kind Big Tasty Sauce and 3 slices of emmental cheese, dressed with 2 slices of tomato, a handful of crispy shredded lettuce and slivered onions."));

//        restaurants = new ArrayList<>();
//        restaurants.add(new RestaurantModel(R.drawable.logo_papajohns,R.drawable.cover_papa, "Papa John's", "EGP 20.0", "45 - 60 mins"));
//        restaurants.add(new RestaurantModel(R.drawable.logog_heartattack, R.drawable.cover_heart,"Heart Attack", "EGP 5.0", "30 - 45 mins"));
//        restaurants.add(new RestaurantModel(R.drawable.logo_kfc,R.drawable.cover_kfc, "KFC", "Free Delivery", "10 - 15 mins"));
//        restaurants.add(new RestaurantModel(R.drawable.logo_mc,R.drawable.cover_mc, "McDonald's", "EGP 10.0", "15 - 20 mins"));
//        restaurants.add(new RestaurantModel(R.drawable.logo_papajohns,R.drawable.cover_papa, "Papa John's", "EGP 20.0", "45 - 60 mins"));
//        restaurants.add(new RestaurantModel(R.drawable.logog_heartattack, R.drawable.cover_heart,"Heart Attack", "EGP 5.0", "30 - 45 mins"));
//        restaurants.add(new RestaurantModel(R.drawable.logo_kfc,R.drawable.cover_kfc, "KFC", "Free Delivery", "10 - 15 mins"));
//        restaurants.add(new RestaurantModel(R.drawable.logo_mc,R.drawable.cover_mc, "McDonald's", "EGP 10.0", "15 - 20 mins"));

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SearchSimulationAdapter adapter = new SearchSimulationAdapter();
        adapter.setList(restaurants);
        RecyclerView recycler = view.findViewById(R.id.s_recylcerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

    }
}