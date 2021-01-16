package com.example.walsmart.Order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.walsmart.Models.Order;
import com.example.walsmart.Models.ProductRecord;
import com.example.walsmart.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private final int layout_id;
    private ArrayList<Order> items;

    public OrderAdapter(int layout_id, ArrayList<Order> items) {
        this.layout_id = layout_id;
        this.items = items;
    }

    public ArrayList<Order> getItems() {
        return items;
    }

    public void setItems(ArrayList<Order> items) {
        this.items = items;
    }


    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView OrderNumber, OrderDate, OrderPrice;
        public Order order;

        public ViewHolder(@NonNull View OrderView) {
            super(OrderView);
            OrderView.setOnClickListener(this);
            OrderNumber = OrderView.findViewById(R.id.order_number);
            OrderDate = OrderView.findViewById(R.id.order_date);
            OrderPrice = OrderView.findViewById(R.id.order_price);
        }

        @Override
        public void onClick(View v) {
            StringBuilder popup_msg = new StringBuilder();
            for (ProductRecord pr : order.getProducts()) {
                popup_msg.append(pr.getAmount()).append("x ").append(pr.getProduct().getName()).append("\n");
            }
            popup_msg.append("\nPick up address: ").append(order.getCity()).append(", ").append(order.getAddress());
            popup_msg.append("\nPick up date: ").append(order.getPickupDate());
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Order details:")
                    .setMessage(popup_msg)
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(R.drawable.invoice)
                    .show();
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
        TextView OrderNumber = holder.OrderNumber;
        OrderNumber.setText("Order number: " + (index + 975310)); // zmienic na key
        TextView OrderDate = holder.OrderDate;
        OrderDate.setText("Date: " + items.get(index).getDate());
        TextView OrderPrice = holder.OrderPrice;
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        DecimalFormat df = (DecimalFormat) nf;
        OrderPrice.setText("PLN " + df.format(items.get(index).getTotalPrice()));
        holder.order = items.get(index);
    }
}
