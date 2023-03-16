package com.example.foodiedelivery;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodiedelivery.login.User;
import com.example.foodiedelivery.login.UserDbHelper;


public class ProfileFragment extends Fragment {
   private UserDbHelper userDbHelper;
   EditText emailEditText ;
   EditText passwordEditText;
   EditText confirmPasswordEdit;

   EditText userName;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      Bundle bundle = getArguments();
      Long userId = bundle.getLong("userId");

      userDbHelper = new UserDbHelper(getContext());
      User user = userDbHelper.getUserById(userId);

      View rootView = inflater.inflate(R.layout.fragment_signup_tab, container, false);
      emailEditText = rootView.findViewById(R.id.editTextSignUpEmail);
      passwordEditText = rootView.findViewById(R.id.editTextSignUpPassword);
      confirmPasswordEdit = rootView.findViewById(R.id.editTextSignUpConfirmPassword);
      userName = rootView.findViewById(R.id.editTextSignUpUsername);

      emailEditText.setText(user.getEmail());
      passwordEditText.setText(user.getPassword());
      confirmPasswordEdit.setText(user.getPassword());
      userName.setText(user.getUsername());

      Button btn = rootView.findViewById(R.id.btnSignUp);
      btn.setText("Update");
      // Add logic
      btn.setOnClickListener((View view) -> {
         String email = emailEditText.getText().toString();
         String password = passwordEditText.getText().toString();
         String confirmPassword = confirmPasswordEdit.getText().toString();
         String name = userName.getText().toString();

         if (password.equals(confirmPassword)) {
            if(email.equals("") || name.equals("") || password.equals("")){
               Toast.makeText(rootView.getContext(), "enter required field", Toast.LENGTH_SHORT).show();
            }else{
               user.setEmail(email);
               user.setPassword(password);
               user.setUsername(name);
               int cnt = userDbHelper.updateUser(user);
               Toast.makeText(rootView.getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
            }
         } else {
            Toast.makeText(rootView.getContext(), "password is not matched", Toast.LENGTH_SHORT).show();
         }

      });

      return rootView;
   }
}