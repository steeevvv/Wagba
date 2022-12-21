package com.stivoo.wagba.ui.passwordReset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.stivoo.wagba.databinding.ActivityForgotPasswordBinding;
import com.stivoo.wagba.ui.login.Login;

public class ForgotPassword extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        intent = new Intent(this, Login.class);
        binding.btnFpwReset.setOnClickListener(v -> {
            String mail = binding.etFpwMail.getText().toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(mail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Please Check your email now!", Toast.LENGTH_SHORT).show();
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

        });
    }
}