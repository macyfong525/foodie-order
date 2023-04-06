package com.example.foodiedelivery.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.fragments.AddRestaurantFragment;
import com.example.foodiedelivery.fragments.CartFragment;
import com.example.foodiedelivery.fragments.HomeFragment;
import com.example.foodiedelivery.fragments.OrderFragment;
import com.example.foodiedelivery.fragments.ProfileFragment;
import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.models.CartItem;
import com.example.foodiedelivery.models.CartViewModel;
import com.example.foodiedelivery.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    AddRestaurantFragment addRestaurantFragment = new AddRestaurantFragment();
    CartFragment cartFragment = new CartFragment();
    OrderFragment orderFragment = new OrderFragment();
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    FoodieDatabase fd;
    SharedPreferences sharedPreferences;
    private int cartQuantity = 0;
    private User user;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);

        // check user is admin and show menu
        MenuItem menuAddRes = menu.findItem(R.id.addRestaurantFragment);
        UserDao userDao = fd.userDao();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            int userId = sharedPreferences.getInt("userId", -1);
            user = userDao.getUserByid(userId);
            runOnUiThread(() -> {
                if (user.getIsAdmin()) {
                    menuAddRes.setVisible(true);
                } else {
                    menuAddRes.setVisible(false);
                }
            });
        });


        final MenuItem menuItem = menu.findItem(R.id.cart);
        View actionView = menuItem.getActionView();
        TextView cartBadgeTextView = actionView.findViewById(R.id.cart_badge_text_view);

        // setting badge
        cartBadgeTextView.setText(String.valueOf(cartQuantity));
        cartBadgeTextView.setVisibility(cartQuantity == 0 ? View.GONE : View.VISIBLE);

        actionView.setOnClickListener((View v) -> {
            onOptionsItemSelected(menuItem);
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addRestaurantFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, addRestaurantFragment).commit();
                break;
            case R.id.logout:
                signOut();
                Toast.makeText(this, "YOU'RE SUCCESSFULLY LOGGED OUT", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cart:
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.container, cartFragment);
                ft.addToBackStack(null);
                ft.commit();
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fd = Room.databaseBuilder(getApplicationContext(), FoodieDatabase.class, "foodie.db").build();

        // for ViewModel
        CartViewModel cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.getCart().observe(this, (List<CartItem> cartItems) -> {
            int quantity = 0;
            for (CartItem cartItem : cartItems) {
                quantity += cartItem.getQuantity();
            }
            cartQuantity = quantity;
            invalidateOptionsMenu();
        });

        // handle top
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // handle bottom
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, homeFragment).commit();
                        return true;
                    case R.id.order:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, orderFragment).commit();
                        return true;
                    case R.id.profile:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, profileFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }

    private void signOut() {
        try {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
            gsc = GoogleSignIn.getClient(this, gso);
            gsc.signOut().addOnCompleteListener((Task<Void> task) -> {
                SharedPreferences sharedPreferences
                        = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}