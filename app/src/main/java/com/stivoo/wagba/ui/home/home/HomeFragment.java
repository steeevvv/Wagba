package com.stivoo.wagba.ui.home.home;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stivoo.wagba.R;
import com.stivoo.wagba.ui.home.RecyclerViewInterface;
import com.stivoo.wagba.ui.meals.MealFragment;
import com.stivoo.wagba.ui.restaurants.RestaurantFragment;
import com.stivoo.wagba.ui.home.search.SearchFragment;
import com.stivoo.wagba.pojo.CategoryModel;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.pojo.RestaurantModel;

import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment implements FeaturedRestaurantsRecyclerViewInterface, RecyclerViewInterface {

    ArrayList<CategoryModel> categories;
    ArrayList<RestaurantModel> restaurants;
    ArrayList<MealModel>meals;
    TextView view_all_restaurant;
    EditText search_val;

    public HomeFragment() {

    }

    FeaturedRestaurantsAdapter adapter1 = new FeaturedRestaurantsAdapter(this);
    RecommendedMealsAdapter adapter2 = new RecommendedMealsAdapter(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HomeFragmentViewModel homeViewModel = new ViewModelProvider(this).get(HomeFragmentViewModel.class);
        LiveData<DataSnapshot> liveData = homeViewModel.getDataSnapshotLiveData();
        LiveData<DataSnapshot> liveMeals = homeViewModel.getMealsLiveData();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    restaurants = new ArrayList<>();
                    for (DataSnapshot dataSnapshott: dataSnapshot.getChildren()){
                        RestaurantModel rest = dataSnapshott.getValue(RestaurantModel.class);
                        if (rest.getFeatured()) {
                            restaurants.add(rest);
                        }
                    }
                    adapter1.setList(restaurants);
                }
            }
        });

        liveMeals.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    meals = new ArrayList<>();
                    for (DataSnapshot dataSnapshott: dataSnapshot.getChildren()){
                        MealModel meal = dataSnapshott.getValue(MealModel.class);
                        meals.add(meal);
                    }
                    adapter2.setList(meals);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        categories = new ArrayList<>();
        categories.add(new CategoryModel(R.drawable.category_burger, "Burgers"));
        categories.add(new CategoryModel(R.drawable.category_pizza, "Pizzas"));
        categories.add(new CategoryModel(R.drawable.category_shawerma, "Shawermas"));
        categories.add(new CategoryModel(R.drawable.category_desserts, "Desserts"));
        categories.add(new CategoryModel(R.drawable.category_burger, "Burgers"));
        categories.add(new CategoryModel(R.drawable.category_pizza, "Pizzas"));
        categories.add(new CategoryModel(R.drawable.category_shawerma, "Shawermas"));
        categories.add(new CategoryModel(R.drawable.category_desserts, "Desserts"));

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CategoryAdapter adapter = new CategoryAdapter();
        adapter.setList(categories);
        RecyclerView recycler = view.findViewById(R.id.mCategoryRecyclerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));


        RecyclerView recycler1 = view.findViewById(R.id.m2RecyclerView);
        recycler1.setNestedScrollingEnabled(false);
        recycler1.setAdapter(adapter1);
        recycler1.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        RecyclerView recycler2 = view.findViewById(R.id.m3RecyclerView);
        recycler2.setAdapter(adapter2);
        recycler2.setLayoutManager(new LinearLayoutManager(getContext()));

        view_all_restaurant = view.findViewById(R.id.tv_2);
        view_all_restaurant.setOnClickListener(v -> {
            FragmentManager fragm = getParentFragmentManager();
            fragm.beginTransaction().replace(R.id.frameLayout, new SearchFragment(restaurants)).addToBackStack(null).commit();
        });

        search_val = view.findViewById(R.id.search_txt);
        search_val.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(search_val.getWindowToken(), 0);


                    ArrayList<RestaurantModel> md = new ArrayList<>();
                    Query nm = FirebaseDatabase.getInstance().getReference().child("Restaurants")
                            .orderByChild("name").startAt(search_val.getText().toString()).endAt(search_val.getText().toString() +"~");
                    nm.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                RestaurantModel rest = postSnapshot.getValue(RestaurantModel.class);
                                md.add(rest);
                            }
                            FragmentManager fragm = getParentFragmentManager();
                            FragmentTransaction fragmentTransaction = fragm.beginTransaction();
                            fragmentTransaction.replace(R.id.frameLayout, new SearchFragment(md));
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onViewBtnClick(RestaurantModel res) {
        FragmentManager fragm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new RestaurantFragment(res));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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