package com.ongidideveloper.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class check_Verified extends AppCompatActivity {
    private Button resendLink;
    private FirebaseAuth fAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_verified);
        fAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        resendLink=findViewById(R.id.btnResendLink);
        resendLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("please wait ...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);

                FirebaseUser user =fAuth.getCurrentUser();
               user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void unused) {
                       progressDialog.dismiss();
                       Toast.makeText(getApplicationContext(), "Verification link sent successfully.Click the verification link sent to your Email to verify account", Toast.LENGTH_LONG).show();
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               startActivity(new Intent(getApplicationContext(),Login.class));
                               finish();
                           }
                       },3000);

                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       progressDialog.dismiss();
                       Toast.makeText(getApplicationContext(), "Error Occured!! "+e.getMessage(), Toast.LENGTH_LONG).show();
                   }
               });
            }
        });
    }
}