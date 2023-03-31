package com.example.foodiedelivery.fragments;

import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.models.AESCrypt;
import com.example.foodiedelivery.models.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SignupFragment extends Fragment {

    EditText emailEditText;
    EditText passwordEditText;
    EditText confirmPasswordEdit;
    EditText userName;
    private FoodieDatabase fd;
    private static final String TAG = "SignupFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fd = Room.databaseBuilder
                (getActivity(), FoodieDatabase.class, "foodie.db").build();
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
            UserDao userDao = fd.userDao();
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                try {
                    String encPw = AESCrypt.encrypt(password);
                    User user = new User(email, encPw, name, false);

                    Log.d(TAG, "onViewCreated: "+ user.toString());
                    int userId = (int) userDao.insert(user);
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(rootView.getContext(), "Sign up Successfully", Toast.LENGTH_SHORT).show();
                    });
                    resetForm();
                } catch (SQLiteConstraintException e) {
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                    });
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    public void resetForm() {
        emailEditText.setText(null);
        passwordEditText.setText(null);
        confirmPasswordEdit.setText(null);
        userName.setText(null);
    }
}
