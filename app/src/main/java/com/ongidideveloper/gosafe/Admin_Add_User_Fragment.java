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

public class Admin_Add_User_Fragment extends Fragment {
  private  onFragmentBtnSelected listener;
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view=inflater.inflate(R.layout.admin_add_user_fragment,container,false);

    Button register=view.findViewById(R.id.adminbtnRegisterUser);
    register.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        listener.admin_register_user();
      }
    });

    return view;
  }

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    if (context instanceof onFragmentBtnSelected) {
      listener = (onFragmentBtnSelected) context;
    } else {
      throw new ClassCastException(context.toString() + " must implement listener");
    }
  }

  public interface onFragmentBtnSelected{
    public void  admin_register_user();

  }


}
