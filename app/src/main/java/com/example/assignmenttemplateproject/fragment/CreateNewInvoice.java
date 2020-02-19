package com.example.assignmenttemplateproject.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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
public class CreateNewInvoice extends Fragment {

    private InvoiceDAO invoiceDAO;

    //Layout
    private EditText edIdInvoice, edDateInvoice;
    private Button btnDateInvoice, btnAddInvoice, btnShowInvoice;


    public CreateNewInvoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_new_invoice, container, false);

        findAllViewById(view);

        setAllOnClick();

        return view;
    }

    private void findAllViewById(View view) {
        edIdInvoice = view.findViewById(R.id.edIdInvoice);
        edDateInvoice = view.findViewById(R.id.edDateInvoice);
        btnDateInvoice = view.findViewById(R.id.btnDateInvoice);
        btnAddInvoice = view.findViewById(R.id.btnAddInvoice);
        btnShowInvoice = view.findViewById(R.id.btnShowInvoice);
    }

    private void setAllOnClick() {
        btnDateInvoice.setOnClickListener(new View.OnClickListener() {
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
                        edDateInvoice.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnAddInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String idInvoice = edIdInvoice.getText().toString();
                String date = edDateInvoice.getText().toString();
                try {
                    if (invoiceDAO.insertNewInvoice(new Invoice(idInvoice, date)))
                        Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    else
                        Toast.makeText(getActivity(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnShowInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_createNewInvoice_to_ListInvoice);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invoiceDAO = new InvoiceDAO(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        invoiceDAO.connectDatabase();
    }

    @Override
    public void onPause() {
        super.onPause();
        invoiceDAO.disconnectDatabase();
    }
}
