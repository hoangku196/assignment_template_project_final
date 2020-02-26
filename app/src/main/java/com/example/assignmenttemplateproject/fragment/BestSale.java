package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.adapter.ListBestSaleAdapter;
import com.example.assignmenttemplateproject.dao.GeneralQuery;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BestSale extends Fragment {

    private GeneralQuery generalQuery;

    private EditText edTopBestSelling;
    private Button btnSearchTopSelling;
    private RecyclerView recyclerListBestSale;

    private ListBestSaleAdapter adapter;

    public BestSale() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        generalQuery = new GeneralQuery(getActivity());
        generalQuery.connectDatabase();

        View view = inflater.inflate(R.layout.fragment_best_sale, container, false);

        findAllViewByID(view);

        setAllOnClick();

        return view;
    }

    private void setAllOnClick() {
        btnSearchTopSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRecyclerListBestSale();
            }
        });
    }

    private void setRecyclerListBestSale() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(RecyclerView.VERTICAL);

        String month = edTopBestSelling.getText().toString();

        adapter = new ListBestSaleAdapter(getActivity(), generalQuery.searchBestSale(month));

        recyclerListBestSale.setLayoutManager(manager);
        recyclerListBestSale.setAdapter(adapter);
    }

    private void findAllViewByID(View view) {
        edTopBestSelling = view.findViewById(R.id.edTopBestSelling);
        btnSearchTopSelling = view.findViewById(R.id.btnSearchTopSelling);
        recyclerListBestSale = view.findViewById(R.id.recyclerListBestSale);
    }

}
