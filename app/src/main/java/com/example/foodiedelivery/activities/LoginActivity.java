package com.example.foodiedelivery.activities;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.adapters.LoginAdapter;
import com.example.foodiedelivery.db.UserDbHelper;
import com.example.foodiedelivery.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    // google sign in
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    // normal sign up and log in tab
    TabLayout tabLayout;
    ViewPager2 viewPager;
    SharedPreferences sharedPreferences;
    Intent intentMain;
    private UserDbHelper userDbHelper;
    private final ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            (result) -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                    // connect to db
                    userDbHelper = new UserDbHelper(getApplicationContext());
                    int userId;

                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        String name = account.getDisplayName();
                        String email = account.getEmail();
                        Log.d("GOOGLE", "onActivityResult: " + name + email);

                        User user = userDbHelper.getUserByEmail(email);
                        if (user == null) {
                            // 0 is not admin, 1 is admin
                            userId = userDbHelper.insertUser(email, "", name, 0);
                        } else {
                            userId = user.getId();
                        }

                        // navigate to home
                        // TODO put to shared preference
                        pushIDtoShare(userId);
                        startActivity(intentMain);
                    } catch (ApiException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "onCreate: logged in " + checkUserLogined());
        intentMain = new Intent(LoginActivity.this, MainActivity.class);
        if (checkUserLogined()) {
            startActivity(intentMain);
            finish();
        }
        // log in sign up tab
        setupTab();
        // google single sign on
        googleSignIn();
    }

    public boolean checkUserLogined() {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getInt("userId", -1) != -1;
    }

    private void setupTab() {
        tabLayout = findViewById(R.id.tabLayoutLogin);
        viewPager = findViewById(R.id.viewPagerLogin);

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
        googleBtn = findViewById(R.id.googleBtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener((View view) -> {
            Intent signInIntent = gsc.getSignInIntent();
            googleLauncher.launch(signInIntent);
            finish();
        });
    }

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