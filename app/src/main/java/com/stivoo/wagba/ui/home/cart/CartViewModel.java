package com.stivoo.wagba.ui.home.cart;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivoo.wagba.FirebaseQueryLiveData;

public class CartViewModel extends ViewModel {
    private static final DatabaseReference CURRENT_CART =
            FirebaseDatabase.getInstance().getReference("/CurrentCart/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(CURRENT_CART);

    @NonNull
    public LiveData<DataSnapshot> getCart() {
        return liveData;
    }

}
