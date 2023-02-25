package com.example.foodiedelivery.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.foodiedelivery.LoginActivity;
import com.example.foodiedelivery.MainActivity;
import com.example.foodiedelivery.R;

public class LoginFragment extends Fragment {

    private UserDbHelper userDbHelper;
    EditText emailEditText;
    EditText pwEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login_tab, container, false);
        // Add logic
        // test data
        // long userid = userDbHelper.insertUser("macy@example.com", "123");

        rootView.findViewById(R.id.btnLogin).setOnClickListener((View view) -> {
            // get the value from user
            emailEditText = rootView.findViewById(R.id.editTextEmail);
            String email = emailEditText.getText().toString();
            pwEditText = rootView.findViewById(R.id.editTextPassword);
            String password = pwEditText.getText().toString();

            // connect to db
            userDbHelper = new UserDbHelper(getContext());
            User user = userDbHelper.getUserByEmail(email);

            if (user != null) {
                if (!password.equals(user.getPassword())) {
                    Toast.makeText(rootView.getContext(), "password is wrong", Toast.LENGTH_SHORT).show();
                }else{
                    // navigate the main activity
                    // TODO parse user to Main
                    Intent intent  = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(rootView.getContext(), "no such user", Toast.LENGTH_SHORT).show();
            }

        });

        return rootView;
    }
}
