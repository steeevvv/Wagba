package com.stivoo.wagba.ui.home.search;

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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.RestaurantModel;
import com.stivoo.wagba.ui.home.home.FeaturedRestaurantsRecyclerViewInterface;
import com.stivoo.wagba.ui.restaurants.RestaurantFragment;

import java.util.ArrayList;

public class SearchFragment extends Fragment implements FeaturedRestaurantsRecyclerViewInterface {

    ArrayList<RestaurantModel> restaurants;
    SearchSimulationAdapter adapter = new SearchSimulationAdapter(this);

    EditText search_val;
    SearchViewModel searchViewModel;

    public SearchFragment() {
    }

    public SearchFragment(ArrayList<RestaurantModel> rest){
        restaurants = rest;
        adapter.setList(restaurants);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (restaurants == null) {
            searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
            LiveData<DataSnapshot> liveData = searchViewModel.getAllRestaurants();
            liveData.observe(this, new Observer<DataSnapshot>() {
                @Override
                public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                    if (dataSnapshot != null) {
                        restaurants = new ArrayList<>();
                        for (DataSnapshot dataSnapshott : dataSnapshot.getChildren()) {
                            RestaurantModel rest = dataSnapshott.getValue(RestaurantModel.class);
                            restaurants.add(rest);
                        }
                        adapter.setList(restaurants);
                    }
                }
            });
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = view.findViewById(R.id.s_recylcerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new GridLayoutManager(getContext(), 2));

        search_val = view.findViewById(R.id.sfsearch_txt);
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
                            adapter.setList(md);
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

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    for(int i = 0; i < fm.getBackStackEntryCount()-1; ++i) {
                        fm.popBackStack();
                    }
                    BottomNavigationView bottomNavigationView;
                    bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.navBar);
                    bottomNavigationView.setSelectedItemId(R.id.Home);
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
        search_val.setText("");
    }
}