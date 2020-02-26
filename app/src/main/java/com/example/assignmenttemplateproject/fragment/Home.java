package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.assignmenttemplateproject.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private ImageView ivCreateNewUser, ivCreateNewCategoryBook, ivCreateNewBook, ivCreateNewInvoice, ivStatistic, ivBestSelling;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_home, container, false);

        findAllViewById(view);

        setAllOnClick();

        return view;
    }

    private void findAllViewById(View view) {
        ivCreateNewUser = view.findViewById(R.id.ivCreateNewUser);
        ivCreateNewCategoryBook = view.findViewById(R.id.ivCreateNewCategoryBook);
        ivCreateNewBook = view.findViewById(R.id.ivCreateNewBook);
        ivCreateNewInvoice = view.findViewById(R.id.ivCreateNewInvoice);
        ivStatistic = view.findViewById(R.id.ivStatistic);
        ivBestSelling = view.findViewById(R.id.ivBestSelling);
    }

    private void setAllOnClick() {
        ivCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home2_to_createNewUser);
            }
        });

        ivCreateNewCategoryBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home2_to_categoryBook);
            }
        });

        ivCreateNewBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home2_to_createNewBook);
            }
        });
        ivCreateNewInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home2_to_invoice);
            }
        });
        ivStatistic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home2_to_statistic);
            }
        });
        ivBestSelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home2_to_bestSale);
            }
        });
    }
}
