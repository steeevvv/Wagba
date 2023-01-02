package com.stivoo.wagba.ui.orderconfirmation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stivoo.wagba.FirebaseQueryLiveData;
import com.stivoo.wagba.pojo.CartItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class OrderConfirmationViewModel extends ViewModel {
    private static final DatabaseReference CURRENT_CART =
            FirebaseDatabase.getInstance().getReference("/CurrentCart/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(CURRENT_CART);

    @NonNull
    public LiveData<DataSnapshot> getCart() {
        return liveData;
    }

    private static final DatabaseReference CART_REF =
            FirebaseDatabase.getInstance().getReference("/ConfirmedOrders");

    public void writeNewOrder(ArrayList<CartItem> items, String info, String period, String gate) {
        Date idd = Calendar.getInstance().getTime();
        DatabaseReference ref = CART_REF.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(idd.toString());
        DatabaseReference meals = ref.child("meals");
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    for(CartItem item:items) {
                        meals.child(item.getMeal_name()).setValue(item);
                    }
                    ref.child("orderInfo").setValue(info);
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat dateOnly = new SimpleDateFormat("MMM dd, yyyy");
                    ref.child("orderDate").setValue(dateOnly.format(cal.getTime()));
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    ref.child("orderTime").setValue(sdf.format(date));
                    ref.child("period").setValue(period);
                    ref.child("gate").setValue(gate);
                    ref.child("id").setValue("#"+new Date().getTime()/1000);
                    ref.child("statusProcess").setValue(String.format("%02d", idd.getHours()) + ":"+ String.format("%02d", idd.getMinutes()));
                    ref.child("statusConfirm").setValue("--:--");
                    ref.child("statusCooking").setValue("--:--");
                    ref.child("statusDelivery").setValue("--:--");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(eventListener);



        DatabaseReference cart = FirebaseDatabase.getInstance().getReference("/CurrentCart"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ValueEventListener eventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    cart.removeValue();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        cart.addListenerForSingleValueEvent(eventListener2);

    }


}
