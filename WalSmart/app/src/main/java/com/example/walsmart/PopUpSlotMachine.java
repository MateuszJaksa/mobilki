package com.example.walsmart;

import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.walsmart.Models.Basket;
import com.example.walsmart.Models.Order;
import com.example.walsmart.Models.Product;
import com.example.walsmart.Models.ProductRecord;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.walsmart.ProductSet.CreateSetActivity.staticProductList;

public class PopUpSlotMachine {
    private Order order;
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    public PopUpSlotMachine(Order order) {
        this.order = order;
    }

    @SuppressLint({"ClickableViewAccessibility", "SetTextI18n"})
    public void showPopupWindow(final View view) {
        view.getContext();
        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert inflater != null;
        @SuppressLint("InflateParams") View ppView = inflater.inflate(R.layout.popup_slot_machine, null);
        @SuppressLint("InflateParams") final PopupWindow popUp = new PopupWindow(
                ppView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, true);
        popUp.showAtLocation(view, Gravity.CENTER, 0, 0);

        VideoView videoView;
        videoView = ppView.findViewById(R.id.animation);
        Uri video;
        int int_random = new Random().nextInt(10); // change according to wanted probability
        if (int_random == 0) {
            // lost 0
            video = Uri.parse("https://firebasestorage.googleapis.com/v0/b/walsmartfb.appspot.com/o/slot_machine.mp4?alt=media&token=0dba2bc3-fb6c-4b9b-9f35-417b0501fea0");
        } else {
            // win 1 - 9
            video = Uri.parse("https://firebasestorage.googleapis.com/v0/b/walsmartfb.appspot.com/o/slot_machine_won.mp4?alt=media&token=c7327b62-f3df-4b62-87a0-acbd63b129d0");
        }
        videoView.setVideoURI(video);
        videoView.setOnPreparedListener(mp -> {
            videoView.start();
            videoView.pause();
        });

        Button yes_btn = ppView.findViewById(R.id.yes_btn);
        yes_btn.setOnClickListener(v -> {
            videoView.start();
            videoView.setOnCompletionListener(mp -> {
                if (int_random == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ppView.getContext())
                            .setTitle("Unlucky")
                            .setMessage("The order price will be increased by 2%. Better luck next time!")
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                String id = mDatabase.push().getKey();
                                order.increasePrice();
                                mDatabase.child("orders").child(id).setValue(order);
                                Intent intent = new Intent(ppView.getContext(), ConfirmationActivity.class);
                                ppView.getContext().startActivity(intent);
                            })
                            .setIcon(R.drawable.clover);
                    AlertDialog dialog = builder.create();
                    dialog.setOnShowListener(arg0 -> {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#C6FF6F00"));
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#C6FF6F00"));
                    });
                    dialog.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ppView.getContext())
                            .setTitle("Congrats, you won!")
                            .setMessage("The order price will be reduced by 20%.")
                            .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                                String id = mDatabase.push().getKey();
                                order.reducePrice();
                                mDatabase.child("orders").child(id).setValue(order);
                                Intent intent = new Intent(ppView.getContext(), ConfirmationActivity.class);
                                ppView.getContext().startActivity(intent);
                            })
                            .setIcon(R.drawable.trophy);
                    AlertDialog dialog = builder.create();
                    dialog.setOnShowListener(arg0 -> {
                        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#C6FF6F00"));
                        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#C6FF6F00"));
                    });
                    dialog.show();
                }

            });
        });
        Button no_btn = ppView.findViewById(R.id.no_btn);
        no_btn.setOnClickListener(v -> {
            String id = mDatabase.push().getKey();
            mDatabase.child("orders").child(id).setValue(order);
            Intent intent = new Intent(ppView.getContext(), ConfirmationActivity.class);
            ppView.getContext().startActivity(intent);
        });
    }
}