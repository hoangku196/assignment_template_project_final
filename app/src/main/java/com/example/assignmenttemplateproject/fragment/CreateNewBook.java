package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;

import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewBook extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    //Layout
    private EditText edIdBook, edAuthorBook, edPublisherBook, edPriceBook, edInStockBook;
    private Spinner spnCategoryBook;
    private Button btnAddBook, btnCancelBook, btnShowBook;

    //DAO
    private BookDAO bookDAO;
    private CategoryBookDAO categoryDAO;

    public CreateNewBook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_book, container, false);

        findAllViewById(view);

        setSpinner();

        setAllOnClick();

        return view;
    }

    private void findAllViewById(View view) {
        edIdBook = view.findViewById(R.id.edIdBook);
        edAuthorBook = view.findViewById(R.id.edAuthorBook);
        edPublisherBook = view.findViewById(R.id.edPublisherBook);
        edPriceBook = view.findViewById(R.id.edPriceBook);
        edInStockBook = view.findViewById(R.id.edInStockBook);
        spnCategoryBook = view.findViewById(R.id.spnCategoryBook);
        btnAddBook = view.findViewById(R.id.btnAddBook);
        btnCancelBook = view.findViewById(R.id.btnCancelBook);
        btnShowBook = view.findViewById(R.id.btnShowBook);
    }

    private void setSpinner() {
        List<CategoryBook> categoryBookList = categoryDAO.getAllCategory();

        ArrayAdapter<CategoryBook> adapterCategorySpinner = null;
        if (getActivity() != null) {
            adapterCategorySpinner = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, categoryBookList);
            adapterCategorySpinner.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        }
        spnCategoryBook.setAdapter(adapterCategorySpinner);
    }

    private void setAllOnClick() {
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idBook = edIdBook.getText().toString();
                String author = edAuthorBook.getText().toString();
                String publisher = edPublisherBook.getText().toString();
                float price = Float.parseFloat(edPriceBook.getText().toString());
                int inStock = Integer.parseInt(edInStockBook.getText().toString());
                CategoryBook categoryBook = (CategoryBook) spnCategoryBook.getSelectedItem();
                try {
                    Book book = new Book(idBook, author, publisher, categoryBook, price, inStock);

                    if (bookDAO.insertNewBook(book))
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            }
        });

        btnShowBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_createNewBook_to_listBook);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bookDAO = new BookDAO(getActivity());
        categoryDAO = new CategoryBookDAO(getActivity());
        bookDAO.connectDatabase();
        categoryDAO.connectDatabase();
    }

    @Override
    public void onResume() {
        super.onResume();
        bookDAO.connectDatabase();
        categoryDAO.connectDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
        bookDAO.disconnectDatabase();
        categoryDAO.disconnectDatabase();
    }
}
