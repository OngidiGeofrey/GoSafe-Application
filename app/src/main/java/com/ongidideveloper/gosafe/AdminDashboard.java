package com.ongidideveloper.gosafe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class AdminDashboard extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        Admin_Add_User_Fragment.onFragmentBtnSelected,
        Admin_Add_PoliceStation_Fragment.onFragmentBtnSelected,
        Admin_Add_Hospital.onFragmentBtnSelected, Admin_Home_Fragment.onFragmentEmergenceButtonClicked
    {


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Toolbar toolbar;

    NavigationView navigationView;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    // instance variables of Police Fragment
    private Button addPolice;
    private TextInputLayout police_name_layout,police_town_layout,police_phone_layout;
    private TextInputEditText editText_police_name,editText_police_town,editText_police_phone;

    // instance variables of Hospital Fragment
    private TextInputLayout hospital_name_layout,hospital_town_layout,hospital_phone_layout;
    private TextInputEditText editText_hospital_name,editText_hospital_town,editText_hospital_phone;
    private Button addHospital;


    // instance variables of Add_user Fragment
    private TextInputLayout user_name_layout,user_password_layout,user_confirm_password_layout;
    private TextInputEditText editText_user_name,editText_user_password,editText_user_confirm_password;
    private Button addUser;

    // progress dialog
    private ProgressDialog progressDialog;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout=findViewById(R.id.drawer1);

        navigationView=findViewById(R.id.adminNavigationView);
        navigationView.setNavigationItemSelectedListener(this);


        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();



        //Load the default fragment
        fragmentManager=getSupportFragmentManager();
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_fragment, new Admin_Home_Fragment());
        fragmentTransaction.commit();


        //initialise firebase elements
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        //initialize progress Dialog
        progressDialog=new ProgressDialog(this);














    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.closeDrawer(GravityCompat.START);

        if(item.getItemId()==R.id.admin_home){

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_Home_Fragment());
            fragmentTransaction.commit();

        }
        if(item.getItemId()==R.id.add_user){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_Add_User_Fragment());
            fragmentTransaction.commit();


        }
        if(item.getItemId()==R.id.view_users){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_View_Registered_User());
            fragmentTransaction.commit();

        }
        if(item.getItemId()==R.id.add_police_station){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_Add_PoliceStation_Fragment());
            fragmentTransaction.commit();

        }
        if(item.getItemId()==R.id.view_police){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_View_Police_Station());
            fragmentTransaction.commit();

        }
        if(item.getItemId()==R.id.add_hospital){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_Add_Hospital());
            fragmentTransaction.commit();

        }
        if(item.getItemId()==R.id.view_hospital){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_View_Hospitals());
            fragmentTransaction.commit();

        }

        if(item.getItemId()==R.id.view_reports){
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new admin_View_Victim());
            fragmentTransaction.commit();

        }
        if(item.getItemId()==R.id.end_session){

            //log out user



        }

        return true;
    }

    @Override
    public void admin_register_user() {

        user_name_layout=findViewById(R.id.textInputLayout_user_email);
        user_password_layout=findViewById(R.id.textInputLayout_user_password);
        user_confirm_password_layout=findViewById(R.id.textInputLayout_user_confirm_password);

        editText_user_name=findViewById(R.id.user_email_address);
        editText_user_password=findViewById(R.id.user_password);
        editText_user_confirm_password=findViewById(R.id.user_confirm_password);

        String email=editText_user_name.getText().toString().toLowerCase().trim();
        String password=editText_user_password.getText().toString().trim();
        String confirm_password=editText_user_confirm_password.getText().toString().trim();

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";

        if(TextUtils.isEmpty(email)){
            user_name_layout.setError("Please fill this field");
            return;
        }
        else if (TextUtils.isEmpty(password))
        {
            user_password_layout.setError("Please fill this password");
            return;
        }
        else if (!password.equals(confirm_password))
        {
           user_confirm_password_layout.setError("Password Mismatch");
           user_password_layout.setError("Password Mismatch");
           return;
        }
        else if(!email.matches(emailPattern))
        {
            user_name_layout.setError("Please enter a Valid email address");
            return;
        }

        else if(!password.matches(PASSWORD_PATTERN))
        {
            user_password_layout.setError("Password must contain minimum 8 characters " +
                    "at least 1 UpperCase ,1 LowerCase, 1 Number and 1 Special Character");
            return;
        }
        else
        {
            progressDialog.setTitle("Please wait..");
            progressDialog.setMessage("Adding new user ....");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        FirebaseUser fUser=firebaseAuth.getCurrentUser();
                        fUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                editText_user_name.setText(null);
                                editText_user_password.setText(null);
                                editText_user_confirm_password.setText(null);
                                Toast.makeText(getApplicationContext(), "User Added successfully and a Verification email sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed to add a new user "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Failed to Register user "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });



        }







    }

    @Override
    public void add_police_station() {


        police_name_layout=findViewById(R.id.policestationLayout);
        police_phone_layout=findViewById(R.id.textInputLayout_police_phone);
        police_town_layout=findViewById(R.id.textInputLayout_police_town);

        editText_police_name=findViewById(R.id.police_station_name);
        editText_police_phone=findViewById(R.id.police_telephone_number);
        editText_police_town=findViewById(R.id.townLocated);
        String police_name=editText_police_name.getText().toString().toUpperCase().trim();

        String police_phone=editText_police_phone.getText().toString().toUpperCase().trim();
        String police_town=editText_police_town.getText().toString().toUpperCase().trim();

        if (TextUtils.isEmpty(police_name)){
            police_name_layout.setError("Please fill out this field");
            return;


        }
        else if( TextUtils.isEmpty(police_phone)){
            police_phone_layout.setError("Please fill out this field");
            return;

        }
        else if(TextUtils.isEmpty(police_town)){
            police_town_layout.setError("Please fill out this field");
            return;

        }
        else
        {   // store to firebase
            progressDialog.show();
            progressDialog.setTitle("Please Wait");
            progressDialog.setMessage("Saving ...");
            progressDialog.setCanceledOnTouchOutside(false);
            databaseReference=firebaseDatabase.getReference("Police");
            Police police=new Police(police_name,police_town,police_phone);
            databaseReference.child(police_phone).setValue(police).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    editText_police_name.setText(null);
                    editText_police_phone.setText(null);
                    editText_police_town.setText(null);
                    Toast.makeText(getApplicationContext(), "Police Added successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            
        }



    }

    @Override
    public void add_hospital() {

        hospital_name_layout=findViewById(R.id.txt_hospital_name_layout);
        hospital_town_layout=findViewById(R.id.txt_hospital_location_layout);
        hospital_phone_layout=findViewById(R.id.txt_hospital_phonenumber_layout);

        editText_hospital_name =findViewById(R.id.txt_hospital_name);
        editText_hospital_town=findViewById(R.id.txt_hospital_phone);
        editText_hospital_phone=findViewById(R.id.txt_hospital_phone);

        String hospital_name=editText_hospital_name.getText().toString().toUpperCase().trim();
        String hospital_town=editText_hospital_town.getText().toString().toUpperCase().trim();
        String hospital_phone=editText_hospital_phone.getText().toString().toUpperCase().trim();



        if(TextUtils.isEmpty(hospital_name)){
                hospital_name_layout.setError("Please fill out this field");
                return;

            }
        else if(TextUtils.isEmpty(hospital_town)){
            hospital_town_layout.setError("Please fill out this field");
            return;

        }
       else if(TextUtils.isEmpty(hospital_phone)){
            hospital_phone_layout.setError("Please fill out this field");
            return;

        }
        else{
            // Save Hospital Details to FireBase
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setMessage("Saving ...");

            databaseReference=firebaseDatabase.getReference("Hospital");

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(hospital_phone)){
                        Toast.makeText(getApplicationContext(), "A hospital with this contact exists", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
                        Hospital hospital=new Hospital(hospital_name,hospital_town,hospital_phone);
                        databaseReference.child(hospital_phone).setValue(hospital);

                        editText_hospital_name.setText(null);
                        editText_hospital_phone.setText(null);
                        editText_hospital_town.setText(null);
                        Toast.makeText(getApplicationContext(), "Hospital Details Added successfully", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });






        }





        }

        @Override
        public void load_home_fragment() {

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_Home_Fragment());
            fragmentTransaction.commit();

        }

        @Override
        public void load_add_fragment() {

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_Add_User_Fragment());
            fragmentTransaction.commit();


        }

        @Override
        public void load_victim_fragment() {

            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new admin_View_Victim() );
            fragmentTransaction.commit();

        }

        @Override
        public void logout_fragment() {

            AlertDialog.Builder alertDialogue=new AlertDialog.Builder(this);
            alertDialogue.setTitle("END SESSION ");
            alertDialogue.setMessage("Are you sure you want to logout?");
            alertDialogue.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // dismiss the dialogue box
                }
            });


            alertDialogue.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseAuth.getInstance().signOut();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(getApplicationContext(),Login.class));

                        }
                    },2000);
                }
            });
            alertDialogue.show(); }

        @Override
        public void load_view_users() {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new Admin_View_Registered_User() );
            fragmentTransaction.commit();

        }
    }