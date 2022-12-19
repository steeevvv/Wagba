package com.stivoo.wagba.ui.previousorders;

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

import com.stivoo.wagba.ui.ordertracking.OrderTrackingFragment;
import com.stivoo.wagba.ui.ordertracking.OrderTrackingRecyclerViewInterface;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.OrderModel;

import java.util.ArrayList;

public class PreviousOrdersFragment extends Fragment implements OrderTrackingRecyclerViewInterface {
    ArrayList<OrderModel> ordersList;

    public PreviousOrdersFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ordersList = new ArrayList<>();
        ordersList.add(new OrderModel("Dec 12, 2022", "#1122334"));
        ordersList.add(new OrderModel("Noc 11, 2022", "#1121489"));
        ordersList.add(new OrderModel("Sep 02, 2022", "#1120112"));
        ordersList.add(new OrderModel("Jun 19, 2022", "#1118731"));
        ordersList.add(new OrderModel("May 29, 2022", "#1116908"));
        ordersList.add(new OrderModel("Feb 02, 2022", "#1114809"));
        ordersList.add(new OrderModel("Feb 01, 2022", "#1104799"));
        ordersList.add(new OrderModel("Jan 27, 2022", "#1103632"));

        return inflater.inflate(R.layout.fragment_previous_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PreviousOrdersAdapter adapter = new PreviousOrdersAdapter(this);
        adapter.setList(ordersList);
        RecyclerView recycler = view.findViewById(R.id.po_recyclerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onViewBtnClick(int pos) {
        FragmentManager fragm = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, new OrderTrackingFragment());
        fragmentTransaction.commit();
    }
}