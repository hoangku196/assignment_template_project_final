package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.BookDAO;
import com.example.assignmenttemplateproject.dao.CategoryBookDAO;
import com.example.assignmenttemplateproject.model.Book;
import com.example.assignmenttemplateproject.model.CategoryBook;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateBook extends Fragment {

    private BookDAO bookDAO;
    private CategoryBookDAO categoryDAO;

    private Bundle bundle;

    private EditText edUpdatePriceBook, edUpdateInStockBook, edUpdateAuthorBook, edUpdatePublisherBook;
    private Button btnUpdateBook, btnExitUpdateBook;
    private Spinner spnCategoryBook;

    public UpdateBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_book, container, false);

        bookDAO = new BookDAO(getActivity());
        categoryDAO = new CategoryBookDAO(getActivity());
        bundle = getArguments();
        bookDAO.connectDatabase();
        categoryDAO.connectDatabase();

        findAllViewById(view);

        setSpnCategoryBook();

        setAllOnClick();

        return view;
    }

    private void setAllOnClick() {
        btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String idBook = bundle.getString("key_idBook");
                    String author = edUpdateAuthorBook.getText().toString();
                    String publisher = edUpdatePublisherBook.getText().toString();
                    float price = Float.parseFloat(edUpdatePriceBook.getText().toString());
                    int inStock = Integer.parseInt(edUpdateInStockBook.getText().toString());
                    CategoryBook category = (CategoryBook) spnCategoryBook.getSelectedItem();
                    if (bookDAO.updateBook(new Book(idBook, author, publisher, category, price, inStock))) {
                        Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Lỗi nhập sai", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnExitUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
    }

    private void setSpnCategoryBook() {
        List<CategoryBook> categoryBookList = categoryDAO.getAllCategory();
        ArrayAdapter<CategoryBook> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categoryBookList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

        spnCategoryBook.setAdapter(adapter);

    }

    private void findAllViewById(View view) {
        edUpdatePriceBook = view.findViewById(R.id.edUpdatePriceBook);
        edUpdateInStockBook = view.findViewById(R.id.edUpdateInStockBook);
        edUpdateAuthorBook = view.findViewById(R.id.edUpdateAuthorBook);
        edUpdatePublisherBook = view.findViewById(R.id.edUpdatePublisherBook);
        btnUpdateBook = view.findViewById(R.id.btnUpdateBook);
        btnExitUpdateBook = view.findViewById(R.id.btnExitUpdateBook);
        spnCategoryBook = view.findViewById(R.id.spnCategoryBook);
    }

}
