package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignmenttemplateproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Start extends Fragment {


    public Start() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_start, container, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigateToLoginFragment();
            }
        }, 1500);

        return view;
    }

    private void navigateToLoginFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_start_to_login);
    }
}
