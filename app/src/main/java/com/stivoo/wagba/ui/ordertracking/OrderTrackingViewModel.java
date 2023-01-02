package com.stivoo.wagba.ui.ordertracking;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivoo.wagba.FirebaseQueryLiveData;

public class OrderTrackingViewModel extends ViewModel {

    @NonNull
    public LiveData<DataSnapshot> getOrder(String timeStamp) {
        final DatabaseReference CURRENT_ORDER =
                FirebaseDatabase.getInstance().getReference("/ConfirmedOrders/"+ FirebaseAuth.getInstance().getCurrentUser().getUid()+ "/" + timeStamp);
        final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(CURRENT_ORDER);

        return liveData;
    }

}
