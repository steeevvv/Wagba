package com.stivoo.wagba.ui.meals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.ui.restaurants.RestaurantMealsAdapter;


public class MealFragment extends Fragment {
    MealModel meal;

    ImageView img;
    TextView title;
    TextView categories;
    TextView price;
    TextView description;

    Button add_to_cart;
    Button inc;
    Button dec;
    TextView amt;


    public MealFragment(MealModel meal) {
        this.meal = meal;
    }

    public MealFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        RestaurantMealsAdapter adapter = new RestaurantMealsAdapter(this);
//        adapter.setList(meals);
//        RecyclerView recycler = view.findViewById(R.id.rd_recyclerView);
//        recycler.setNestedScrollingEnabled(false);
//        recycler.setAdapter(adapter);
//        recycler.setLayoutManager(new GridLayoutManager(getContext(),2));


        img = view.findViewById(R.id.imageView3);
        Glide.with(getContext()).load(meal.getImg()).into(img);

        title = view.findViewById(R.id.m_name);
        title.setText(meal.getName());

        price = view.findViewById(R.id.m_price);
        price.setText(meal.getPrice());

        description = view.findViewById(R.id.m_categories2);
        description.setText(meal.getDescription());

        categories = view.findViewById(R.id.m_categories);
        //TODO
        String cats = String.join(" • ", "Shawerma", "Beef");
        categories.setText(cats);

        add_to_cart = view.findViewById(R.id.add_to_cart_btn);
        inc = view.findViewById(R.id.cbtn_inc);
        dec = view.findViewById(R.id.cbtn_dec);
        amt = view.findViewById(R.id.ctv_quantity);

        add_to_cart.setOnClickListener(v -> {

        });

        inc.setOnClickListener(v -> {
            amt.setText(String.valueOf(Integer.parseInt(amt.getText().toString()) + 1));
        });

        dec.setOnClickListener(v -> {
            if(Integer.parseInt(amt.getText().toString()) > 0) {
                amt.setText(String.valueOf(Integer.parseInt(amt.getText().toString()) - 1));
            }else{
                Toast.makeText(getContext(), "Can't Decrement than 0!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}