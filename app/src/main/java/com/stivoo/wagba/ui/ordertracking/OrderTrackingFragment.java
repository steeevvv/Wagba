package com.stivoo.wagba.ui.ordertracking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.OrderModel;

import java.util.Objects;


public class OrderTrackingFragment extends Fragment {

    TextView id;
    TextView placedTime;
    TextView cookingTime;
    TextView confirmedTime;
    TextView deliveryTime;
    TextView date;
    TextView gate;
    TextView period;
    ImageView confirmed_image;
    ImageView cooking_image;
    ImageView delivery_image;

    OrderModel order;

    public OrderTrackingFragment() {
    }

    public OrderTrackingFragment(OrderModel pos) {
        order = pos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_order_tracking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id = view.findViewById(R.id.ot_id_val);
        id.setText(order.getId());

        gate= view.findViewById(R.id.ot_gate_val);
        gate.setText(order.getGate());

        period = view.findViewById(R.id.ot_period_val);
        period.setText(order.getPeriod());

        date = view.findViewById(R.id.ot_date_val);
        date.setText(order.getOrderDate()+ ", " + order.getOrderTime());

        placedTime = view.findViewById(R.id.ot_inprocess3);
        placedTime.setText(order.getStatusProcess());

        cookingTime = view.findViewById(R.id.ot_inprocess26);
        cookingTime.setText(order.getStatusCooking());

        confirmedTime = view.findViewById(R.id.ot_inprocess22);
        confirmedTime.setText(order.getStatusConfirm());

        deliveryTime = view.findViewById(R.id.ot_inprocess29);
        deliveryTime.setText(order.getStatusDelivery());


        confirmed_image = view.findViewById(R.id.imageView10);
        if(!Objects.equals(order.getStatusConfirm(), "--:--")){
            confirmed_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_round_main));
        }

        cooking_image = view.findViewById(R.id.imageView11);
        if(!Objects.equals(order.getStatusCooking(), "--:--")){
            cooking_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_round_main));
        }

        delivery_image = view.findViewById(R.id.imageView12);
        if(!Objects.equals(order.getStatusDelivery(), "--:--")){
            delivery_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_round_main));
        }
    }
}