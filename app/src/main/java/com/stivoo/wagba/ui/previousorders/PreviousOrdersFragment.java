package com.stivoo.wagba.ui.previousorders;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.ui.ordertracking.OrderTrackingFragment;
import com.stivoo.wagba.ui.ordertracking.OrderTrackingRecyclerViewInterface;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.OrderModel;

import java.util.ArrayList;

public class PreviousOrdersFragment extends Fragment implements OrderTrackingRecyclerViewInterface {

    PreviousOrdersAdapter adapter = new PreviousOrdersAdapter(this);
    ArrayList<OrderModel> ordersList;
    PreviousOrdersViewModel previousOrdersViewModel;

    public PreviousOrdersFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        previousOrdersViewModel = new ViewModelProvider(this).get(PreviousOrdersViewModel.class);
        LiveData<DataSnapshot> liveData = previousOrdersViewModel.getOrders();
        liveData.observe(this, new Observer<DataSnapshot>() {
            @Override
            public void onChanged(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null){
                    ordersList = new ArrayList<>();
                    for (DataSnapshot dataSnapshott : dataSnapshot.getChildren()) {
                        OrderModel item = new OrderModel();
                        item.setId((String) dataSnapshott.child("id").getValue());
                        item.setOrderDate((String) dataSnapshott.child("orderDate").getValue());
//                        item.setGate((String) dataSnapshott.child("gate").getValue());
//                        item.setMeals((ArrayList<CartItem>) dataSnapshott.child("meals").getValue());
//                        item.setOrderInfo((String) dataSnapshott.child("orderInfo").getValue());
//                        item.setPeriod((String) dataSnapshott.child("period").getValue());
//                        item.setOrderTime((String) dataSnapshott.child("orderTime").getValue());
                        ordersList.add(item);
                    }
                    adapter.setList(ordersList);
                }

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ordersList = new ArrayList<>();

        return inflater.inflate(R.layout.fragment_previous_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycler = view.findViewById(R.id.po_recyclerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewBtnClick(OrderModel pos) {
        FragmentManager fragm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new OrderTrackingFragment(pos));
        fragmentTransaction.commit();
    }
}