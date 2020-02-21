package com.example.assignmenttemplateproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignmenttemplateproject.R;
import com.example.assignmenttemplateproject.dao.GeneralQuery;

import java.util.List;

public class ListBestSaleAdapter extends RecyclerView.Adapter<ListBestSaleAdapter.BestSaleViewHolder> {

    private List<SaleItem> sales;
    private LayoutInflater inflater;

    public ListBestSaleAdapter(Context context, List<SaleItem> sales) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.sales = sales;
    }

    @NonNull
    @Override
    public BestSaleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.adapter_list_best_sale, parent);

        return new BestSaleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BestSaleViewHolder holder, int position) {
        SaleItem item = sales.get(position);

        holder.tvIdBestSale.setText(item.getId());
        holder.tvAmountBestSale.setText(String.valueOf(item.getAmount()));
    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    static class BestSaleViewHolder extends RecyclerView.ViewHolder {

        private TextView tvIdBestSale, tvAmountBestSale;

        public BestSaleViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIdBestSale = itemView.findViewById(R.id.tvIdBestSale);
            tvAmountBestSale = itemView.findViewById(R.id.tvAmountBestSale);
        }
    }

    public static class SaleItem {
        private String id;
        private int amount;

        public SaleItem(String id, int amount) {
            this.id = id;
            this.amount = amount;
        }

        public String getId() {
            return id;
        }

        public int getAmount() {
            return amount;
        }
    }
}
