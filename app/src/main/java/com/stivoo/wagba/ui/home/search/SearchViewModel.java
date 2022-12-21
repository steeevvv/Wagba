package com.stivoo.wagba.ui.home.search;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivoo.wagba.FirebaseQueryLiveData;


public class SearchViewModel extends ViewModel {
    private static final DatabaseReference RESTAURANTS_REF =
            FirebaseDatabase.getInstance().getReference("/Restaurants");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(RESTAURANTS_REF);


    @NonNull
    public LiveData<DataSnapshot> getAllRestaurants() {
        return liveData;
    }
}
