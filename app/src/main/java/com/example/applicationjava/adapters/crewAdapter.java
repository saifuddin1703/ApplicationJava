package com.example.applicationjava.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.applicationjava.R;
import com.example.applicationjava.models.crewModel;

import java.util.ArrayList;

public class crewAdapter extends RecyclerView.Adapter<crewAdapter.viewholder> {
    private Context context;
    private ArrayList<crewModel> list;

    public crewAdapter(Context context, ArrayList<crewModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.crew_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
                  crewModel item= list.get(position);
                  holder.name.setText(item.getName());
                  holder.agency.setText(item.getAgency());
                  holder.status.setText(item.getStatus());
                  holder.wiki.setText(item.getWiki());
        Glide.with(context).load(item.getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView name;
        TextView agency;
        TextView status;
        TextView wiki;
        ImageView image;
        public viewholder(@NonNull View itemView) {
            super(itemView);

             name= itemView.findViewById(R.id.name);
             agency=itemView.findViewById(R.id.agency);
             status=itemView.findViewById(R.id.status);
             wiki=itemView.findViewById(R.id.wiki);
             image=itemView.findViewById(R.id.image);
        }
    }
}
