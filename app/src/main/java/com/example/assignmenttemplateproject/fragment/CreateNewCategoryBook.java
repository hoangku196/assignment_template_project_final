package com.example.assignmenttemplateproject.fragment;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
public class CreateNewCategoryBook extends Fragment {

    private CategoryBookDAO categoryDAO;

    private EditText edIdCategoryBook, edNameCategoryBook, edLocationCategoryBook, edDescribeCategoryBook;
    private Button btnAddCategoryBook, btnCancelCategoryBook, btnShowCategoryBook;

    public CreateNewCategoryBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_book, container, false);

        findAllViewById(view);

        setAllOnClick();

        return view;
    }

    private void findAllViewById(View view) {
        edIdCategoryBook = view.findViewById(R.id.edIdCategoryBook);
        edNameCategoryBook = view.findViewById(R.id.edNameCategoryBook);
        edLocationCategoryBook = view.findViewById(R.id.edLocationCategoryBook);
        edDescribeCategoryBook = view.findViewById(R.id.edDescribeCategoryBook);
        btnAddCategoryBook = view.findViewById(R.id.btnAddCategoryBook);
        btnCancelCategoryBook = view.findViewById(R.id.btnCancelCategoryBook);
        btnShowCategoryBook = view.findViewById(R.id.btnShowCategoryBook);
    }

    private void setAllOnClick() {
        btnAddCategoryBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String id = edIdCategoryBook.getText().toString();
                    String name = edNameCategoryBook.getText().toString();
                    int location = Integer.parseInt(edLocationCategoryBook.getText().toString());
                    String describe = edDescribeCategoryBook.getText().toString();
                    if (!id.isEmpty() || !name.isEmpty()) {
                        if (categoryDAO.insertCategory(new CategoryBook(id, name, describe, location)))
                            Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(getActivity(), "Id và tên không được bỏ trống", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Nhập sai", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnShowCategoryBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_categoryBook_to_listCategoryBook);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryDAO = new CategoryBookDAO(getActivity());
        categoryDAO.connectDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        categoryDAO.connectDatabase();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        categoryDAO.disconnectDatabase();
    }
}
