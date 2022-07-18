package com.example.disasterpreventionmanagement.safetytips;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.disasterpreventionmanagement.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private ArrayList<Item> items;
    private Context context;

    public Adapter(ArrayList<Item> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip,
                parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        final Item item = items.get(position);
        holder.imageView.setImageResource(item.getImageResourse());
        holder.titleTextView.setText(item.getTitle());
        holder.descTextView.setText(item.getDesc());
        holder.descTextView.setOnStateChangeListener(new ExpandableTextView.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean isShrink) {
                Item contentItem = items.get(position);
                contentItem.setShrink(isShrink);
                items.set(position, contentItem);
            }
        });
        holder.descTextView.setText(item.getDesc());
        holder.descTextView.resetState(item.isShrink());
        holder.videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = item.getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
               context.startActivity(i);
            }
        });

    }
    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ExpandableTextView descTextView;
        TextView titleTextView;
        MaterialButton videoView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            descTextView = itemView.findViewById(R.id.descTextView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            videoView = itemView.findViewById(R.id.videoView);
        }
    }
}