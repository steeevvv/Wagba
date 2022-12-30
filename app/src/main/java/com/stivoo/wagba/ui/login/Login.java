package com.stivoo.wagba.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseUser;
import com.stivoo.wagba.R;
import com.stivoo.wagba.no_internet;
import com.stivoo.wagba.ui.home.HomeActivity;
import com.stivoo.wagba.ui.home.cart.EmptyCartFragment;
import com.stivoo.wagba.ui.passwordReset.ForgotPassword;
import com.stivoo.wagba.ui.signup.Signup;
import com.stivoo.wagba.databinding.ActivityLoginBinding;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    Intent go_to_signup_intent;
    Intent go_to_homepage_intent;
    Intent go_to_forget_password;
    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loginViewModel.getUserMutableLiveData().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null){
                    startActivity(go_to_homepage_intent);
                    finish();
                }
            }
        });


        go_to_signup_intent = new Intent(this, Signup.class);
        go_to_homepage_intent = new Intent(this, HomeActivity.class);
        go_to_forget_password = new Intent(this, ForgotPassword.class);

        binding.tvSignupLoginbtn.setOnClickListener(v -> {
            startActivity(go_to_signup_intent);
            finish();
        });

        binding.tvSigninForgotpw.setOnClickListener(v -> {
            startActivity(go_to_forget_password);
        });

        binding.btnLoginLogin.setOnClickListener(v -> {
            String mail = binding.etSigninMail.getText().toString();
            String pw = binding.etSigninPw.getText().toString();

            if (mail.length()==0){
                binding.etSigninMail.setError("Please enter an Email first");
                binding.etSigninMail.setBackgroundResource(R.drawable.custom_input_err);
            }
//            else if(pw.length()<8 || pw.length()>15){
//                binding.etSigninPw.setError("Please enter a valid password");
//                binding.etSigninPw.setBackgroundResource(R.drawable.custom_input_err);
//            }
            else{
                loginViewModel.login(mail, pw);
            }
        });

        AtomicInteger pw_visibilityToggle = new AtomicInteger();
        binding.loginViewpwBtn.setOnClickListener(v->{
            if (pw_visibilityToggle.getAndIncrement() % 2 == 0){
                binding.loginViewpwBtn.setImageResource(R.drawable.custom_visibility_off_icon);
                binding.etSigninPw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else{
                binding.loginViewpwBtn.setImageResource(R.drawable.custom_visibility_icon);
                binding.etSigninPw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }
    private boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

}