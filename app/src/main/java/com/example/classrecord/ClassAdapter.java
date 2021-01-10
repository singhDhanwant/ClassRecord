package com.example.classrecord;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.SubjectViewHolder> {

    private static List<Table> item = new ArrayList<>();

    private Context context;
    static MainActivity listener;

    List<String> colors;

    ClassAdapter(Context context1, List<String> colors, MainActivity listener){
        context = context1;
        this.colors = colors;
        ClassAdapter.listener = listener;
    }

    void updateList(List<Table> newList){
        item.clear();
        item.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new SubjectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectViewHolder holder, int position) {
        holder.subName.setText(item.get(position).subject);
        Random r = new Random();
        int i1 = r.nextInt(11);
        GradientDrawable draw = new GradientDrawable();
        draw.setColor(Color.parseColor(colors.get(i1)));
        holder.subName.setBackground(draw);
    }

    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private TextView subName;
        private final Context context;
        public SubjectViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            subName = itemView.findViewById(R.id.SubName);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SubClassActivity.class);
            int pos = getAdapterPosition();
            intent.putExtra("Subject", item.get(pos).subject);
            context.startActivity(intent);
            Toast.makeText(v.getContext(), item.get(pos).subject, Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            final int pos = getAdapterPosition();
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setMessage("Are you sure you wanted to delete this item");
                    alertDialogBuilder.setPositiveButton("Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    Toast.makeText(context, "Item deleted", Toast.LENGTH_SHORT).show();
                                    listener.delete(item.get(pos));
                                }
                            });
            alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
            return false;
        }
    }
}
