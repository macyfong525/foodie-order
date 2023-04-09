package com.example.foodiedelivery.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodiedelivery.adapters.LoginAdapter;
import com.example.foodiedelivery.databinding.ActivityLoginBinding;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    // google sign in
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    SharedPreferences sharedPreferences;
    ActivityLoginBinding activityLoginBinding;
    private FoodieDatabase fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());

        activityLoginBinding.progressBar.setVisibility(View.GONE);
        Log.d(TAG, "onCreate: logged in " + checkUserLogined());
        if (checkUserLogined()) {
            moveToHome();
        }
        // open db if user not login
        fd = Room.databaseBuilder
                (getApplicationContext(), FoodieDatabase.class, "foodie.db").build();

        // log in sign up tab
        setupTab();
        // google single sign on
        googleSignIn();
    }

    public boolean checkUserLogined() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getInt("userId", -1) != -1;
    }

    public void moveToHome() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    private void setupTab() {
        TabLayout tabLayout = activityLoginBinding.tabLayoutLogin;
        ViewPager2 viewPager = activityLoginBinding.viewPagerLogin;

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager.setAdapter(new LoginAdapter(getSupportFragmentManager(),
                getLifecycle(), this, tabLayout.getTabCount()));

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Login");
                            break;
                        case 1:
                            tab.setText("Signup");
                            break;
                    }
                }).attach();
    }

    private void googleSignIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        activityLoginBinding.googleBtn.setOnClickListener((View view) -> {
            activityLoginBinding.progressBar.setVisibility(View.VISIBLE);
            Intent signInIntent = gsc.getSignInIntent();
            googleLauncher.launch(signInIntent);
        });
    }

    private final ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (result) -> {
                if (result.getResultCode() != Activity.RESULT_OK || result == null) {
                    activityLoginBinding.progressBar.setVisibility(View.GONE);
                    return;
                }
                Intent data = result.getData();
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    String name = account.getDisplayName();
                    String email = account.getEmail();
                    Log.d(TAG, "onActivityResult: " + name + email);

                    UserDao userDao = fd.userDao();
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(() -> {
                        int userId = -1;
                        User user = userDao.getUserByEmail(email);
                        if (user == null) {
                            user = new User(email, "", name, false);
                            userId = (int) userDao.insert(user);
                        } else {
                            userId = user.getId();
                        }
                        // navigate to home
                        Log.d(TAG, "google userid: " + userId);
                        pushIDtoShare(userId);
                        moveToHome();
                    });
                } catch (ApiException e) {
                    e.printStackTrace();
                } finally {
                    activityLoginBinding.progressBar.setVisibility(View.GONE);
                }
            }
    );

    public void pushIDtoShare(int userId) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        // create editor - needed to make changes to the shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // make changes
        editor.putInt("userId", userId);
        // commit changes to shared preferences
        editor.commit();
    }
}