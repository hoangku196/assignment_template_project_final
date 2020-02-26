package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.adapter.ListInvoiceDetailsPreviewAdapter;
import com.example.assignmenttemplateproject.dao.BookDAO;
import com.example.assignmenttemplateproject.dao.InvoiceDetailsDAO;
import com.example.assignmenttemplateproject.model.InvoiceDetails;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListInvoiceDetails extends Fragment {

    private ListView lvInvoiceDetail;

    private ListInvoiceDetailsPreviewAdapter adapter;

    private List<ListInvoiceDetailsPreviewAdapter.InvoiceDetailsPreview> listDetails = new ArrayList<>();

    private BookDAO bookDAO;
    private InvoiceDetailsDAO detailsDAO;

    private Bundle bundle;

    public ListInvoiceDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_invoice_details, container, false);
        bundle = getArguments();
        adapter = new ListInvoiceDetailsPreviewAdapter(getActivity(), listDetails);
        bookDAO = new BookDAO(getActivity());
        detailsDAO = new InvoiceDetailsDAO(getActivity());
        adapter.setBookDAO(bookDAO);
        adapter.setDetailsDAO(detailsDAO);
        bookDAO.connectDatabase();
        detailsDAO.connectDatabase();

        List<InvoiceDetails> invoiceDetailsList = detailsDAO.getAllInvoiceDetails(bundle.getString("key_idInvoice"));

        for (InvoiceDetails item : invoiceDetailsList) {
            listDetails.add(new ListInvoiceDetailsPreviewAdapter.InvoiceDetailsPreview(item.getIdDetails(), item.getBook(), item.getAmount(), item.getBook().getPrice()));
            adapter.notifyDataSetChanged();
        }


        findAllViewById(view);

        setLvInvoiceDetail();

        return view;
    }

    private void setLvInvoiceDetail() {
        lvInvoiceDetail.setAdapter(adapter);
    }

    private void findAllViewById(View view) {
        lvInvoiceDetail = view.findViewById(R.id.lvInvoiceDetail);
    }

}
