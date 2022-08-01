package com.ongidideveloper.gosafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

private static int SPLASH_TIME_OUT=3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // removing title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=firebaseAuth.getInstance();



        // setting a post delay

       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {

               if(firebaseAuth.getCurrentUser()!=null)
               {
                   if(firebaseAuth.getCurrentUser().getEmail().equals("ongidigeofrey@gmail.com")){
                       Intent intent=new Intent(MainActivity.this,AdminDashboard.class);
                       startActivity(intent);
                       finish();
                   }
                   else{
                       Intent intent=new Intent(MainActivity.this,DashBoard.class);
                       startActivity(intent);
                       finish();
                   }

               }
               else
               {
                   Intent intent=new Intent(MainActivity.this,Login.class);
                   startActivity(intent);
                   finish();
               }
           }
       },SPLASH_TIME_OUT);


    }
}