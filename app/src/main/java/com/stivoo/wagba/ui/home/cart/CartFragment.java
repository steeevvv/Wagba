package com.stivoo.wagba.ui.home.cart;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.stivoo.wagba.ui.orderconfirmation.OrderConfirmationFragment;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    ArrayList<MealModel> cartMeals;
    Button btn;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //TODO
//        cartMeals = new ArrayList<>();
//        cartMeals.add(new MealModel("Big Mac", R.drawable.meal_bigmac, "EGP 69.99", "The McDonald's Big Mac® is a 100% beef burger with a taste like no other.  It's topped off with pickles, crisp shredded lettuce, finely chopped onion, and a slice of American cheese."));
//        cartMeals.add(new MealModel("Chicken Ranch Pizza", R.drawable.meal_chickenranch, "EGP 129.99", "A delicious, tempting Papa John's Pizza classic with perfectly grilled, succulent chicken, fresh, tender mushrooms, tomatoes and spicy jalapeno peppers with a tangy ranch sauce!"));
//        cartMeals.add(new MealModel("Big Tasty", R.drawable.meal_bigtasty, "EGP 99.99", "A big and tasty beef patty smothered in our one of a kind Big Tasty Sauce and 3 slices of emmental cheese, dressed with 2 slices of tomato, a handful of crispy shredded lettuce and slivered onions."));

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn = view.findViewById(R.id.cbtn_order);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragm = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragm.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout, new OrderConfirmationFragment());
                fragmentTransaction.commit();

            }
        });

        CartAdapter adapter = new CartAdapter();
        adapter.setList(cartMeals);
        RecyclerView recycler = view.findViewById(R.id.cRecyclerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));


    }
}