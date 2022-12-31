package com.stivoo.wagba.ui.home.cart;

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

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.pojo.CartItem;
import com.stivoo.wagba.ui.orderconfirmation.OrderConfirmationFragment;
import com.stivoo.wagba.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {
    CartAdapter adapter = new CartAdapter();

    TextView subtotal;
    TextView delivery;
    TextView total;
    Button btn;
    CartViewModel cartViewModel;
    ArrayList<CartItem> cartItems;
    Float subtotal_value =0.00f;
    Float delivery_value =0.00f;
    DecimalFormat df = new DecimalFormat("#.00");

    public CartFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        LiveData<DataSnapshot> liveData = cartViewModel.getCart();
        liveData.observe(this, new Observer<DataSnapshot>() {

            @Override
            public void onChanged(@Nullable DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    cartItems = new ArrayList<>();
                    subtotal_value = 0.00f;
                    delivery_value = 0.00f;
                    for (DataSnapshot dataSnapshott : dataSnapshot.getChildren()) {
                        CartItem item = dataSnapshott.getValue(CartItem.class);
                        cartItems.add(item);
                        subtotal_value+=item.getQty()*Float.parseFloat(item.getPrice().substring(4));
                        if (item.getDelivery_fee() > delivery_value){
                            delivery_value = item.getDelivery_fee();
                        }
                    }
                    adapter.setList(cartItems);
                    subtotal.setText("EGP "+ df.format(subtotal_value));
                    delivery.setText("EGP "+ df.format(delivery_value));
                    total.setText("EGP "+ df.format(delivery_value+subtotal_value));


                    if (cartItems.size() == 0){
                        FragmentManager fragm = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragm.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, new EmptyCartFragment());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        subtotal = view.findViewById(R.id.ctv_subtotal_val);
        total = view.findViewById(R.id.ctv_total_val);
        delivery = view.findViewById(R.id.ctv_deliveryfee_val);

        RecyclerView recycler = view.findViewById(R.id.cRecyclerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));


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
}