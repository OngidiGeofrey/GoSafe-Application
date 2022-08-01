package com.ongidideveloper.gosafe;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Admin_Add_Hospital extends Fragment {

    private onFragmentBtnSelected listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.admin_add_hospital,container,false);
        Button btn_add_hospital=view.findViewById(R.id.btn_admin_add_hospital);
        btn_add_hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.add_hospital();
            }
        });
        return view;



    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentBtnSelected){
            listener= (onFragmentBtnSelected) context;
        }
        else
        {
            throw new ClassCastException(context.toString() + "Must implement listener");

        }
    }

    public interface onFragmentBtnSelected{
        public void add_hospital();
    }
}
