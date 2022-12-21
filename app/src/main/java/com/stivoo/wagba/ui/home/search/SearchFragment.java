package com.stivoo.wagba.ui.home.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.pojo.RestaurantModel;
import com.stivoo.wagba.ui.home.home.FeaturedRestaurantsRecyclerViewInterface;
import com.stivoo.wagba.ui.home.home.HomeFragmentViewModel;
import com.stivoo.wagba.ui.meals.MealFragment;
import com.stivoo.wagba.ui.restaurants.RestaurantFragment;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements FeaturedRestaurantsRecyclerViewInterface {

    ArrayList<RestaurantModel> restaurants;
    public SearchFragment() {

    }

    public SearchFragment(ArrayList<RestaurantModel> rest){
        restaurants = rest;
    }

    SearchSimulationAdapter adapter = new SearchSimulationAdapter(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SearchViewModel searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        LiveData<DataSnapshot> liveData = searchViewModel.getAllRestaurants();

        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    restaurants = new ArrayList<>();
                    for (DataSnapshot dataSnapshott: dataSnapshot.getChildren()){
                        RestaurantModel rest = dataSnapshott.getValue(RestaurantModel.class);
                            restaurants.add(rest);
                    }
                    adapter.setList(restaurants);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter.setList(restaurants);
        RecyclerView recycler = view.findViewById(R.id.s_recylcerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    @Override
    public void onViewBtnClick(RestaurantModel res) {
        FragmentManager fragm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new RestaurantFragment(res));
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}