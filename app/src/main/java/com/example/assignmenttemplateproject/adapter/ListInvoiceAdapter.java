package com.example.assignmenttemplateproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.InvoiceDAO;
import com.example.assignmenttemplateproject.model.Invoice;

import java.util.List;

public class ListInvoiceAdapter extends RecyclerView.Adapter<ListInvoiceAdapter.InvoiceViewHolder> {

    private List<Invoice> invoices;
    private InvoiceDAO invoiceDAO;
    private LayoutInflater inflater;

    public ListInvoiceAdapter(Context context, InvoiceDAO invoiceDAO) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.invoiceDAO = invoiceDAO;
        this.invoices = this.invoiceDAO.getAllInvoice();
    }

    @NonNull
    @Override
    public InvoiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.adapter_list_invoice, parent, false);

        return new InvoiceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InvoiceViewHolder holder, int position) {
        final Invoice invoice = invoices.get(position);
        holder.tvIdInvoice.setText(invoice.getIdInvoice());
        holder.tvDateInvoice.setText(invoice.getDate());
        holder.ivDeleteInvoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (invoiceDAO.deleteInvoice(invoice)) {
                    Toast.makeText(holder.itemView.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    invoices.remove(invoice);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return invoices.size();
    }

    static class InvoiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDeleteInvoice;
        private TextView tvIdInvoice, tvDateInvoice;

        public InvoiceViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDeleteInvoice = itemView.findViewById(R.id.ivDeleteInvoice);
            tvIdInvoice = itemView.findViewById(R.id.tvIdInvoice);
            tvDateInvoice = itemView.findViewById(R.id.tvDateInvoice);
        }
    }
}
