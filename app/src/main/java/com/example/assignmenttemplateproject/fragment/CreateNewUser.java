package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.UserDAO;
import com.example.assignmenttemplateproject.model.User;
import com.example.assignmenttemplateproject.mysql.BackgroundTask;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewUser extends Fragment {

    private EditText edUserName, edPassword, edRePassword, edPhone, edFullName;
    private Button btnAddUser, btnShowUser, btnCancelUser;

    private UserDAO userDAO;

    public CreateNewUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_user, container, false);
        getActivity().setTitle("Người dùng");

        findAllViewById(view);

        setAllOnClick();

        return view;
    }

    private void findAllViewById(View view) {
        edUserName = view.findViewById(R.id.edUserName);
        edPassword = view.findViewById(R.id.edPassword);
        edRePassword = view.findViewById(R.id.edRePassword);
        edPhone = view.findViewById(R.id.edPhone);
        edFullName = view.findViewById(R.id.edFullName);
        btnAddUser = view.findViewById(R.id.btnAddUser);
        btnCancelUser = view.findViewById(R.id.btnCancelUser);
        btnShowUser = view.findViewById(R.id.btnShowUser);
    }

    private void setAllOnClick() {
        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String method = "register";
                BackgroundTask backgroundTask = new BackgroundTask(getActivity());
                String userName = edUserName.getText().toString();
                String password = edPassword.getText().toString();
                String rePassword = edRePassword.getText().toString();
                String phone = edPhone.getText().toString();
                String fullName = edFullName.getText().toString();
                if (userName.isEmpty() || password.isEmpty() || rePassword.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(getActivity(), "Điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6 || !phone.matches("\\d{10,11}")) {
                    if (password.length() < 6)
                        Toast.makeText(getActivity(), "Mật khẩu phải có ít nhất 6 kí tự", Toast.LENGTH_SHORT).show();
                    if (!phone.matches("\\d{10}"))
                        Toast.makeText(getActivity(), "Số điện thoại phải là 10 số", Toast.LENGTH_SHORT).show();
                } else if (!password.equalsIgnoreCase(rePassword)) {
                    Toast.makeText(getActivity(), "Hai mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                } else if (userDAO.searchUser(userName)) {
                    Toast.makeText(getActivity(), "User Name đã tồn tại", Toast.LENGTH_SHORT).show();
                } else {
                    if (userDAO.insertUser(new User(userName, password, phone, fullName))) {
                        backgroundTask.execute(method, userName, password, phone, fullName);
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Thêm thất bại kiểm tra lại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCancelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edUserName.setText("");
                edPassword.setText("");
                edRePassword.setText("");
                edPhone.setText("");
                edFullName.setText("");
            }
        });

        btnShowUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_createNewUser_to_listUser2);
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
