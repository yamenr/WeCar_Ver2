package com.example.wecar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.wecar.D_FireBase.FirebaseServices;
import com.example.wecar.D_FireBase.ListFragmentType;
import com.example.wecar.D_FireBase.User;
import com.example.wecar.pagess.AddCarFragment;
import com.example.wecar.pagess.CarListMapFragment;
import com.example.wecar.pagess.CarsListFragment;
import com.example.wecar.pagess.FavoriteFragment;
import com.example.wecar.pagess.LoginFragment;
import com.example.wecar.pagess.ProfileFragment;
import com.example.wecar.pagess.SearchFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Stack;

// TODO: multi photo
// TODO: Check favourites, details
// TODO: Search fragment - recyclerview crash issue

public class MainActivity extends AppCompatActivity {

    private FirebaseServices fbs;
    private BottomNavigationView bottomNavigationView;
    private ListFragmentType listType;
    private Stack<Fragment> fragmentStack = new Stack<>();
    private FrameLayout fragmentContainer;
    private User userData;
    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init()
    {
        fbs = FirebaseServices.getInstance();
        //fbs.getAuth().signOut();
        listType = ListFragmentType.Regular;
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
            if (item.getItemId() == R.id.action_home)
            {
                //selectedFragment = new CarsListFragment();
                selectedFragment = new CarListMapFragment();
            }
            else if (item.getItemId() == R.id.action_fav) {
                selectedFragment = new FavoriteFragment();
                //selectedFragment = new CarListMapFragment(); // Favourits
            }
            else if (item.getItemId() == R.id.action_add) {
                selectedFragment = new AddCarFragment();
            }
            else if (item.getItemId() == R.id.action_search) { // Add search bar
                selectedFragment = new SearchFragment();
            }
            /*
            else if (item.getItemId() == R.id.action_profile) { // Add search bar
                selectedFragment = new ProfileFragment();
            } */
            else if (item.getItemId() == R.id.action_signout) {
               signout();
              //  selectedFragment=new ProfileFragment();
            }
                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameLayout, selectedFragment)
                            .commit();
                }
                return true;
            }});
        fragmentContainer = findViewById(R.id.frameLayout);
        userData = getUserData();
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.favcheck);// set drawable icon
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (fbs.getAuth().getCurrentUser() == null)
        {
            bottomNavigationView.setVisibility(View.GONE);
            gotoLoginFragment();
            pushFragment(new LoginFragment());
        }
        else
        {
            bottomNavigationView.setVisibility(View.VISIBLE);
            gotoCarList();
            pushFragment(new CarsListFragment());
        }
    }

    public User getUserDataObject()
    {
        return this.userData;
    }

    private void signout() {
        fbs.getAuth().signOut();
        bottomNavigationView.setVisibility(View.INVISIBLE);
        gotoLoginFragment();
    }

    private void gotoProfileFragment() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new ProfileFragment());
        ft.commit();
    }
    private void gotoLoginFragment() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new LoginFragment());
        ft.commit();
    }
    public void gotoCarList() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout,new CarsListFragment());
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (fragmentStack.size() > 1) {
            fragmentStack.pop(); // Remove the current fragment from the stack
            Fragment previousFragment = fragmentStack.peek(); // Get the previous fragment

            // Replace the current fragment with the previous one
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, previousFragment)
                    .commit();
        } else {
            super.onBackPressed(); // If there's only one fragment left, exit the app
        }
    }

    // Method to add a new fragment to the stack
    public void pushFragment(Fragment fragment) {
        fragmentStack.push(fragment);
        /*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit(); */
    }

    public User getUserData()
    {
        final User[] currentUser = {null};
        try {
            fbs.getFire().collection("users")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    User user = document.toObject(User.class);
                                    if (fbs.getAuth().getCurrentUser() != null && (fbs.getAuth().getCurrentUser().getEmail().equals(user.getUsername()))) {
                                    //if (fbs.getAuth().getCurrentUser().getEmail().equals(user.getUsername())) {
                                        currentUser[0] = document.toObject(User.class);
                                        fbs.setCurrentUser(currentUser[0]);
                                    }
                                }
                            } else {
                                Log.e("AllRestActivity: readData()", "Error getting documents.", task.getException());
                            }
                        }
                    });
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "error reading!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return currentUser[0];
    }
}