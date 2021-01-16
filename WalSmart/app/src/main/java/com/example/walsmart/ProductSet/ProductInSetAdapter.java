package com.example.walsmart.ProductSet;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walsmart.ConfirmationActivity;
import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductRecord;
import com.example.walsmart.Models.Stock;
import com.example.walsmart.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Math.round;

public class ProductInSetAdapter extends RecyclerView.Adapter<com.example.walsmart.ProductSet.ProductInSetAdapter.ViewHolder> {

    private final int layout_id;
    private ArrayList<ProductRecord> items;


    public ProductInSetAdapter(int layout_id, ArrayList<ProductRecord> items) {
        this.layout_id = layout_id;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, productSize, productPrice;
        private ImageButton decreaseBtn, increaseBtn, cancelBtn;
        private TextView productAmount;

        @SuppressLint("SetTextI18n")
        public ViewHolder(@NonNull View productView) {
            super(productView);
            productName = productView.findViewById(R.id.product_name);
            productSize = productView.findViewById(R.id.product_size);
            productPrice = productView.findViewById(R.id.product_price);
            productAmount = productView.findViewById(R.id.product_amount);
            decreaseBtn = productView.findViewById(R.id.decrease);
            increaseBtn = productView.findViewById(R.id.increase);
            cancelBtn = productView.findViewById(R.id.cancel_btn);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout_id, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int index) {
        TextView productName = holder.productName;
        productName.setText(items.get(index).getProduct().getName());
        TextView productSize = holder.productSize;
        productSize.setText(items.get(index).getProduct().getSize());
        TextView productPrice = holder.productPrice;
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        productPrice.setText("PLN " + df.format(items.get(index).getTotalPrice()));
        TextView productAmount = holder.productAmount;
        productAmount.setText(String.valueOf(items.get(index).getAmount()));
        ImageButton decreaseBtn = holder.decreaseBtn;
        decreaseBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(productAmount.getText().toString());
            if (amount > 1) {
                amount--;
                productAmount.setText(Integer.toString(amount));
                items.get(index).decrease();
                notifyDataSetChanged();
            }
        });
        ImageButton increaseBtn = holder.increaseBtn;
        increaseBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(productAmount.getText().toString());
            if (amount < 10) {
                amount++;
                productAmount.setText(Integer.toString(amount));
                items.get(index).increase();
                notifyDataSetChanged();
            }
        });
        ImageButton cancelBtn = holder.cancelBtn;
        cancelBtn.setOnClickListener(v -> {
            String category = items.get(index).getProduct().getCategory();
            String name = items.get(index).getProduct().getName();
            ProductRecord substitute = new ProductRecord(null, 1);
            for (Product next : Stock.getProducts()) {
                if (next.getCategory().equals(category) && !next.getName().equals(name)) {
                    substitute = new ProductRecord(next, 1);
                    break;
                }
            }
            ProductRecord finalSubstitute = substitute;
            AlertDialog.Builder builder = new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle("Maybe a swap?")
                    .setMessage("You don't like " + items.get(index).getProduct().getName()
                            + "? How about swapping it for " + substitute.getProduct().getName()
                            + "? " + substitute.getProduct().getSize() + " for only PLN "
                            + substitute.getProduct().getPrice() + "!")
                    .setPositiveButton("Ok", (dialog, which) -> {
                        items.set(index, finalSubstitute);
                        notifyDataSetChanged();
                    })
                    .setNegativeButton("No, thanks", (dialog, which) -> {
                        items.remove(index);
                        notifyDataSetChanged();
                    })
                    .setIcon(R.drawable.swap);
            AlertDialog dialog = builder.create();
            dialog.setOnShowListener(arg0 -> {
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#C6FF6F00"));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#C6FF6F00"));
            });
            dialog.show();
        });
    }
}
