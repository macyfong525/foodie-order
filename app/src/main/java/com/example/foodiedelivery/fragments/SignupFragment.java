package com.example.foodiedelivery.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.db.UserDbHelper;

public class SignupFragment extends Fragment {

    private UserDbHelper userDbHelper;
    EditText emailEditText ;
    EditText passwordEditText;
    EditText confirmPasswordEdit;

    EditText userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_signup_tab, container, false);
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

            if (password.equals(confirmPassword)) {
                if(email.equals("") || name.equals("") || password.equals("")){
                    Toast.makeText(rootView.getContext(), "enter required field", Toast.LENGTH_SHORT).show();
                }else{
                    // connect to db
                    userDbHelper = new UserDbHelper(getContext());
                    // sign up is only for user, 0 is not admin, 1 is admin
                    long userId = userDbHelper.insertUser(email, password, name, 0);
                    if(userId==-1){
                        Toast.makeText(rootView.getContext(), "Error inserting data into table", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(rootView.getContext(), "Sign up Successfully", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Toast.makeText(rootView.getContext(), "password is not matched", Toast.LENGTH_SHORT).show();
            }

        });

        return rootView;
    }
}
