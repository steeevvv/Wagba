package com.stivoo.wagba.ui.home.profile;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.stivoo.wagba.MainActivity;
import com.stivoo.wagba.pojo.UserModel;
import com.stivoo.wagba.ui.home.home.HomeFragment;
import com.stivoo.wagba.ui.login.Login;
import com.stivoo.wagba.ui.previousorders.PreviousOrdersFragment;
import com.stivoo.wagba.R;

import java.io.IOException;
import java.util.UUID;


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
    Uri selectedImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    private Uri filePath;
    Task<Uri> url;
    UserModel x;


    public ProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

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
                    x = new UserModel(userModels.getId(),
                            userModels.getName(),
                            userModels.getEmail(),
                            userModels.getPhone(),
                            userModels.getPicture());
                    tv_email.setText(x.getEmail());
                    tv_name.setText(x.getName());
                    tv_number.setText(x.getPhone());
                    Log.d("PROFILE", "ROOM");
                    Glide.with(getContext())
                            .load(x.getPicture())
                            .into(profile_img);
                    Log.d("TTAAGG", userModels.toString());
                }else{
                    LiveData<DataSnapshot> liveData = profileViewModel.getDataSnapshotLiveData();
                    liveData.observe(ProfileFragment.this, dataSnapshot -> {
                        if (dataSnapshot != null) {
                            Log.d("PROFILE", "DB");
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

    private void uploadImage() {
        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference ref = storageReference.child("images/"+ FirebaseAuth.getInstance().getCurrentUser().getUid());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
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
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);

            storageReference.child("images/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    DatabaseReference ref =
                            FirebaseDatabase.getInstance().getReference("/Users"+"/"+FirebaseAuth.getInstance().getCurrentUser().getUid());

                    ValueEventListener eventListener = new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()) {
                                ref.child("profile_img").setValue(String.valueOf(uri));
                                profileViewModel.updatePic(String.valueOf(uri), FirebaseAuth.getInstance().getUid());
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    };
                    ref.child("profile_img").addListenerForSingleValueEvent(eventListener);
//                    Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
//                    generatedFilePath = downloadUri.toString(); /// The string(file link) that you need
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        });
        tv_name.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                String new_name = tv_name.getText().toString();
                profileViewModel.updateName(new_name, FirebaseAuth.getInstance().getUid());
                Toast.makeText(getContext(), "Username Updated Successfully", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getContext(), "Phone Number Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            return false;
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if( keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    for(int i = 0; i < fm.getBackStackEntryCount()-1; ++i) {
                        fm.popBackStack();
                    }
                    BottomNavigationView bottomNavigationView;
                    bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.navBar);
                    bottomNavigationView.setSelectedItemId(R.id.Home);
                    return true;
                }
                return false;
            }
        });



//        tv_email.setOnKeyListener((v, keyCode, event) -> {
//            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
//                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
//                String new_email = tv_email.getText().toString();
//                String emailPattern = "[a-zA-Z0-9._-]+@eng.asu.edu.eg";
//
//                if(!new_email.matches(emailPattern)) {
//                    Toast.makeText(getContext(), "Please Set a new email of @eng.asu.edu.eg", Toast.LENGTH_SHORT).show();
//                    tv_email.setBackgroundResource(R.drawable.custom_input_err);
//                    tv_email.setError("Please Set a new email of @eng.asu.edu.eg");
//                }else{
//                    tv_email.setBackgroundResource(R.drawable.custom_input);
//                    profileViewModel.updateEmail(new_email, FirebaseAuth.getInstance().getUid());
//                    Toast.makeText(getContext(), "Email Updated Successfully", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//            return false;
//        });
    }



    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK && data!= null){

            try {
                filePath = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplicationContext().getContentResolver()
                        , data.getData());
                selectedImage =data.getData();
                uploadImage();
                Glide.with(getContext()).load(selectedImage).into(profile_img);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }
}