package com.example.walsmart.ProductSet;

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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walsmart.Models.CustomSet;
import com.example.walsmart.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomSetAdapter extends RecyclerView.Adapter<CustomSetAdapter.ViewHolder> implements Filterable {

    private final int layout_id;
    private ArrayList<CustomSet> items;
    private final ArrayList<CustomSet> all_items;

    public CustomSetAdapter(int layout_id, ArrayList<CustomSet> items) {
        this.layout_id = layout_id;
        this.items = items;
        all_items = items;
    }

    public ArrayList<CustomSet> getItems() {
        return items;
    }

    public void setItems(ArrayList<CustomSet> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView productSetName, productSetPrice;
        public ImageView productSetImage;
        public CustomSet customSet;

        public ViewHolder(@NonNull View productSetView) {
            super(productSetView);
            productSetView.setOnClickListener(this);
            productSetName = productSetView.findViewById(R.id.product_name);
            productSetPrice = productSetView.findViewById(R.id.product_price);
            productSetImage = productSetView.findViewById(R.id.product_image);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), EditCustomSet.class);
            intent.putExtra("custom_set", customSet);
            intent.putParcelableArrayListExtra("custom_set_products", customSet.getProducts());
            v.getContext().startActivity(intent);
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
        TextView productSetName = holder.productSetName;
        productSetName.setText(items.get(index).getName());
        TextView productSetPrice = holder.productSetPrice;
        productSetPrice.setText("PLN " + items.get(index).getTotalPrice());
        ImageView productSetImage = holder.productSetImage;
        holder.customSet = items.get(index);
        Picasso.with(holder.productSetImage.getContext()).load(items.get(index).getPhoto()).into(productSetImage);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence cs) {
                items = all_items;
                String charString = cs.toString();
                if (!charString.isEmpty()) {
                    ArrayList<CustomSet> filtered = new ArrayList<>();
                    for (CustomSet ps : all_items) {
                        if (ps.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filtered.add(ps);
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
                items = (ArrayList<CustomSet>) results.values;
                notifyDataSetChanged();
            }
        };
    }

}
