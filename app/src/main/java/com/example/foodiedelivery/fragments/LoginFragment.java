package com.example.foodiedelivery.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.activities.MainActivity;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.models.AESCrypt;
import com.example.foodiedelivery.models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginFragment extends Fragment {

    EditText emailEditText;
    EditText pwEditText;
    private FoodieDatabase fd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fd = Room.databaseBuilder
                (getActivity(), FoodieDatabase.class, "foodie.db").build();
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

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(rootView.getContext(), "please enter email and password", Toast.LENGTH_SHORT).show();
                return;
            }

            UserDao userDao = fd.userDao();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {

                try {
                    User user = userDao.getUserByEmail(email);

                    if (user == null) {
                        getActivity().runOnUiThread(() -> {
                            Toast.makeText(rootView.getContext(), "No this user register", Toast.LENGTH_SHORT).show();
                        });
                        return;
                    }

                    if (!password.equals(AESCrypt.decrypt(user.getPassword()))) {
                        getActivity().runOnUiThread(() -> {
                            Log.d(TAG, "onViewCreated: " + user.toString());
                            Toast.makeText(rootView.getContext(), "password is wrong" + user.toString(), Toast.LENGTH_SHORT).show();
                        });
                        return;
                    }

                    // navigate the main activity
                    pushIDtoShare(user.getId());
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                    resetForm();
                    getActivity().finish();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            });


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
