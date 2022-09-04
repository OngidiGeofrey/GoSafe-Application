package com.ongidideveloper.gosafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VictimAdapter extends RecyclerView.Adapter<VictimAdapter.MyViewHolder> {

    Context context;
    ArrayList<Victim> list;

    public VictimAdapter(Context context, ArrayList<Victim> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.victim_card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

       Victim victim=list.get(position);
        holder.address_line.setText(victim.getAddressLine());
        holder.link.setText(victim.getLink());
        holder.county.setText(victim.getCounty());
        holder.date.setText(victim.getDate());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cardView;

        TextView address_line,link,county,date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            address_line=itemView.findViewById(R.id.victim_address_line);
            link=itemView.findViewById(R.id.victim_location);
            county=itemView.findViewById(R.id.victim_contact_list);
            date=itemView.findViewById(R.id.victim_date);


        }
    }
}
