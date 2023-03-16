package com.example.foodiedelivery.login;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class LoginAdapter extends FragmentStateAdapter {

    private Context context;
    int totalTab;

    public LoginAdapter(FragmentManager fragmentManager,Lifecycle lifecycle, Context context, int totalTab) {
        super(fragmentManager, lifecycle);
        this.context = context;
        this.totalTab = totalTab;
    }

    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                LoginFragment loginFragment = new LoginFragment();
                return loginFragment;
            case 1:
                SignupFragment signupFragment = new SignupFragment();
                return signupFragment;
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return totalTab;
    }
}
