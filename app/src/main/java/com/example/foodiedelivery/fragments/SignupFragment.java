package com.example.foodiedelivery.fragments;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.db.UserDbHelper;

public class SignupFragment extends Fragment {

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEdit;
    EditText userName;
    private UserDbHelper userDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);
        // Add logic
        rootView.findViewById(R.id.btnSignUp).setOnClickListener((View view) -> {
            emailEditText = rootView.findViewById(R.id.editTextSignUpEmail);
            String email = emailEditText.getText().toString();
            passwordEditText = rootView.findViewById(R.id.editTextSignUpPassword);
            String password = passwordEditText.getText().toString();
            confirmPasswordEdit = rootView.findViewById(R.id.editTextSignUpConfirmPassword);
            String confirmPassword = confirmPasswordEdit.getText().toString();
            userName = rootView.findViewById(R.id.editTextSignUpUsername);
            String name = userName.getText().toString();

            if (email.equals("") || name.equals("") || password.equals("")) {
                Toast.makeText(rootView.getContext(), "enter required field", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(rootView.getContext(), "enter validate email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(confirmPassword)) {
                Toast.makeText(rootView.getContext(), "password is not matched", Toast.LENGTH_SHORT).show();
            }
            try {
                // connect to db
                userDbHelper = new UserDbHelper(getContext());
                // sign up is only for user, 0 is not admin, 1 is admin
                long userId = userDbHelper.insertUser(email, password, name, 0);
                if (userId == -1) {
                    Toast.makeText(rootView.getContext(), "Please try again with other email", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(rootView.getContext(), "Sign up Successfully", Toast.LENGTH_SHORT).show();
                    resetForm();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void resetForm() {
        emailEditText.setText(null);
        passwordEditText.setText(null);
        confirmPasswordEdit.setText(null);
        userName.setText(null);
    }
}
