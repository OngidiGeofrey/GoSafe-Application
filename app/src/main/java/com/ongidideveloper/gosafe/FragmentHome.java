package com.ongidideveloper.gosafe;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class FragmentHome extends Fragment {
    private  onfragmentBtnSelected listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_home,container,false);
        Button start=view.findViewById(R.id.btnStart);
        Button stop=view.findViewById(R.id.btnStop);
        Button track=view.findViewById(R.id.sendLocation);
        ImageView imageView=view.findViewById(R.id.imageView2);

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.getLocation();

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageResource(R.drawable.mic_on);
                start.setText("RECORDING ...");
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start.setText("START");
                imageView.setImageResource(R.drawable.mic_off);
                listener.stop_recording();


            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start.setVisibility(View.INVISIBLE);
                imageView.setImageResource(R.drawable.mic_on);
                start.setText("Recording ...");




                listener.record_method();

            }
        });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof onfragmentBtnSelected)
        {
            listener= (onfragmentBtnSelected) context;

        }
        else {
            throw new ClassCastException(context.toString() + "must implement listener");
        }

    }

    public interface onfragmentBtnSelected
    {
        public  void record_method();
        public void stop_recording();
        public void getLocation();
    }
}
