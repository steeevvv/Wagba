package com.stivoo.wagba.ui.home.profile;

import static android.app.Activity.RESULT_OK;

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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.stivoo.wagba.pojo.UserModel;
import com.stivoo.wagba.ui.login.Login;
import com.stivoo.wagba.ui.previousorders.PreviousOrdersFragment;
import com.stivoo.wagba.R;

public class ProfileFragment extends Fragment {
    Button viewOrdersBtn;
    Button logOutBtn;
    Intent go_to_login;
    private ProfileViewModel profileViewModel;
    EditText tv_email;
    EditText tv_name;
    EditText tv_number;
    ImageView profile_img;
    ImageButton change_pic;

    public ProfileFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        profileViewModel.getLoggedOutMutableLiveData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    startActivity(go_to_login);
                    getActivity().finish();
                }
            }
        });

        profileViewModel.getUsers(FirebaseAuth.getInstance().getCurrentUser().getUid()).observe(this, new Observer<UserModel>() {
            @Override
            public void onChanged(UserModel userModels) {
                if (userModels != null) {
                    UserModel x = new UserModel(userModels.getId(),
                            userModels.getName(),
                            userModels.getEmail(),
                            userModels.getPhone(),
                            userModels.getPicture());
                    tv_email.setText(x.getEmail());
                    tv_name.setText(x.getName());
                    tv_number.setText(x.getPhone());
                    Glide.with(getContext())
                            .load(userModels.getPicture())
                            .into(profile_img);
                    Log.d("TTAAGG", userModels.toString());
                }else{
                    LiveData<DataSnapshot> liveData = profileViewModel.getDataSnapshotLiveData();
                    liveData.observe(ProfileFragment.this, dataSnapshot -> {
                        if (dataSnapshot != null) {
                            String name = dataSnapshot.child(profileViewModel.getUid()).child("name").getValue(String.class);
                            String mail = dataSnapshot.child(profileViewModel.getUid()).child("mail").getValue(String.class);
                            String phone = dataSnapshot.child(profileViewModel.getUid()).child("phone").getValue(String.class);
                            String img = dataSnapshot.child(profileViewModel.getUid()).child("profile_img").getValue(String.class);
                            Glide.with(getContext())
                                    .load(img)
                                    .into(profile_img);
                            UserModel x = new UserModel(FirebaseAuth.getInstance().getUid(),name,mail,phone, img);
                            profileViewModel.insert(x);
                        }
                    });
                }
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
        tv_number = view.findViewById(R.id.tv_profile_number);
        go_to_login = new Intent(getActivity(), Login.class);
        profile_img = view.findViewById(R.id.profile_img);
        change_pic = view.findViewById(R.id.changepic_btn);

        viewOrdersBtn.setOnClickListener(v -> {
            FragmentManager fragm = getParentFragmentManager();
            FragmentTransaction fragmentTransaction = fragm.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, new PreviousOrdersFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });

        logOutBtn.setOnClickListener(v -> {
            profileViewModel.logOut();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        change_pic.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 9999);
        });
        tv_name.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String new_name = tv_name.getText().toString();
                profileViewModel.updateName(new_name, FirebaseAuth.getInstance().getUid());
                return true;
            }
            return false;
        });

        tv_number.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String new_phone = tv_number.getText().toString();
                String phone_pattern = "(010|011|012|015)[0-9]{8}$";

                if(!new_phone.matches(phone_pattern)) {
                    Toast.makeText(getContext(), "Please Set a Valid Phone Number", Toast.LENGTH_SHORT).show();
                    tv_number.setBackgroundResource(R.drawable.custom_input_err);
                    tv_number.setError("Please Set a Valid Phone Number");
                }else{
                    tv_number.setBackgroundResource(R.drawable.custom_input);
                    profileViewModel.updatePhone(new_phone, FirebaseAuth.getInstance().getUid());
                }
                return true;
            }
            return false;
        });


        tv_email.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String new_email = tv_email.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@eng.asu.edu.eg";

                if(!new_email.matches(emailPattern)) {
                    Toast.makeText(getContext(), "Please Set a new email of @eng.asu.edu.eg", Toast.LENGTH_SHORT).show();
                    tv_email.setBackgroundResource(R.drawable.custom_input_err);
                    tv_email.setError("Please Set a new email of @eng.asu.edu.eg");
                }else{
                    tv_email.setBackgroundResource(R.drawable.custom_input);
                    profileViewModel.updateEmail(new_email, FirebaseAuth.getInstance().getUid());
                }
                return true;
            }
            return false;
        });
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK && reqCode == 9999){
            Log.d("TTAAGG", data.getData().toString());
            profile_img.setImageURI(data.getData());
        }
    }
}