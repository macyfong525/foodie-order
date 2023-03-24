package com.example.foodiedelivery.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.activities.MainActivity;
import com.example.foodiedelivery.db.UserDbHelper;
import com.example.foodiedelivery.models.User;

public class LoginFragment extends Fragment {

    EditText emailEditText;
    EditText pwEditText;
    private UserDbHelper userDbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_tab, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(rootView, savedInstanceState);

        rootView.findViewById(R.id.btnLogin).setOnClickListener((View view) -> {
            // get the value from user
            emailEditText = rootView.findViewById(R.id.editTextEmail);
            String email = emailEditText.getText().toString();
            pwEditText = rootView.findViewById(R.id.editTextPassword);
            String password = pwEditText.getText().toString();

            try {
                // connect to db
                userDbHelper = new UserDbHelper(getContext());
                User user = userDbHelper.getUserByEmail(email);

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(rootView.getContext(), "please enter email and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (user == null) {
                    Toast.makeText(rootView.getContext(), "no such user", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(user.getPassword())) {
                    Toast.makeText(rootView.getContext(), "password is wrong", Toast.LENGTH_SHORT).show();
                    return;
                }

                // navigate the main activity
                // TODO put to share
                pushIDtoShare(user.getId());
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                resetForm();
                getActivity().finish();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public void pushIDtoShare(int userId) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        // create editor - needed to make changes to the shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        // make changes
        editor.putInt("userId", userId);
        // commit changes to shared preferences
        editor.commit();
    }

    public void resetForm() {
        emailEditText.setText(null);
        pwEditText.setText(null);
    }
}
