package com.ongidideveloper.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;

public class Register extends AppCompatActivity {
    private Button btn;
    private  TextView loginLink;
    private EditText email,pass,confirmPass;
    private ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn=findViewById(R.id.btnRegister);
        loginLink=findViewById(R.id.textSignUp);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_register_details();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_login_activity();
            }
        });


    }

    private void validate_register_details()
    {
        email=findViewById(R.id.TextEmailAddressSignUp);
        pass=findViewById(R.id.textPasswordSignUp);
        confirmPass=findViewById(R.id.TextConfirmPasswordSignUp);


        String emailAddress=email.getText().toString().toLowerCase().trim();
        String password=pass.getText().toString().trim();
        String confirm=confirmPass.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        if(TextUtils.isEmpty(emailAddress))
        {
            email.setError("Email Required");
            return;
        }
        else if(TextUtils.isEmpty(password))
        {
            pass.setError("Password is required");
            return;
        }
        else if(!(password.equals(confirm)))

        {
         pass.setError("Password and Confirm Password Mismatch");
         confirmPass.setError("Password and Confirm Password Mismatch");
         return;
        }

       else if (!(emailAddress.matches(emailPattern)))
        {
            email.setError("Enter a valid email Address");
            return;
        }
       else if((password.length()<8) || (!(password.matches(PASSWORD_PATTERN))))
        {
            pass.setError("Password must contain minimum 8 characters " +
                    "at least 1 UpperCase ,1 LowerCase, 1 Number and 1 Special Character");
            return;
        }


        else
        {
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            //register user in firebase
            mAuth.createUserWithEmailAndPassword(emailAddress,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        FirebaseUser  fUser= mAuth.getCurrentUser();

                        fUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Account created successfully. Click the verification link sent to your Email to verify account", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(),Login.class));

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Error Occured!! "+e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });



                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error Occured!! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }


                }
            });




        }

    }
    private void call_login_activity()
    {
        Intent loginActivity=new Intent(getApplicationContext(),Login.class);
        startActivity(loginActivity);
        finish();
    }




}