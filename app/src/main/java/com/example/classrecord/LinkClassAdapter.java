package com.example.classrecord;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class LinkClassAdapter extends RecyclerView.Adapter<LinkClassAdapter.LinkViewHolder> {

    public static List<LinkTable> linkItem = new ArrayList<>();

    private Context context;
    private String sub;
    static SubClassActivity listener;

    LinkClassAdapter(Context context1, String subject, SubClassActivity listener){
        context = context1;
        sub = subject;
        LinkClassAdapter.listener = listener;
    }

    void updateLinks(List<LinkTable> list){
        linkItem.clear();
        for(LinkTable table : list){
            if(table.className.equalsIgnoreCase(sub))
                linkItem.add(table);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LinkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.link_item, parent, false);
        return new LinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkViewHolder holder, int position) {
        String text = "Link "+(position+1);
        holder.textView.setText(text);
    }

    @Override
    public int getItemCount() {
        return linkItem.size();
    }

    public static class LinkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        public TextView textView;
        private Context context;
        public LinkViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.linkName);
            itemView.setOnClickListener(this);
            context = itemView.getContext();
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos = this.getAdapterPosition();
            String send = linkItem.get(pos).link;
            if(!send.contains("https://"))
                send = "https://"+send;
            try {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(send));
                context.startActivity(linkIntent);
            }
            catch(Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
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
                            listener.delete(linkItem.get(pos));
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
