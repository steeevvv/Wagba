package com.stivoo.wagba.ui.meals;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.stivoo.wagba.FirebaseQueryLiveData;
import com.stivoo.wagba.pojo.CartItem;
import com.stivoo.wagba.pojo.MealModel;

import java.util.HashMap;
import java.util.Map;

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
                    CartItem item = new CartItem(meal.getName(),meal.getRestaurant_name(),meal.getPrice(),info,qty,meal.getImg(),20.0f);
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
