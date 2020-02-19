package com.example.assignmenttemplateproject.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.UserDAO;
import com.example.assignmenttemplateproject.model.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateUserInfo extends Fragment {

    private Bundle bundle = getArguments();

    private UserDAO userDAO;

    private EditText edChangeUserName, edChangePhone, edChangeFullName;
    private Button btnUpdateUser, btnExitUpdateUser;

    public UpdateUserInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_user_info2, container, false);

        findAllViewById(view);

        getBundle();

        setAllOnClick();

        return view;
    }

    private void findAllViewById(View view) {
        edChangeUserName = view.findViewById(R.id.edChangeUserName);
        edChangePhone = view.findViewById(R.id.edChangePhone);
        edChangeFullName = view.findViewById(R.id.edChangeFullName);
        btnUpdateUser = view.findViewById(R.id.btnUpdateUser);
        btnExitUpdateUser = view.findViewById(R.id.btnExitUpdateUser);
    }

    private void getBundle() {
        if (bundle != null) {
            edChangeUserName.setText(bundle.getString("username_key"));
            edChangePhone.setText(bundle.getString("phone_key"));
            edChangeFullName.setText(bundle.getString("fullName_key"));
        }
    }

    private void setAllOnClick() {
        //Update User
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bundle != null) {
                    String userName = edChangeUserName.getText().toString();
                    String password = bundle.getString("password_key");
                    String phone = edChangePhone.getText().toString();
                    String fullName = edChangeFullName.getText().toString();

                    User user = new User(userName, password, phone, fullName);

                    if (userDAO.updateUser(user)) {
                        Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        if (getActivity() != null)
                            getActivity().onBackPressed();
                    }
                }
            }
        });

        //Back
        btnExitUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null)
                    getActivity().onBackPressed();
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDAO = new UserDAO(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        userDAO.connectDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
        userDAO.disconnectDatabase();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
