package com.example.assignmenttemplateproject.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.adapter.ListCategoryBookAdapter;
import com.example.assignmenttemplateproject.dao.CategoryBookDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListCategoryBook extends Fragment {

    private LinearLayoutManager layoutManager;

    private ListCategoryBookAdapter adapter;

    private CategoryBookDAO categoryDAO;

    private RecyclerView recyclerCategory;

    public ListCategoryBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_category_book, container, false);

        findAllViewById(view);

        return view;
    }

    private void findAllViewById(View view) {
        recyclerCategory = view.findViewById(R.id.recyclerListCategoryBook);

        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerCategory.setLayoutManager(layoutManager);

        if (getActivity() != null)
            adapter = new ListCategoryBookAdapter(getActivity(), categoryDAO);
        recyclerCategory.setAdapter(adapter);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryDAO = new CategoryBookDAO(getActivity());
        layoutManager = new LinearLayoutManager(getActivity());
        categoryDAO.connectDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        categoryDAO.connectDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
        categoryDAO.disconnectDatabase();
    }
}
