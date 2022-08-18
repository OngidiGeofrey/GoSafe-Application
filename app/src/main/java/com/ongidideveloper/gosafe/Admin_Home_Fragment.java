package com.ongidideveloper.gosafe;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Admin_Home_Fragment extends Fragment {

    CardView cardView_home, cardView_user,cardView_logout,
            cardView_settings,cardView_add_user,cardView_victim;

    private onFragmentEmergenceButtonClicked listener;
    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.admin_home_fragment,container,false);

        cardView_home=view.findViewById(R.id.card_view_home);
        cardView_victim=view.findViewById(R.id.card_view_victim);

        cardView_add_user=view.findViewById(R.id.card_view_user_adds);

        cardView_user=view.findViewById(R.id.card_view_user);
        cardView_logout=view.findViewById(R.id.card_view_logout);
        cardView_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.load_home_fragment();



            }
        });


        cardView_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.load_add_fragment();
            }
        });

        cardView_add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.load_add_fragment();
            }
        });


        cardView_victim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.load_victim_fragment();
            }
        });

        cardView_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.load_view_users();
            }
        });

        cardView_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.logout_fragment();
            }
        });


        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onFragmentEmergenceButtonClicked){
            listener= (onFragmentEmergenceButtonClicked) context;


        }
        else {
            throw new ClassCastException(context.toString() + " must implement listener");
        }
    }
    public interface onFragmentEmergenceButtonClicked{
        public  void load_home_fragment();
        public  void load_add_fragment();
        public  void load_victim_fragment();
        public  void logout_fragment();
        public  void load_view_users();

    }
}
