package com.stivoo.wagba.ui.signup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.stivoo.wagba.R;
import com.stivoo.wagba.databinding.ActivitySignupBinding;
import com.stivoo.wagba.ui.login.Login;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Signup extends AppCompatActivity {

    ActivitySignupBinding binding;
    Intent go_to_login_intent;
    private SignupViewModel signupViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);
        go_to_login_intent = new Intent(this, Login.class);

        binding.btnSignupSignup.setOnClickListener(v -> {

            String email = binding.etSignupMail.getText().toString();
            String name = binding.etSignupName.getText().toString();
            String phone = binding.etSignupPhone.getText().toString();

            String emailPattern = "[a-zA-Z0-9._-]+@eng.asu.edu.eg";
            String pw = binding.etSignupPw.getText().toString();
            Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(pw);
            boolean b = m.find();

            if(name.isEmpty()){
                binding.etSignupName.setError("Please Fill in your name");
                binding.etSignupName.setBackgroundResource(R.drawable.custom_input_err);
            }
            else if(!email.matches(emailPattern)) {
                binding.etSignupName.setBackgroundResource(R.drawable.custom_input);
                binding.etSignupMail.setError("Email must end with @eng.asu.edu.eg");
            }
            else if (phone.isEmpty()){
                binding.etSignupPhone.setError("Please Fill in your Phone Number");
                binding.etSignupPhone.setBackgroundResource(R.drawable.custom_input_err);
            }
            else if (!(pw.length()>8 && pw.length()<16)) {
                binding.etSignupPhone.setBackgroundResource(R.drawable.custom_input);
                binding.etSignupPw.setError("Password's length must be between 8 and 16");
                binding.etSignupPw.setBackgroundResource(R.drawable.custom_input_err);
            }else if(!b){
                binding.etSignupPw.setError("Password must include at least one special character");
                binding.etSignupPw.setBackgroundResource(R.drawable.custom_input_err);
            }
            else if(!(pw.equals(binding.etSignupPwConfirm.getText().toString()))){
                binding.etSignupPw.setBackgroundResource(R.drawable.custom_input);
                binding.etSignupPwConfirm.setError("Password doesn't match");
                binding.etSignupPwConfirm.setBackgroundResource(R.drawable.custom_input_err);
            }
            else{
                signupViewModel.register(email, pw, name, phone);
                startActivity(go_to_login_intent);
                finish();
            }
        });

        binding.etSignupMail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if(!binding.etSignupMail.getText().toString().matches("[a-zA-Z0-9._-]+@eng.asu.edu.eg")) {
                        binding.etSignupMail.setError("Email must end with @eng.asu.edu.eg");
                        binding.etSignupMail.setBackgroundResource(R.drawable.custom_input_err);
                    }else {
                        binding.etSignupMail.setBackgroundResource(R.drawable.custom_input);
                    }
                }
        }});

        binding.etSignupPw.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String pw = binding.etSignupPw.getText().toString();
                    Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                    Matcher m = p.matcher(pw);
                    boolean b = m.find();
                    Pattern p2 = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])");
                    Matcher m2 = p2.matcher(pw);
                    boolean b2 = m2.find();

                    if  (!(pw.length()>7 && pw.length()<17)) {
                        binding.etSignupPw.setError("Password's length must be between 8 and 16");
                        binding.etSignupPw.setBackgroundResource(R.drawable.custom_input_err);
                        binding.textView2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_correct, 0, 0, 0);
                        return;
                    }else {
                        binding.textView2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_correct_checked, 0, 0, 0);
                        binding.etSignupPw.setBackgroundResource(R.drawable.custom_input);
                    }

                    if(!b2){
                        binding.etSignupPw.setError("Password must include at least one lowercase and one upercase letter");
                        binding.etSignupPw.setBackgroundResource(R.drawable.custom_input_err);
                        binding.textView4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_correct, 0, 0, 0);
                        return;
                    } else{
                        binding.textView4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_correct_checked, 0, 0, 0);
                        binding.etSignupPw.setBackgroundResource(R.drawable.custom_input);
                    }

                    if(!b){
                        binding.etSignupPw.setError("Password must include at least one special character");
                        binding.etSignupPw.setBackgroundResource(R.drawable.custom_input_err);
                        binding.textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_correct, 0, 0, 0);
                    } else{
                        binding.textView3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_correct_checked, 0, 0, 0);
                        binding.etSignupPw.setBackgroundResource(R.drawable.custom_input);
                    }
                }
            }});

        binding.etSignupPwConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String confirmPW = binding.etSignupPwConfirm.getText().toString();

                    if(!confirmPW.equals(binding.etSignupPw.getText().toString())){
                        binding.etSignupPwConfirm.setError("Password Doesn't Match");
                        binding.etSignupPwConfirm.setBackgroundResource(R.drawable.custom_input_err);
                    } else {
                        binding.etSignupPwConfirm.setBackgroundResource(R.drawable.custom_input);
                    }
                }
        }});


        binding.tvSignupLoginbtn.setOnClickListener(v -> {
            startActivity(go_to_login_intent);
            finish();
        });


        AtomicInteger visibilityToggle = new AtomicInteger();
        binding.pwVisibilityBtn.setOnClickListener(v-> {
            if (visibilityToggle.getAndIncrement() % 2 == 0){
                binding.pwVisibilityBtn.setImageResource(R.drawable.custom_visibility_off_icon);
                binding.etSignupPw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else{
                binding.pwVisibilityBtn.setImageResource(R.drawable.custom_visibility_icon);
                binding.etSignupPw.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });


        AtomicInteger conf_visibilityToggle = new AtomicInteger();
        binding.confpwVisibilityBtn.setOnClickListener(v->{
            if (conf_visibilityToggle.getAndIncrement() % 2 == 0){
                binding.confpwVisibilityBtn.setImageResource(R.drawable.custom_visibility_off_icon);
                binding.etSignupPwConfirm.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else{
                binding.confpwVisibilityBtn.setImageResource(R.drawable.custom_visibility_icon);
                binding.etSignupPwConfirm.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });
    }
}