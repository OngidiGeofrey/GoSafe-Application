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

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.MyViewHolder>{
    Context context;
    ArrayList<Hospital> list;

    public HospitalAdapter(Context context, ArrayList<Hospital> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HospitalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.hospital_card_view,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HospitalAdapter.MyViewHolder holder, int position) {
        Hospital hospital=list.get(position);
        holder.name.setText(hospital.getHospital_name());
        holder.town.setText(hospital.getLocation());
        holder.contact.setText(hospital.getEmergency_contacts());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,town,contact;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.hospital_name);
            town=itemView.findViewById(R.id.hospital_town);
            contact=itemView.findViewById(R.id.hospital_contact_list);
        }
    }
}
