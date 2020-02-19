package com.example.assignmenttemplateproject.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

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
public class ChangePassword extends Fragment {

    private EditText edChangeUser, edChangePassword, edNewPassword;
    private Button btnUpdateUser, btnExitUpdateUser;

    private UserDAO userDAO;

    public ChangePassword() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        findAllViewById(view);

        setAllOnClick();

        return view;
    }

    private void findAllViewById(View view) {
        edChangeUser = view.findViewById(R.id.edChangeUser);
        edChangePassword = view.findViewById(R.id.edChangePassword);
        edNewPassword = view.findViewById(R.id.edNewPassword);
        btnUpdateUser = view.findViewById(R.id.btnUpdateUser);
        btnExitUpdateUser = view.findViewById(R.id.btnExitUpdateUser);
    }

    private void setAllOnClick() {
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idUser = edChangeUser.getText().toString();
                String password = edChangePassword.getText().toString();
                String newPassword = edNewPassword.getText().toString();
                if (!idUser.isEmpty() || !password.isEmpty() || !newPassword.isEmpty()) {
                    if (userDAO.updatePassword(new User(idUser, password, null, null), newPassword)) {
                        Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        if (getActivity() != null)
                            getActivity().onBackPressed();
                    } else
                        Toast.makeText(getActivity(), "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
        userDAO.connectDatabase();
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
        userDAO.disconnectDatabase();
    }
}
