package com.stivoo.wagba.ui.meals;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.pojo.RestaurantModel;
import com.stivoo.wagba.ui.home.search.SearchViewModel;
import com.stivoo.wagba.ui.restaurants.RestaurantMealsAdapter;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


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
    MealViewModel mealViewModel;
    EditText additional_info;


    public MealFragment(MealModel meal) {
        this.meal = meal;
    }

    public MealFragment(){}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
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
        additional_info = view.findViewById(R.id.oet_additional_info3);


        if (meal.getQty() == 0){
            add_to_cart.setEnabled(false);
        }


        add_to_cart.setOnClickListener(v -> {

            if(Integer.parseInt(amt.getText().toString()) == 0){
                Toast.makeText(getContext(), "Please Add Quantity before adding to Cart!", Toast.LENGTH_SHORT).show();
            }else {
                mealViewModel.writeNewCart(meal, Integer.parseInt(amt.getText().toString()), additional_info.getText().toString());
                Toast.makeText(getContext(), "Added to Cart!", Toast.LENGTH_SHORT).show();
            }
        });

        inc.setOnClickListener(v -> {
            if(Integer.parseInt(amt.getText().toString())+1 <= meal.getQty()){
                amt.setText(String.valueOf(Integer.parseInt(amt.getText().toString()) + 1));
                DecimalFormat df = new DecimalFormat("#.00");
                Float price_val = Float.parseFloat(meal.getPrice().substring(4)) * Integer.parseInt(amt.getText().toString());
                price.setText("EGP "+df.format(price_val));
            }else{
                Toast.makeText(getContext(), "Can't Increment than maximum quantity!", Toast.LENGTH_SHORT).show();
            }
        });

        dec.setOnClickListener(v -> {
            if(Integer.parseInt(amt.getText().toString()) > 0) {
                amt.setText(String.valueOf(Integer.parseInt(amt.getText().toString()) - 1));
                if(Integer.parseInt(amt.getText().toString()) == 0){
                    price.setText(meal.getPrice());
                } else {
                    DecimalFormat df = new DecimalFormat("#.00");
                    Float price_val = Float.parseFloat(meal.getPrice().substring(4)) * Integer.parseInt(amt.getText().toString());
                    price.setText("EGP " + df.format(price_val));
                }
            }else{
                Toast.makeText(getContext(), "Can't Decrement than 0!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}