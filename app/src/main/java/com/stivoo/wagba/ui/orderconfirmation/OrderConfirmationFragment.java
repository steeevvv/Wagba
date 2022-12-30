package com.stivoo.wagba.ui.orderconfirmation;

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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.CartItem;
import com.stivoo.wagba.pojo.MealModel;
import com.stivoo.wagba.ui.home.cart.CartViewModel;
import com.stivoo.wagba.ui.home.cart.EmptyCartFragment;
import com.stivoo.wagba.ui.orderconfirmation.OrderConfirmationAdapter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderConfirmationFragment extends Fragment {

    OrderConfirmationAdapter adapter = new OrderConfirmationAdapter();
    ArrayList<CartItem> orderItems;
    OrderConfirmationViewModel orderConfirmationViewModel;
    Float subtotal_value =0.00f;
    Float delivery_value =0.00f;
    DecimalFormat df = new DecimalFormat("#.00");

    TextView subtotal;
    TextView delivery;
    TextView total;
    Button confirm;
    RadioButton gate3, gate4;
    RadioButton am, pm;


    public OrderConfirmationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        orderConfirmationViewModel = new ViewModelProvider(this).get(OrderConfirmationViewModel.class);
        LiveData<DataSnapshot> liveData = orderConfirmationViewModel.getCart();
        liveData.observe(this, new Observer<DataSnapshot>() {

            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    orderItems = new ArrayList<>();
                    subtotal_value = 0.00f;
                    delivery_value = 0.00f;
                    for (DataSnapshot dataSnapshott : dataSnapshot.getChildren()) {
                        CartItem item = dataSnapshott.getValue(CartItem.class);
                        orderItems.add(item);
                        subtotal_value+=item.getQty()*Float.parseFloat(item.getPrice().substring(4));
                        if (item.getDelivery_fee() > delivery_value){
                            delivery_value = item.getDelivery_fee();
                        }
                    }
                    adapter.setList(orderItems);
                    subtotal.setText("EGP "+ df.format(subtotal_value));
                    delivery.setText("EGP "+ df.format(delivery_value));
                    total.setText("EGP "+ df.format(delivery_value+subtotal_value));
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_order_confirmation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subtotal = view.findViewById(R.id.o_subtotal_val);
        delivery = view.findViewById(R.id.o_delivery_val);
        total = view.findViewById(R.id.o_total_val);
        confirm = view.findViewById(R.id.o_confirm_btn);
        gate3 = view.findViewById(R.id.radio_gate3);
        gate4 = view.findViewById(R.id.radio_gate4);
        am = view.findViewById(R.id.radio_time_am);
        pm = view.findViewById(R.id.radio_time_pm);

        RecyclerView recycler5 = view.findViewById(R.id.o_summary_recyclerView);
        recycler5.setNestedScrollingEnabled(false);
        recycler5.setAdapter(adapter);
        recycler5.setLayoutManager(new LinearLayoutManager(getContext()));

        confirm.setOnClickListener(v -> {
            String gate = "";
            String time = "";
            if (gate3.isChecked()) {
                    gate = "Gate 3";
            } else if (gate4.isChecked()) {
                gate = "Gate 4";
            } else{
                Toast.makeText(getContext(), "Please Choose a gate!", Toast.LENGTH_SHORT).show();
            }

            long currentTime = Calendar.getInstance().getTime().getHours();
            Log.d("TTAAGG",String.valueOf(currentTime));

            if (am.isChecked()) {
                if (12 - currentTime >= 2) {
                    if (Calendar.getInstance().getTime().getMinutes() > 0) {
                        time = "am";
                    } else {
                        Toast.makeText(getContext(), "INVALID TIME!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "INVALID TIME!!", Toast.LENGTH_SHORT).show();
                }
            } else if (pm.isChecked()) {
                if (15 - currentTime >= 2) {
                    if (Calendar.getInstance().getTime().getMinutes() > 0) {
                        time = "pm";
                    } else {
                        Toast.makeText(getContext(), "INVALID TIME!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "INVALID TIME!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}