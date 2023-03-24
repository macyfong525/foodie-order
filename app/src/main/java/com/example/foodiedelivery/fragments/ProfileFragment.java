package com.example.foodiedelivery.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.db.UserDbHelper;
import com.example.foodiedelivery.models.User;


public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEdit;
    EditText userName;
    Button btnSignUp;
    SharedPreferences sharedPreferences;
    private int userId;
    private UserDbHelper userDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup_tab, container, false);
        emailEditText = view.findViewById(R.id.editTextSignUpEmail);
        emailEditText.setEnabled(false);
        btnSignUp = view.findViewById(R.id.btnSignUp);
        btnSignUp.setText("Update");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        // get user in share preference
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userId = sharedPreferences.getInt("userId", -1);
        if (userId == -1) {
            return;
        }
        try {
            userDbHelper = new UserDbHelper(getActivity());
            User user = userDbHelper.getUserById(userId);

            // get user update information
            passwordEditText = rootView.findViewById(R.id.editTextSignUpPassword);
            confirmPasswordEdit = rootView.findViewById(R.id.editTextSignUpConfirmPassword);
            userName = rootView.findViewById(R.id.editTextSignUpUsername);

            emailEditText.setText(user.getEmail());
            passwordEditText.setText(user.getPassword());
            confirmPasswordEdit.setText(user.getPassword());
            userName.setText(user.getUsername());
            Log.d(TAG, "User is : " + user.getEmail());

            // Add logic
            btnSignUp.setOnClickListener((View view) -> {
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEdit.getText().toString();
                String name = userName.getText().toString();

                if (name.equals("") || password.equals("")) {
                    Toast.makeText(view.getContext(), "Invalid! enter required field", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Invalid! enter required field");
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(view.getContext(), "password is not matched", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "password is not matched");
                    return;
                }

                user.setEmail(email);
                user.setPassword(password);
                user.setUsername(name);
                int cnt = userDbHelper.updateUser(user);
                Toast.makeText(view.getContext(), "Update Successfully", Toast.LENGTH_SHORT).show();
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}