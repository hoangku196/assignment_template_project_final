package com.example.assignmenttemplateproject.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.adapter.ListUserAdapter;
import com.example.assignmenttemplateproject.dao.UserDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListUser extends Fragment {

    private RecyclerView recyclerListUser;

    private LinearLayoutManager manager;

    private UserDAO userDAO;

    private ListUserAdapter listUserAdapter;


    public ListUser() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_user, container, false);
        getActivity().setTitle("Người dùng");
        setHasOptionsMenu(true);

        findAllViewById(view);

        setRecyclerListUser();

        return view;
    }

    private void findAllViewById(View view) {
        recyclerListUser = view.findViewById(R.id.recyclerListUser);
    }

    private void setRecyclerListUser() {

        manager = new LinearLayoutManager(getActivity());
        recyclerListUser.setLayoutManager(manager);

        listUserAdapter = new ListUserAdapter(getActivity(), userDAO);
        recyclerListUser.setAdapter(listUserAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list_user, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_list_user_add:
                    getActivity().onBackPressed();
                return true;
            case R.id.item_list_user_change_password:
                return true;
            case R.id.item_list_user_log_out:
                NavHostFragment.findNavController(this).navigate(R.id.action_listUser2_to_login);
                return true;
        }

        return super.onOptionsItemSelected(item);
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
