package com.example.assignmenttemplateproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import androidx.databinding.DataBindingUtil;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.BookDAO;
import com.example.assignmenttemplateproject.dao.InvoiceDetailsDAO;
import com.example.assignmenttemplateproject.databinding.AdapterListInvoiceDetailPreviewBinding;
import com.example.assignmenttemplateproject.model.Book;

import java.util.List;

public class ListInvoiceDetailsPreviewAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<InvoiceDetailsPreview> previews;
    private AdapterListInvoiceDetailPreviewBinding viewBinding;
    private BookDAO bookDAO;
    private InvoiceDetailsDAO detailsDAO;

    public ListInvoiceDetailsPreviewAdapter(Context context, List<InvoiceDetailsPreview> previews) {
        this.previews = previews;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setBookDAO(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    public void setDetailsDAO(InvoiceDetailsDAO detailsDAO) {
        this.detailsDAO = detailsDAO;
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
        final InvoiceDetailsPreview preview = previews.get(position);
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.adapter_list_invoice_detail_preview, parent, false);
        viewBinding.setPreview(preview);

        ImageView imgDeletePreView = viewBinding.getRoot().findViewById(R.id.imgDeletePreView);
        imgDeletePreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previews.remove(preview);
                if (bookDAO != null)
                    updateBookInDatabase(preview);
                if (detailsDAO != null)
                    detailsDAO.deleteInvoiceDetails(Integer.parseInt(preview.getIdDetails()));
                notifyDataSetChanged();
            }
        });

        return viewBinding.getRoot();
    }

    private void updateBookInDatabase(InvoiceDetailsPreview previewItem) {
        Book book = previewItem.getBook();

        int beforeInStock = book.getInStock();
        int afterInStock = beforeInStock + previewItem.getAmount();
        book.setInStock(afterInStock);

        bookDAO.updateBook(book);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }


    public static class InvoiceDetailsPreview {
        private String idDetails;
        private Book book;
        private int amount;
        private float price, total;

        public InvoiceDetailsPreview(Book book, int amount, float price) {
            this.book = book;
            this.amount = amount;
            this.price = price;
            this.total = price * amount;
        }

        public InvoiceDetailsPreview(String idDetails, Book book, int amount, float price) {
            this.idDetails = idDetails;
            this.book = book;
            this.amount = amount;
            this.price = price;
            this.total = price * amount;
        }

        public String getIdDetails() {
            return idDetails;
        }

        public void setIdDetails(String idDetails) {
            this.idDetails = idDetails;
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
}
