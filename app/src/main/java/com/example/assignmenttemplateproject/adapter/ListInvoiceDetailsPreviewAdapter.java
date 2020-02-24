package com.example.assignmenttemplateproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.model.Book;

import java.util.List;

public class ListInvoiceDetailsPreviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<InvoiceDetailsPreview> previews;

    public ListInvoiceDetailsPreviewAdapter(Context context, List<InvoiceDetailsPreview> previews) {
        this.previews = previews;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return previews.size();
    }

    @Override
    public Object getItem(int position) {
        return previews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        PreviewViewHolder holder;

        if (convertView == null) {
            holder = new PreviewViewHolder();
            convertView = inflater.inflate(R.layout.adapter_list_invoice_detail_preview, parent);
            holder.tvIdBook = convertView.findViewById(R.id.tvIdBook);
            holder.tvAmountBook = convertView.findViewById(R.id.tvAmountBook);
            holder.tvPriceBook = convertView.findViewById(R.id.tvPriceBook);
            holder.tvTotal = convertView.findViewById(R.id.tvTotal);
            holder.imgDeletePreView = convertView.findViewById(R.id.imgDeletePreView);
            holder.imgDeletePreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    previews.remove(position);
                    notifyDataSetChanged();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (PreviewViewHolder) convertView.getTag();
        }

        InvoiceDetailsPreview preview = previews.get(position);

        holder.tvIdBook.setText(preview.getBook().getIdBook());
        holder.tvAmountBook.setText(String.valueOf(preview.getAmount()));
        holder.tvPriceBook.setText(preview.getPrice() + ".vnd");
        holder.tvIdBook.setText(preview.getTotal() + ".vnd");

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    public static class InvoiceDetailsPreview {
        private Book book;
        private int amount;
        private float price, total;

        public InvoiceDetailsPreview(Book book, int amount, float price) {
            this.book = book;
            this.amount = amount;
            this.price = price;
            this.total = price * amount;
        }

        public Book getBook() {
            return book;
        }

        public int getAmount() {
            return amount;
        }

        public float getPrice() {
            return price;
        }

        public float getTotal() {
            return total;
        }
    }

    private class PreviewViewHolder {

        private TextView tvIdBook, tvAmountBook, tvPriceBook, tvTotal;
        private ImageView imgDeletePreView;

    }
}
