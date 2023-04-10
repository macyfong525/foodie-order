package com.example.foodiedelivery.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.foodiedelivery.databinding.FragmentSignupTabBinding;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.repositories.AESCrypt;
import com.example.foodiedelivery.models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    SharedPreferences sharedPreferences;
    FragmentSignupTabBinding fragmentSignupTabBinding;
    private int userId;
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private User user;

    private FoodieDatabase fd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentSignupTabBinding = FragmentSignupTabBinding.inflate(inflater, container, false);
        fd = Room.databaseBuilder(getActivity(), FoodieDatabase.class, "foodie.db").build();
        return fragmentSignupTabBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View rootView, @Nullable Bundle savedInstanceState) {
        fragmentSignupTabBinding.editTextSignUpEmail.setEnabled(false);
        fragmentSignupTabBinding.btnSignUp.setText("Update");
        // get user in share preference
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userId = sharedPreferences.getInt("userId", -1);
        Log.d(TAG, "userid is " + userId);
        if (userId == -1) {
            return;
        }
        UserDao userDao = fd.userDao();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                user = userDao.getUserByid(userId);
                Log.d(TAG, "Profile user: " + user.toString());
                if (user != null) {
                    setProfileText(user);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Update user
        fragmentSignupTabBinding.btnSignUp.setOnClickListener((View view) -> {
            if (!validateProfile()) {
                return;
            }
            executorService.execute(() -> {
                String encPw = null;
                try {
                    encPw = AESCrypt.encrypt(password);
                    user.setPassword(encPw);
                    user.setUsername(name);
                    int rowcnt = userDao.updateUser(user);
                    Log.d(TAG, "update rowcnt: " + rowcnt);
                    getActivity().runOnUiThread(() -> {
                        if (rowcnt == 1) {
                            Toast.makeText(getActivity(), "Update Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Error when update to database", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

        });

    }

    public boolean validateProfile() {
        email = fragmentSignupTabBinding.editTextSignUpEmail.getText().toString();
        password = fragmentSignupTabBinding.editTextSignUpPassword.getText().toString();
        confirmPassword = fragmentSignupTabBinding.editTextSignUpConfirmPassword.getText().toString();
        name = fragmentSignupTabBinding.editTextSignUpUsername.getText().toString();

        if (name.equals("") || password.equals("")) {
            Toast.makeText(getActivity(), "Invalid! enter required field", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Invalid! enter required field");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(getActivity(), "password is not matched", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "password is not matched");
            return false;
        }
        return true;
    }


    public void setProfileText(User user) throws Exception {
        String decPw = null;
        if (!user.getPassword().equals("")) {
            decPw = AESCrypt.decrypt(user.getPassword());
        }
        fragmentSignupTabBinding.editTextSignUpEmail.setText(user.getEmail());
        fragmentSignupTabBinding.editTextSignUpPassword.setText(decPw);
        fragmentSignupTabBinding.editTextSignUpConfirmPassword.setText(decPw);
        fragmentSignupTabBinding.editTextSignUpUsername.setText(user.getUsername());
        Log.d(TAG, "User is : " + user.getEmail());
    }
}