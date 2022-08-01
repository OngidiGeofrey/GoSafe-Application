package com.ongidideveloper.gosafe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PoliceAdapter extends RecyclerView.Adapter<HospitalAdapter.MyViewHolder>{

    Context context;
    ArrayList<Police> list;

    public PoliceAdapter(Context context, ArrayList<Police> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public HospitalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.hospital_card_view,parent,false);
        return new HospitalAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HospitalAdapter.MyViewHolder holder, int position) {

        Police police=list.get(position);
        holder.name.setText(police.getName());
        holder.town.setText(police.getLocation());
        holder.contact.setText(police.getEmergency_contacts());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name,town,contact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.hospital_name);
            town=itemView.findViewById(R.id.hospital_town);
            contact=itemView.findViewById(R.id.hospital_contact_list);
        }
    }


}
