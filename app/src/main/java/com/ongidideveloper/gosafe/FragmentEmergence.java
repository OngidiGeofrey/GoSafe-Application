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

public class FragmentEmergence extends Fragment {
    private onFragmentEmergenceButtonClicked listener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_emergence, container, false);
       Button addGuardian=view.findViewById(R.id.btnGuadianSave);
       addGuardian.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               listener.saveGuardian();
           }
       });
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof onFragmentEmergenceButtonClicked)
        {
            listener= (onFragmentEmergenceButtonClicked) context;

        }
        else {
            throw new ClassCastException(context.toString() + "must implement listener");
        }
    }

    public interface onFragmentEmergenceButtonClicked
    {
        public void saveGuardian();
    }
}
