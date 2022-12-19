package com.stivoo.wagba.ui.home.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.ui.login.Login;
import com.stivoo.wagba.ui.previousorders.PreviousOrdersFragment;
import com.stivoo.wagba.R;
import com.stivoo.wagba.ui.signup.Signup;

import java.util.Locale;

public class ProfileFragment extends Fragment {

    Button viewOrdersBtn;
    Button logOutBtn;
    Intent go_to_login;
    private ProfileViewModel profileViewModel;
    TextView tv_email;
    TextView tv_name;
    ImageView profile_img;

    public ProfileFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        profileViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startActivity(go_to_login);
                    getActivity().finish();
                }
            }
        });

        LiveData<DataSnapshot> liveData = profileViewModel.getDataSnapshotLiveData();

        liveData.observe(this, dataSnapshot -> {
            if (dataSnapshot != null) {
                String name = dataSnapshot.child(profileViewModel.getUid()).child("name").getValue(String.class);
                String mail = dataSnapshot.child(profileViewModel.getUid()).child("mail").getValue(String.class);
                String img = dataSnapshot.child(profileViewModel.getUid()).child("profile_img").getValue(String.class);
                tv_name.setText(name);
                tv_email.setText(mail);
                Glide.with(getContext())
                        .load(img)
                        .into(profile_img);

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        viewOrdersBtn = view.findViewById(R.id.btn_viewOrders);
        logOutBtn = view.findViewById(R.id.btn_sigOut);
        tv_email = view.findViewById(R.id.tv_profile_email);
        tv_name = view.findViewById(R.id.tv_profile_name);
        go_to_login = new Intent(getActivity(), Login.class);
        profile_img = view.findViewById(R.id.profile_img);

        viewOrdersBtn.setOnClickListener(v-> {
            FragmentManager fragm = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragm.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, new PreviousOrdersFragment());
            fragmentTransaction.commit();
        });

        logOutBtn.setOnClickListener(v-> {
            profileViewModel.logOut();
        });
        return view;
    }
}