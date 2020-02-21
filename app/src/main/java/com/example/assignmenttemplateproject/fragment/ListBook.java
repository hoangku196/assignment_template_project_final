package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.adapter.ListBookAdapter;
import com.example.assignmenttemplateproject.dao.BookDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListBook extends Fragment {

    private LinearLayoutManager layoutManager;
    private BookDAO bookDAO;
    private RecyclerView recyclerListBook;
    private ListBookAdapter recyclerBookAdapter;

    public ListBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_book, container, false);
        bookDAO = new BookDAO(getActivity());
        bookDAO.connectDatabase();

        findAllViewById(view);

        setRecyclerListBook();

        return view;
    }

    private void findAllViewById(View view) {
        recyclerListBook = view.findViewById(R.id.recyclerListBook);
    }

    private void setRecyclerListBook() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        if (getActivity() != null)
            recyclerBookAdapter = new ListBookAdapter(getActivity(), bookDAO);
        recyclerListBook.setAdapter(recyclerBookAdapter);
        recyclerListBook.setLayoutManager(layoutManager);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        bookDAO.connectDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
        bookDAO.disconnectDatabase();
    }
}
