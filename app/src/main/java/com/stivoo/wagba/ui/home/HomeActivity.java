package com.stivoo.wagba.ui.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.stivoo.wagba.ui.home.cart.CartFragment;
import com.stivoo.wagba.ui.home.home.HomeFragment;
import com.stivoo.wagba.ui.home.profile.ProfileFragment;
import com.stivoo.wagba.R;
import com.stivoo.wagba.ui.home.search.SearchFragment;
import com.stivoo.wagba.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        binding.navBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.Home:
                    FragmentManager fragm = getSupportFragmentManager();
                    fragm.beginTransaction().replace(R.id.frameLayout, new HomeFragment(),"HOMEEE").addToBackStack(null).commit();
                    break;
                case R.id.Profile:
                    replaceFragment(new ProfileFragment());
                    break;
                case R.id.Cart:
                    replaceFragment(new CartFragment());
                    break;
                case R.id.Search:
                    replaceFragment(new SearchFragment());
                    break;
            }
            return true;
        });
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragm = getSupportFragmentManager();
        fragm.beginTransaction().replace(R.id.frameLayout, fragment).addToBackStack(null).commit();
    }
}