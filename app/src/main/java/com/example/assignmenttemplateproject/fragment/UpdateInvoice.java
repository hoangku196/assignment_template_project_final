package com.example.assignmenttemplateproject.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.InvoiceDAO;
import com.example.assignmenttemplateproject.model.Invoice;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateInvoice extends Fragment {

    private InvoiceDAO invoiceDAO;

    private Bundle bundle;

    private EditText edUpdateDateInvoice;
    private Button btnUpdateDateInvoice, btnUpdateInvoice, btnExitUpdateInvoice;

    private final String TAG = this.getClass().getSimpleName();

    public UpdateInvoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_invoice, container, false);

        bundle = getArguments();
        invoiceDAO = new InvoiceDAO(getActivity());
        invoiceDAO.connectDatabase();

        findAllViewById(view);

        setAllOnClick();

        return view;
    }

    private void setAllOnClick() {
        btnUpdateDateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        calendar.set(year, month, dayOfMonth);
                        edUpdateDateInvoice.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnUpdateInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String idInvoice = bundle.getString("key_idInvoice");
                    Log.e(TAG, bundle.getString("key_idInvoice"));
                    String dateInvoice = edUpdateDateInvoice.getText().toString();

                    //todo
                    if (invoiceDAO.updateInvoice(new Invoice(idInvoice, dateInvoice))) {
                        Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        getActivity().onBackPressed();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Nhập sai", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void findAllViewById(View view) {
        edUpdateDateInvoice = view.findViewById(R.id.edUpdateDateInvoice);
        btnUpdateDateInvoice = view.findViewById(R.id.btnUpdateDateInvoice);
        btnUpdateInvoice = view.findViewById(R.id.btnUpdateInvoice);
        btnExitUpdateInvoice = view.findViewById(R.id.btnExitUpdateInvoice);
    }

}
