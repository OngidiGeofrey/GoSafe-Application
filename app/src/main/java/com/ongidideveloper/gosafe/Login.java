package com.ongidideveloper.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

    public class Login extends AppCompatActivity {
    private EditText emailAdress,Password;
    private ProgressDialog progressDialog;
    private Button btnLogin;
    private TextView forgot,signUp;
    private FirebaseAuth fAuth;
    private FirebaseUser fuser;
    private final String adminID="67r70zXOxvZU4SB15pobPIpmGco2";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin=findViewById(R.id.btnLogin);
        signUp=findViewById(R.id.textSignUp);
        forgot=findViewById(R.id.textReset);
        fAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);

        //capture onclick lister on  login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_details();
            }
        });

        // capture onclick listener on signup link
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_register_activity();
            }
        });
        // capture onclick listener on password reset link
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


           final EditText resetMail=new EditText(v.getContext());
            final AlertDialog.Builder passwordResetDialoq =new AlertDialog.Builder(v.getContext());
            passwordResetDialoq.setTitle("Enter your Email To reset password");
            passwordResetDialoq.setView(resetMail);


          passwordResetDialoq.setPositiveButton("SEND", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    progressDialog.show();
                    progressDialog.setMessage("Please Wait ...");
                    progressDialog.setCanceledOnTouchOutside(false);

                    String mail=resetMail.getText().toString().toLowerCase().trim();

                    fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Reset Link is sent to your Email", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Oops ! "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
            });
          passwordResetDialoq.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int which) {
                  //close the dialog
              }
          });
            passwordResetDialoq.create().show();
            }
        });









    }

    private void validate_details()
    {
        emailAdress=findViewById(R.id.TextEmailAddressLogin);
        Password=findViewById(R.id.TextPasswordLogin);





        String email=emailAdress.getText().toString().toLowerCase().trim();
        String password=Password.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            emailAdress.setError("Email is Required");
            return;
        }

       if(TextUtils.isEmpty(password))
        {
            Password.setError("Password is Required");
            return;
        }

        else
        {
            // Firebase Sign In code
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        fuser=fAuth.getCurrentUser();
                        if (!fuser.isEmailVerified())
                        {

                            startActivity(new Intent(getApplicationContext(),check_Verified.class));
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();

                            if(email.equalsIgnoreCase("ongidigeofrey@gmail.com") && password.equals("0717907898"))
                            {
                                startActivity(new Intent(getApplicationContext(),AdminDashboard.class));
                                finish();
                            }
                            else
                            {
                                startActivity(new Intent(getApplicationContext(),DashBoard.class));
                                finish();

                            }





                        }
                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error Occured "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }


    }

    private void call_register_activity()
    {
        Intent registerActivity=new Intent(getApplicationContext(),Register.class);
        startActivity(registerActivity);
        finish();
    }


}