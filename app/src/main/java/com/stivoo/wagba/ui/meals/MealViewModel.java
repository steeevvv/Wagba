package com.stivoo.wagba.ui.meals;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stivoo.wagba.pojo.CartItem;
import com.stivoo.wagba.pojo.MealModel;

public class MealViewModel extends ViewModel {
    private static final DatabaseReference CHECK_REF =
            FirebaseDatabase.getInstance().getReference("/CurrentCart"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

    private static final DatabaseReference CART_REF =
            FirebaseDatabase.getInstance().getReference("/CurrentCart");

    public void writeNewCart(MealModel meal, int qty, String info) {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    CartItem item = new CartItem(meal.getName(),meal.getRestaurant_name(),meal.getPrice(),info,qty,meal.getImg(),Float.parseFloat(meal.getDelivery_fees().substring(4)));
                    CART_REF.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(meal.getName()).setValue(item);
                } else{
                    CART_REF.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(meal.getName()).child("qty").setValue(qty);
                    CART_REF.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(meal.getName()).child("inf").setValue(info);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        CHECK_REF.child(meal.getName()).addListenerForSingleValueEvent(eventListener);
    }

}
