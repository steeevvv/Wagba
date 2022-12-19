package com.stivoo.wagba.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.stivoo.wagba.ui.home.HomeActivity;
import com.stivoo.wagba.ui.signup.Signup;
import com.stivoo.wagba.databinding.ActivityLoginBinding;
import com.stivoo.wagba.ui.signup.SignupViewModel;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;
    Intent go_to_signup_intent;
    Intent go_to_homepage_intent;
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

        binding.tvSignupLoginbtn.setOnClickListener(v -> {
            startActivity(go_to_signup_intent);
            finish();
        });

        binding.btnLoginLogin.setOnClickListener(v -> {
            String mail = binding.etSigninMail.getText().toString();
            String pw = binding.etSigninPw.getText().toString();
            if (mail.length()>0 && pw.length()>0) {
                loginViewModel.login(mail, pw);
            }
        });
    }
}