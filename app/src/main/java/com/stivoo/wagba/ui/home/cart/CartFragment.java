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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.pojo.CartItem;
import com.stivoo.wagba.pojo.RestaurantModel;
import com.stivoo.wagba.ui.home.search.SearchSimulationAdapter;
import com.stivoo.wagba.ui.home.search.SearchViewModel;
import com.stivoo.wagba.ui.orderconfirmation.OrderConfirmationFragment;
import com.stivoo.wagba.R;
import com.stivoo.wagba.pojo.MealModel;

import java.util.ArrayList;


public class CartFragment extends Fragment {

    ArrayList<MealModel> cartMeals;
    CartAdapter adapter = new CartAdapter();

    Button btn;
    CartViewModel cartViewModel;
    ArrayList<CartItem> cartItems;

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
                    for (DataSnapshot dataSnapshott : dataSnapshot.getChildren()) {
                        CartItem item = dataSnapshott.getValue(CartItem.class);
                        cartItems.add(item);
                    }
                    adapter.setList(cartItems);
                    if (cartItems.size() == 0){
                        FragmentManager fragm = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragm.beginTransaction();
                        fragmentTransaction.replace(R.id.frameLayout, new EmptyCartFragment());
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
//                FragmentManager fragm = getParentFragmentManager();
//                FragmentTransaction fragmentTransaction = fragm.beginTransaction();
//                fragmentTransaction.replace(R.id.frameLayout, new OrderConfirmationFragment());
//                fragmentTransaction.commit();
            }
        });

        RecyclerView recycler = view.findViewById(R.id.cRecyclerView);
        recycler.setNestedScrollingEnabled(false);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}