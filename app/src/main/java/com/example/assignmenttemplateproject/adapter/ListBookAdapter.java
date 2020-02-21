package com.example.assignmenttemplateproject.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.BookDAO;
import com.example.assignmenttemplateproject.model.Book;

import java.util.List;

public class ListBookAdapter extends RecyclerView.Adapter<ListBookAdapter.BookViewHolder> {

    private BookDAO bookDAO;
    private LayoutInflater inflater;
    private List<Book> books;

    public ListBookAdapter(Context context, BookDAO bookDAO) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.bookDAO = bookDAO;
        books = this.bookDAO.getAllListBook();
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.adapter_list_book, parent, false);

        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookViewHolder holder, int position) {
        final Book book = books.get(position);

        holder.tvIdBook.setText(book.getIdBook());
        holder.tvPriceBook.setText(String.valueOf(book.getPrice()));
        holder.tvInStockBook.setText(String.valueOf(book.getInStock()));
        holder.ivDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bookDAO.deleteBook(book)) {
                    books.remove(book);
                    notifyDataSetChanged();
                    Toast.makeText(holder.itemView.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(holder.itemView.getContext(), "Xóa thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("key_idBook", book.getIdBook());
                Navigation.findNavController(v).navigate(R.id.action_listBook_to_updateBook, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {

        ImageView ivDeleteBook;
        TextView tvIdBook, tvPriceBook, tvInStockBook;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDeleteBook = itemView.findViewById(R.id.ivDeleteBook);
            tvIdBook = itemView.findViewById(R.id.tvIdBook);
            tvPriceBook = itemView.findViewById(R.id.tvPriceBook);
            tvInStockBook = itemView.findViewById(R.id.tvInStockBook);
        }
    }
}
