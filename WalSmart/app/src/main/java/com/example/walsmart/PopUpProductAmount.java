package com.example.walsmart;


import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductRecord;

import static com.example.walsmart.ProductSet.CreateSetActivity.staticProductList;

public class PopUpProductAmount {
    private TextView productAmount;
    private final Product productToAdd;
    private final String destination;

    public PopUpProductAmount(Product product, String destination) {
        productToAdd = product;
        this.destination = destination;
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void showPopupWindow(final View view) {
        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View ppView = inflater.inflate(R.layout.popup_product_amount, null);
        @SuppressLint("InflateParams") final PopupWindow popUp = new PopupWindow(
                ppView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
        popUp.showAtLocation(view, Gravity.CENTER, 0, 0);

        productAmount = ppView.findViewById(R.id.product_amount);
        productAmount.setText("1");
        ImageButton decreaseBtn = ppView.findViewById(R.id.decrease);
        decreaseBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(productAmount.getText().toString());
            if (amount > 1) {
                amount--;
                productAmount.setText(Integer.toString(amount));
            }
        });

        ImageButton increaseBtn = ppView.findViewById(R.id.increase);
        increaseBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(productAmount.getText().toString());
            if (amount < 10) {
                amount++;
                productAmount.setText(Integer.toString(amount));
            }
        });

        Button addBtn = ppView.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(productAmount.getText().toString());
            if (destination.equals("Basket")) {
                ProductRecord pr = new ProductRecord(productToAdd, amount);
                Basket.addProductRecord(pr);
            } else if (destination.equals("Product Set")) {
                for (int i = 0; i < amount; i++) {
                    staticProductList.add(productToAdd);
                }
            }
            Toast.makeText(v.getContext(), "Product added", Toast.LENGTH_SHORT).show();
            popUp.dismiss();
        });
        ImageButton cancelBtn = ppView.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(v -> {
            popUp.dismiss();
        });
    }
}