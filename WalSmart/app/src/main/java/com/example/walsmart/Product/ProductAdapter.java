package com.example.walsmart.Product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walsmart.Models.Product;
import com.example.walsmart.PopUpProductAmount;
import com.example.walsmart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements Filterable {

    private final int layout_id;
    private ArrayList<Product> items;
    private final ArrayList<Product> all_items;
    private final String destination;

    public ProductAdapter(int layout_id, ArrayList<Product> items, String destination) {
        this.layout_id = layout_id;
        this.items = items;
        all_items = items;
        this.destination = destination;
    }

    public ArrayList<Product> getItems() {
        return items;
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }


    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView productName, productSize, productPrice;
        public ImageView productImage;
        public Product product;
        public String myDestination;

        public ViewHolder(@NonNull View productView) {
            super(productView);
            productView.setOnClickListener(this);
            productName = productView.findViewById(R.id.product_name);
            productSize = productView.findViewById(R.id.product_size);
            productPrice = productView.findViewById(R.id.product_price);
            productImage = productView.findViewById(R.id.product_image);
        }

        @Override
        public void onClick(View v) {
            Log.d("Debug", "Msg: " + product.getName());
            PopUpProductAmount popUp = new PopUpProductAmount(product, myDestination);
            popUp.showPopupWindow(v);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int index) {
        TextView productName = holder.productName;
        productName.setText(items.get(index).getName());
        TextView productSize = holder.productSize;
        productSize.setText(items.get(index).getSize());
        TextView productPrice = holder.productPrice;
        productPrice.setText("PLN " + items.get(index).getPrice());
        ImageView productImage = holder.productImage;
        Picasso.with(holder.productImage.getContext()).load(items.get(index).getPhoto()).into(productImage);
        holder.product = items.get(index);
        holder.myDestination = destination;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence cs) {
                items = all_items;
                String charString = cs.toString();
                if (!charString.isEmpty()) {
                    ArrayList<Product> filtered = new ArrayList<>();
                    for (Product row : all_items) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filtered.add(row);
                        }
                    }
                    items = filtered;
                }
                FilterResults results = new FilterResults();
                results.values = items;
                return results;
            }

            @Override
            protected void publishResults(CharSequence cs, FilterResults results) {
                items = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }


}
