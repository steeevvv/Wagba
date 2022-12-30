package com.stivoo.wagba.ui.previousorders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivoo.wagba.FirebaseQueryLiveData;

public class PreviousOrdersViewModel extends ViewModel {
    private static final DatabaseReference PREVIOUS_ORDERS =
            FirebaseDatabase.getInstance().getReference("/ConfirmedOrders/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(PREVIOUS_ORDERS);

    @NonNull
    public LiveData<DataSnapshot> getOrders() {
        return liveData;
    }

}
