package co.pizzayum.pizzayum_android.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import co.pizzayum.pizzayum_android.R;
import co.pizzayum.pizzayum_android.fragments.CartFragment;
import co.pizzayum.pizzayum_android.fragments.HistoryFragment;
import co.pizzayum.pizzayum_android.fragments.HomeFragment;
import co.pizzayum.pizzayum_android.fragments.ProfileFragment;

public class MainActivity extends AppCompatActivity {
    boolean home_activity_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(listener);


//        BottomNavigationMenuView bottomNavigationMenuView =
//                (BottomNavigationMenuView) navigation.getChildAt(0);
//        View v = bottomNavigationMenuView.getChildAt(3);
//        BottomNavigationItemView itemView = (BottomNavigationItemView) v;
//
//        View badge = LayoutInflater.from(this)
//                .inflate(R.layout.badge_bottom, bottomNavigationMenuView, false);
//        itemView.addView(badge);


        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (home_activity_flag) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
            transaction.commit();
        } else {
            finish();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener listener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_home:
                            home_activity_flag = false;
                            selectedFragment = HomeFragment.newInstance();
                            break;
                        case R.id.navigation_cart:
                            home_activity_flag = true;
                            selectedFragment = CartFragment.newInstance();
                            break;
                        case R.id.navigation_history:
                            home_activity_flag = true;
                            selectedFragment = HistoryFragment.newInstance();
                            break;
                        case R.id.navigation_profile:
                            home_activity_flag = true;
                            selectedFragment = ProfileFragment.newInstance();
                            break;
                    }

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                }
            };
}