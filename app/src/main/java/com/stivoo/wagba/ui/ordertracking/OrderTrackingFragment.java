package com.stivoo.wagba.ui.ordertracking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.CartItem;
import com.stivoo.wagba.pojo.Order;
import com.stivoo.wagba.pojo.OrderModel;
import com.stivoo.wagba.ui.orderconfirmation.OrderConfirmationViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

    OrderTrackingViewModel orderTrackingViewModel;
    Order order;
    String time;

    public OrderTrackingFragment() {
    }

    public OrderTrackingFragment(String timeStamp) {
        time = timeStamp;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderTrackingViewModel = new ViewModelProvider(this).get(OrderTrackingViewModel.class);
        LiveData<DataSnapshot> liveData = orderTrackingViewModel.getOrder(time);
        liveData.observe(this, dataSnapshott -> {
            order = new Order();
            if (dataSnapshott != null) {
                order.setId((String) dataSnapshott.child("id").getValue());
                order.setOrderDate((String) dataSnapshott.child("orderDate").getValue());
                order.setOrderTime((String) dataSnapshott.child("orderTime").getValue());
                order.setStatusProcess((String) dataSnapshott.child("statusProcess").getValue());
                order.setStatusConfirm((String) dataSnapshott.child("statusConfirm").getValue());
                order.setStatusCooking((String) dataSnapshott.child("statusCooking").getValue());
                order.setStatusDelivery((String) dataSnapshott.child("statusDelivery").getValue());
                order.setGate((String) dataSnapshott.child("gate").getValue());
                order.setPeriod((String) dataSnapshott.child("period").getValue());

                id.setText(order.getId());
                gate.setText(order.getGate());
                period.setText(order.getPeriod());
                date.setText(order.getOrderDate() + ", " + order.getOrderTime());
                placedTime.setText(order.getStatusProcess());
                cookingTime.setText(order.getStatusCooking());
                confirmedTime.setText(order.getStatusConfirm());
                deliveryTime.setText(order.getStatusDelivery());

                if (!Objects.equals(order.getStatusConfirm(), "--:--")) {
                    confirmed_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_round_main));
                }
                if (!Objects.equals(order.getStatusCooking(), "--:--")) {
                    cooking_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_round_main));
                }
                if (!Objects.equals(order.getStatusDelivery(), "--:--")) {
                    delivery_image.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.background_round_main));
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_order_tracking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id = view.findViewById(R.id.ot_id_val);

        gate = view.findViewById(R.id.ot_gate_val);

        period = view.findViewById(R.id.ot_period_val);

        date = view.findViewById(R.id.ot_date_val);

        placedTime = view.findViewById(R.id.ot_inprocess3);

        cookingTime = view.findViewById(R.id.ot_inprocess26);

        confirmedTime = view.findViewById(R.id.ot_inprocess22);

        deliveryTime = view.findViewById(R.id.ot_inprocess29);

        confirmed_image = view.findViewById(R.id.imageView10);

        cooking_image = view.findViewById(R.id.imageView11);

        delivery_image = view.findViewById(R.id.imageView12);
    }
}