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
import com.example.assignmenttemplateproject.dao.CategoryBookDAO;
import com.example.assignmenttemplateproject.model.CategoryBook;

import java.util.List;

public class ListCategoryBookAdapter extends RecyclerView.Adapter<ListCategoryBookAdapter.CategoryViewHolder> {

    private CategoryBookDAO categoryDAO;
    private List<CategoryBook> categoryBooks;
    private LayoutInflater inflater;

    public ListCategoryBookAdapter(Context context, CategoryBookDAO categoryDAO) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categoryDAO = categoryDAO;
        categoryBooks = this.categoryDAO.getAllCategory();
    }


    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.adapter_list_category_book, parent, false);

        return new CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryViewHolder holder, int position) {
        final CategoryBook category = categoryBooks.get(position);

        holder.tvNameCategory.setText(category.getName());
        holder.tvLocationCategory.setText(String.valueOf(category.getLocation()));

        holder.ivDeleteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (categoryDAO.deleteCategory(category)) {
                    Toast.makeText(holder.itemView.getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                    categoryBooks.remove(category);
                    notifyDataSetChanged();
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("idCategory_key", category.getId());

                Navigation.findNavController(v).navigate(R.id.action_listCategoryBook_to_updateCategory, bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryBooks.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivDeleteCategory;
        private TextView tvNameCategory, tvLocationCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDeleteCategory = itemView.findViewById(R.id.ivDeleteCategory);
            tvNameCategory = itemView.findViewById(R.id.tvNameCategory);
            tvLocationCategory = itemView.findViewById(R.id.tvLocationCategory);
        }
    }
}
