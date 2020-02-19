package com.example.assignmenttemplateproject.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.adapter.ListInvoiceAdapter;
import com.example.assignmenttemplateproject.dao.InvoiceDAO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListInvoice extends Fragment {

    private RecyclerView recyclerListInvoice;

    private InvoiceDAO invoiceDAO;

    private ListInvoiceAdapter invoiceAdapter;

    private LinearLayoutManager layoutManager;

    public ListInvoice() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_invoice, container, false);

        findAllViewById(view);

        setRecyclerListInvoice();

        registerForContextMenu(recyclerListInvoice);

        return view;
    }

    private void setRecyclerListInvoice() {
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        invoiceAdapter = new ListInvoiceAdapter(getActivity(), invoiceDAO);
        recyclerListInvoice.setLayoutManager(layoutManager);
        recyclerListInvoice.setAdapter(invoiceAdapter);
    }

    private void findAllViewById(View view) {
        recyclerListInvoice = view.findViewById(R.id.recyclerListInvoice);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.context_menu_list_invoice, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_list_user_update:
                NavHostFragment.findNavController(this).navigate(R.id.action_listInvoice_to_updateInvoice);
                return true;
            case R.id.context_list_user_add_details:
                NavHostFragment.findNavController(this).navigate(R.id.action_listInvoice_to_createNewInvoiceDetails);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
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
