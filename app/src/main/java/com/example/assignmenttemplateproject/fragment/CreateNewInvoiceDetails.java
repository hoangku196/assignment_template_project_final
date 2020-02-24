package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.adapter.ListInvoiceDetailsPreviewAdapter;
import com.example.assignmenttemplateproject.dao.BookDAO;
import com.example.assignmenttemplateproject.dao.InvoiceDetailsDAO;
import com.example.assignmenttemplateproject.model.Book;
import com.example.assignmenttemplateproject.model.Invoice;
import com.example.assignmenttemplateproject.model.InvoiceDetails;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CreateNewInvoiceDetails extends Fragment {

    private BookDAO bookDAO;
    private InvoiceDetailsDAO detailsDAO;

    private Bundle bundle;

    private List<ListInvoiceDetailsPreviewAdapter.InvoiceDetailsPreview> previews;

    private ListInvoiceDetailsPreviewAdapter adapter;

    private EditText edIdInvoiceDetail, edAmountInvoiceDetail;
    private Spinner spnIdBook;
    private ListView lvInvoiceDetailPreview;
    private Button btnAddInvoiceDetail, btnPayInvoiceDetail;
    private TextView tvIdInvoice;

    public CreateNewInvoiceDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_invoice_details, container, false);

        bookDAO = new BookDAO(getActivity());
        detailsDAO = new InvoiceDetailsDAO(getActivity());
        bookDAO.connectDatabase();
        detailsDAO.connectDatabase();
        bundle = getArguments();

        findAllViewById(view);

        setSpnIdBook();

        setTvIdInvoice();

        setAllOnClick();

        return view;
    }

    private void setAllOnClick() {
        btnAddInvoiceDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = (Book) spnIdBook.getSelectedItem();
                float price = book.getPrice();
                int amount = Integer.parseInt(edAmountInvoiceDetail.getText().toString());

                ListInvoiceDetailsPreviewAdapter.InvoiceDetailsPreview preview =
                        new ListInvoiceDetailsPreviewAdapter.InvoiceDetailsPreview(book, amount, price);

                previews.add(preview);

                setLvInvoiceDetailPreview();
            }
        });

        btnPayInvoiceDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = edIdInvoiceDetail.getText().toString();
                String idInvoice = bundle.getString("key_idInvoice");
                String dateInvoice = bundle.getString("key_dateInvoice");
                Invoice invoice = new Invoice(idInvoice, dateInvoice);
                boolean check = false;
                for (ListInvoiceDetailsPreviewAdapter.InvoiceDetailsPreview item : previews) {
                    InvoiceDetails details = new InvoiceDetails(id, item.getAmount(), item.getBook(), invoice);
                    check = detailsDAO.insertNewInvoiceDetails(details);
                }
                if (check) {
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setLvInvoiceDetailPreview() {
        adapter = new ListInvoiceDetailsPreviewAdapter(getActivity(), previews);
        lvInvoiceDetailPreview.setAdapter(adapter);
    }

    private void setTvIdInvoice() {
        String idInvoice = bundle.getString("key_idInvoice");
        tvIdInvoice.setText(idInvoice);
    }

    private void setSpnIdBook() {
        List<Book> books = bookDAO.getAllListBook();
        ArrayAdapter<Book> spnIdBookAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, books);
        spnIdBookAdapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnIdBook.setAdapter(spnIdBookAdapter);
    }

    private void findAllViewById(View view) {
        edIdInvoiceDetail = view.findViewById(R.id.edIdInvoiceDetail);
        edAmountInvoiceDetail = view.findViewById(R.id.edAmountInvoiceDetail);
        spnIdBook = view.findViewById(R.id.spnIdBook);
        lvInvoiceDetailPreview = view.findViewById(R.id.lvInvoiceDetailPreview);
        btnAddInvoiceDetail = view.findViewById(R.id.btnAddInvoiceDetail);
        btnPayInvoiceDetail = view.findViewById(R.id.btnPayInvoiceDetail);
        tvIdInvoice = view.findViewById(R.id.tvIdInvoice);
    }

    @Override
    public void onPause() {
        super.onPause();
        bookDAO.disconnectDatabase();
        detailsDAO.disconnectDatabase();
    }
}
