package com.ongidideveloper.gosafe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Reset_Password extends AppCompatActivity {
 private Button buttonReset;
 private TextView login;
 private EditText emailReset;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        buttonReset=findViewById(R.id.btnReset);
        login=findViewById(R.id.textSignInReset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate_email();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                call_login_activity();
            }
        });
    }

    private void validate_email()
    {
         emailReset=findViewById(R.id.TextEmailAddressReset);
         if(TextUtils.isEmpty(emailReset.getText().toString().toLowerCase().trim()))
        {
            emailReset.setError("Email Address required");
            return;
        }

         else
         {
             // firebase code
             Toast.makeText(getApplicationContext(), "ready to validate", Toast.LENGTH_SHORT).show();
         }
    }

    private void call_login_activity()
    {

        Intent login=new Intent(getApplicationContext(),Login.class);
        startActivity(login);
        finish();
    }
}