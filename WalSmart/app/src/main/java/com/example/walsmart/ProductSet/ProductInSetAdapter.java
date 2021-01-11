package com.example.walsmart.ProductSet;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductRecord;
import com.example.walsmart.Models.ProductSet;
import com.example.walsmart.PopUpProductAmount;
import com.example.walsmart.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

import static java.lang.Math.round;

public class ProductInSetAdapter extends RecyclerView.Adapter<com.example.walsmart.ProductSet.ProductInSetAdapter.ViewHolder> {

    private final int layout_id;
    private ArrayList<ProductRecord> items;
    private double totalPrice;

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
        DecimalFormat df = (DecimalFormat)nf;
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
                Log.d("Debug", "Msg: int" + amount);
                items.get(index).increase();
                notifyDataSetChanged();
            }
        });
        ImageButton cancelBtn = holder.cancelBtn;
        cancelBtn.setOnClickListener(v -> {
            Basket.removeProductRecord(items.get(index)); // czy tu na pewno wszystko dziala?
            items.remove(index); // popup czy na pewno?
            notifyDataSetChanged();
        });
    }
}
