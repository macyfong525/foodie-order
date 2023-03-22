package com.example.foodiedelivery.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.foodiedelivery.fragments.MenuFragment;
import com.example.foodiedelivery.fragments.OrderFragment;
import com.example.foodiedelivery.fragments.ProfileFragment;
import com.example.foodiedelivery.R;
import com.example.foodiedelivery.fragments.AddRestaurantFragment;
import com.example.foodiedelivery.fragments.GreenFragment;
import com.example.foodiedelivery.fragments.HomeFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
   BottomNavigationView bottomNavigationView;
   HomeFragment homeFragment = new HomeFragment();
   OrderFragment orderFragment = new OrderFragment();
   ProfileFragment profileFragment = new ProfileFragment();
   AddRestaurantFragment addRestaurantFragment = new AddRestaurantFragment();
   GreenFragment greenFragment = new GreenFragment();

   GoogleSignInOptions gso;
   GoogleSignInClient gsc;

   @Override
   public boolean onCreateOptionsMenu(Menu menu) {
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.app_menu,menu);
      return true;
   }

   @Override
   public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      switch (item.getItemId()){
         case R.id.addRestaurantFragment:
            getSupportFragmentManager().beginTransaction().replace(R.id.container, addRestaurantFragment).commit();
            break;
         case R.id.green:
            getSupportFragmentManager().beginTransaction().replace(R.id.container, greenFragment).commit();
            break;
         case R.id.logout:
            signOut();
            Toast.makeText(this,"YOU'RE SUCCESSFULLY LOGGED OUT",Toast.LENGTH_SHORT).show();
            break;
         case R.id.cart:
            Intent intent  = new Intent(MainActivity.this, CartActivity.class);
            startActivity(intent);
            break;
      }
      return true;
   }

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      Bundle bundle = getIntent().getExtras();

      gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
      gsc = GoogleSignIn.getClient(this, gso);

      // handle top
      Toolbar toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      // handle bottom
      bottomNavigationView = findViewById(R.id.bottom_navigation);
      getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();

      bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

         @SuppressLint("NonConstantResourceId")
         @Override
         public boolean onNavigationItemSelected(MenuItem item) {
            switch(item.getItemId()){
               case R.id.home:
                  getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                  return true;
               case R.id.order:
                  getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuFragment()).commit();
                  return true;
               case R.id.profile:
                  profileFragment.setArguments(bundle);
                  getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment).commit();
                  return true;
            }
            return false;
         }
      });

   }

   void signOut(){
      gsc.signOut().addOnCompleteListener((Task<Void> task)->{
         finish();
         startActivity(new Intent(MainActivity.this, LoginActivity.class));
      });
   }
}