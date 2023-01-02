package com.stivoo.wagba.ui.home.cart;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivoo.wagba.FirebaseQueryLiveData;

public class CartViewModel extends ViewModel {

    @NonNull
    public LiveData<DataSnapshot> getCart(String uid) {
        final DatabaseReference CURRENT_CART =
                FirebaseDatabase.getInstance().getReference("/CurrentCart/"+ uid);
        final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(CURRENT_CART);
        return liveData;
    }
}
