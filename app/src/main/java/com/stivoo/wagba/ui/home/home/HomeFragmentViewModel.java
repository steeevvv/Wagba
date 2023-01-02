package com.stivoo.wagba.ui.home.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stivoo.wagba.FirebaseQueryLiveData;

public class HomeFragmentViewModel extends ViewModel {
    private static final DatabaseReference RESTAURANTS_REF =
            FirebaseDatabase.getInstance().getReference("/Restaurants");

    private final FirebaseQueryLiveData liveData = new FirebaseQueryLiveData(RESTAURANTS_REF);

    private static final DatabaseReference MEALS_REF =
            FirebaseDatabase.getInstance().getReference("/FeaturedMeals");
    private final FirebaseQueryLiveData liveMeals = new FirebaseQueryLiveData(MEALS_REF);

    @NonNull
    public LiveData<DataSnapshot> getDataSnapshotLiveData() {
        return liveData;
    }

    @NonNull
    public LiveData<DataSnapshot> getMealsLiveData() {
        return liveMeals;
    }
}
