package com.example.foodiedelivery.login;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.foodiedelivery.activities.MainActivity;
import com.example.foodiedelivery.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class LoginActivity extends AppCompatActivity {

    // google sign in
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;

    // normal sign up and log in tab
    TabLayout tabLayout;
    ViewPager2 viewPager;

    private UserDbHelper userDbHelper;
    private long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // log in sign up tab
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


        // google single sign on
        googleBtn = findViewById(R.id.googleBtn);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener((View view)->{
            signIn();
        });
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        googleLauncher.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> googleLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

                        // connect to db
                        userDbHelper = new UserDbHelper(getApplicationContext());

                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            String name = account.getDisplayName();
                            String email = account.getEmail();
                            Log.d("GOOGLE", "onActivityResult: "+ name + email);

                            User user = userDbHelper.getUserByEmail(email);
                            if (user == null) {
                                // 0 is not admin, 1 is admin
                                userId = userDbHelper.insertUser(email, "", name, 0);
                            }else{
                                userId = user.getId();
                            }

                            // navigate to home
                            // TODO parse userid to Main
                            Bundle bundle = new Bundle();
                            bundle.putLong("userId", userId);
                            Intent intent  = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        } catch (ApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == 200){
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//
//            try {
//                task.getResult(ApiException.class);
//                // navigate to home
//                Intent intent  = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//            } catch (ApiException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}