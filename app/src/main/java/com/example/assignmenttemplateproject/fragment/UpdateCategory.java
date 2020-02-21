package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.CategoryBookDAO;
import com.example.assignmenttemplateproject.model.CategoryBook;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateCategory extends Fragment {

    private CategoryBookDAO categoryDAO;

    private Bundle bundle;

    private EditText edUpdateNameCategory, edUpdateDescribeCategory, edUpdateLocationCategory;
    private Button btnUpdateCategory, btnExitUpdateCategory;

    public UpdateCategory() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_category, container, false);

        bundle = getArguments();
        categoryDAO = new CategoryBookDAO(getActivity());
        categoryDAO.connectDatabase();

        findAllViewById(view);

        setAllOnClick();

        return view;
    }

    private void setAllOnClick() {
        btnUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idCategory = bundle.getString("idCategory_key");
                String name = edUpdateNameCategory.getText().toString();
                String describe = edUpdateDescribeCategory.getText().toString();
                int location = Integer.parseInt(edUpdateLocationCategory.getText().toString());

                if (categoryDAO.updateCategoryBook(new CategoryBook(idCategory, name, describe, location))) {
                    Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    getActivity().onBackPressed();
                }
            }
        });
        btnExitUpdateCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void findAllViewById(View view) {
        edUpdateNameCategory = view.findViewById(R.id.edUpdateNameCategory);
        edUpdateDescribeCategory = view.findViewById(R.id.edUpdateDescribeCategory);
        edUpdateLocationCategory = view.findViewById(R.id.edUpdateLocationCategory);
        btnUpdateCategory = view.findViewById(R.id.btnUpdateCategory);
        btnExitUpdateCategory = view.findViewById(R.id.btnExitUpdateCategory);
    }

}
