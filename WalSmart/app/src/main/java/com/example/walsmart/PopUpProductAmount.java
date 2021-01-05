package com.example.walsmart;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.walsmart.Models.Product;

public class PopUpProductAmount {
    private ImageButton decreaseBtn, increaseBtn, cancelBtn;
    private TextView productAmount;
    private Button addBtn;
    private Product productToAdd;

    public PopUpProductAmount(Product product) {
        productToAdd = product;
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void showPopupWindow(final View view) {
        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        View ppView = inflater.inflate(R.layout.popup_product_amount, null);
        @SuppressLint("InflateParams") final PopupWindow popUp = new PopupWindow(
                ppView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
        popUp.showAtLocation(view, Gravity.CENTER, 0, 0);

        productAmount = ppView.findViewById(R.id.product_amount);
        productAmount.setText("1");
        decreaseBtn = ppView.findViewById(R.id.decrease);
        decreaseBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(productAmount.getText().toString());
            if (amount > 1) {
                amount--;
                productAmount.setText(Integer.toString(amount));
            }
        });

        increaseBtn = ppView.findViewById(R.id.increase);
        increaseBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(productAmount.getText().toString());
            if (amount < 10) {
                amount++;
                productAmount.setText(Integer.toString(amount));
            }
        });

        addBtn = ppView.findViewById(R.id.add_btn);
        addBtn.setOnClickListener(v -> {
            int amount = Integer.parseInt(productAmount.getText().toString());
            if(amount == 0) popUp.dismiss();
            for (int i = 0; i < amount; i++) {
                // dodaj do koszyka productToAdd
            }
            popUp.dismiss();
        });

        cancelBtn = ppView.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(v -> {
            popUp.dismiss();
        });

        // klikniecie poza popupem
        ppView.setOnTouchListener((v, event) -> {
            popUp.dismiss();
            return true;
        });
    }
}