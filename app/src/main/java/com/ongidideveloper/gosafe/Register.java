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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class Register extends AppCompatActivity {
    private Button btn;
    private  TextView loginLink;
    private TextInputEditText fname,lname,email,pass,confirmPass,phone;
    private ProgressDialog progressDialog;
    private TextInputLayout layout_fname,layout_lname,layout_email,layout_phone,layout_pass,layout_confirm_pass;


    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn=findViewById(R.id.btnRegister);
        loginLink=findViewById(R.id.textView_sign_In);
        progressDialog=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");



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
        email=findViewById(R.id.textInputEditText_email_sign_up);
        fname=findViewById(R.id.textInputEditText_f_name_sign_up);
        lname=findViewById(R.id.textInputEditText_l_name_sign_up);
        pass=findViewById(R.id.textInputEditText_pass_sign_up);
        phone=findViewById(R.id.textInputEditText_phone_sign_up);
        confirmPass=findViewById(R.id.textInputEditText_confirm_sign_up);


        layout_fname=findViewById(R.id.textInputLayout_f_name_register);
        layout_lname=findViewById(R.id.textInputLayout_l_name_register);
        layout_email=findViewById(R.id.textInputLayout_email_register);
        layout_phone=findViewById(R.id.textInputLayout_phone_register);
        layout_pass=findViewById(R.id.textInputLayout_pass_register);
        layout_confirm_pass=findViewById(R.id.textInputLayout_confirm_pass_register);


        String first_name=fname.getText().toString().toLowerCase().trim();
        String last_name=lname.getText().toString().toLowerCase().trim();
        String full_name=last_name+" "+last_name;
        String emailAddress=email.getText().toString().toLowerCase().trim();
        String phone_number=phone.getText().toString().trim();
        String password=pass.getText().toString().trim();
        String confirm=confirmPass.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";


        if(TextUtils.isEmpty(first_name))
        {
            layout_fname.setError("first Name Required");
            return;
        }

        if(TextUtils.isEmpty(last_name))
        {
            layout_lname.setError("Last Name is required");
            return;
        }
        if(TextUtils.isEmpty(emailAddress))
        {
            layout_email.setError("Email Required");
            return;
        }

        else if(TextUtils.isEmpty(phone_number))
        {
            layout_phone.setError("Phone is required");
            return;
        }
        else if(TextUtils.isEmpty(password))
        {
            layout_pass.setError("Password is required");
            return;
        }

        else if(TextUtils.isEmpty(password))
        {
            layout_pass.setError("Password is required");
            return;
        }

        else if(!(password.equals(confirm)))

        {
            layout_pass.setError("Password and Confirm Password Mismatch");
            layout_confirm_pass.setError("Password and Confirm Password Mismatch");
            return;
        }

        else if (!(emailAddress.matches(emailPattern)))
        {
            layout_email.setError("Enter a valid email Address");
            return;
        }
        else if((password.length()<8) || (!(password.matches(PASSWORD_PATTERN))))
        {
            layout_pass.setError("Password must contain minimum 8 characters " +
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

                                User user=new User(full_name,phone_number,emailAddress);
                                databaseReference.child(emailAddress).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                        Toast.makeText(getApplicationContext(), "Account created successfully. Click the verification link sent to your Email to verify account", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getApplicationContext(),Login.class));

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(getApplicationContext(), "Error Occurred!! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();

                                    }
                                });








                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(), "Error Occurred!! "+e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });



                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error Occurred!! "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
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