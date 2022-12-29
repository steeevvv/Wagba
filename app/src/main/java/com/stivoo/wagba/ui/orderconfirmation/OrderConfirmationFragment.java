package com.stivoo.wagba.ui.orderconfirmation;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.ui.orderconfirmation.OrderConfirmationAdapter;

import java.util.ArrayList;

public class OrderConfirmationFragment extends Fragment {

    ArrayList<MealModel> orderSummary;

    public OrderConfirmationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //TODO
        orderSummary = new ArrayList<>();
        orderSummary.add(new MealModel("Big Mac", "", "EGP 69.99", "The McDonald's Big Mac® is a 100% beef burger with a taste like no other.  It's topped off with pickles, crisp shredded lettuce, finely chopped onion, and a slice of American cheese.", 5, "McDonald's"));
        orderSummary.add(new MealModel("Chicken Ranch Pizza", "", "EGP 129.99", "A delicious, tempting Papa John's Pizza classic with perfectly grilled, succulent chicken, fresh, tender mushrooms, tomatoes and spicy jalapeno peppers with a tangy ranch sauce!",9, "KFC"));
        orderSummary.add(new MealModel("Big Tasty", "", "EGP 99.99", "A big and tasty beef patty smothered in our one of a kind Big Tasty Sauce and 3 slices of emmental cheese, dressed with 2 slices of tomato, a handful of crispy shredded lettuce and slivered onions.",4,"PAPA"));

        return inflater.inflate(R.layout.fragment_order_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        OrderConfirmationAdapter adapter5 = new OrderConfirmationAdapter();
        adapter5.setList(orderSummary);
        RecyclerView recycler5 = view.findViewById(R.id.o_summary_recyclerView);
        recycler5.setNestedScrollingEnabled(false);
        recycler5.setAdapter(adapter5);
        recycler5.setLayoutManager(new LinearLayoutManager(getContext()));


    }
}