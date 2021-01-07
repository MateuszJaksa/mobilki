package com.example.walsmart.Basket;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walsmart.Models.Product;
import com.example.walsmart.PopUpProductAmount;
import com.example.walsmart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BasketAdapter extends RecyclerView.Adapter<BasketAdapter.ViewHolder> {

    private final int layout_id;
    private ArrayList<Product> items;

    public BasketAdapter(int layout_id, ArrayList<Product> items) {
        this.layout_id = layout_id;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, productSize, productPrice;
        public ImageView productImage;
        public Product product;

        public ViewHolder(@NonNull View productView) {
            super(productView);
            productName = productView.findViewById(R.id.product_name);
            productSize = productView.findViewById(R.id.product_size);
            productPrice = productView.findViewById(R.id.product_price);
            productImage = productView.findViewById(R.id.product_image);
        }
    }

    @NonNull
    @Override
    public BasketAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BasketAdapter.ViewHolder holder, final int index) {
        TextView productName = holder.productName;
        productName.setText(items.get(index).getName());
        TextView productSize = holder.productSize;
        productSize.setText(items.get(index).getSize());
        TextView productPrice = holder.productPrice;
        productPrice.setText("PLN " + items.get(index).getPrice());
        ImageView productImage = holder.productImage;
        Picasso.with(holder.productImage.getContext()).load(items.get(index).getPhoto()).into(productImage);
    }
}
