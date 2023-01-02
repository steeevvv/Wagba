package com.stivoo.wagba.ui.home.profile;

import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LiveDataListener extends LiveData<DataSnapshot> {
    private final Query query;
    private final MyValueEventListener listener = new MyValueEventListener();

    public LiveDataListener(Query query) {
        this.query = query;
    }

    public LiveDataListener(DatabaseReference ref) {
        this.query = ref;
    }

    @Override
    protected void onActive() {
        query.addValueEventListener(listener);
    }

    @Override
    protected void onInactive() {
        query.removeEventListener(listener);
    }

    private class MyValueEventListener implements ValueEventListener {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            setValue(dataSnapshot);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    }
}
