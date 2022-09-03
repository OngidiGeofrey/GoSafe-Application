package com.ongidideveloper.gosafe;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class DashBoard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentHome.onfragmentBtnSelected,FragmentEmergence.onFragmentEmergenceButtonClicked,
        FragmentProfile.onFragmentProfileButtonClicked
{
    ActionBarDrawerToggle actionBarDrawerToggle;
     NavigationView navigationView;
     FragmentManager fragmentManager;
     FragmentTransaction fragmentTransaction;
     LocationManager locationManager;
     FusedLocationProviderClient fusedLocationProviderClient;
     ProgressDialog progressDialog;



     DrawerLayout drawerLayout;
     int RECORD_AUDIO=0;
    //LongOperation recordAudioSync=null;

    double longitude;
    double latitude;
    String country_name;
    String country_admin_name;
    String link;
    String current_user;
    String address_line;
    String number;
    String police_town;

    String hospital_town;


    Button stop;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ImageView mic;
  /*  LongOperation recordAudioSyc;*/
    MediaRecorder recorder;


    EditText profileName,guardianName,profilePhone,guardianPhone;

    String currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        Toolbar toolbar = findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);
         drawerLayout = findViewById(R.id.drawer);

         navigationView=findViewById(R.id.navigationview);
         navigationView.setNavigationItemSelectedListener(this);
         mic=findViewById(R.id.imageView2);
         stop=findViewById(R.id.btnStop);


         actionBarDrawerToggle=new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.open,R.string.close);
         drawerLayout.addDrawerListener(actionBarDrawerToggle);
         actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
         actionBarDrawerToggle.syncState();

         //  initialize fused components
         fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);


         // default Fragment
         fragmentManager=getSupportFragmentManager();
         fragmentTransaction=fragmentManager.beginTransaction();
         fragmentTransaction.add(R.id.container_fragment,new FragmentHome());
         fragmentTransaction.commit();

         firebaseAuth=FirebaseAuth.getInstance();

         rootNode=FirebaseDatabase.getInstance();
         progressDialog=new ProgressDialog(this);

        // request permission to send and receive sms
        ActivityCompat.requestPermissions(DashBoard.this, new String[]{Manifest.permission.READ_SMS,Manifest.permission.SEND_SMS},PackageManager.PERMISSION_GRANTED);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        // check which Item is selected
        drawerLayout.closeDrawer(GravityCompat.START);
        if(item.getItemId()==R.id.home)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new FragmentHome());
            fragmentTransaction.commit();
        }

       else if(item.getItemId()==R.id.profile)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new FragmentProfile());
            fragmentTransaction.commit();
        }
        else if(item.getItemId()==R.id.emergence)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new FragmentEmergence());
            fragmentTransaction.commit();
        }
       else  if(item.getItemId()==R.id.reports)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new FragmentReport());
            fragmentTransaction.commit();
        }
      else  if(item.getItemId()==R.id.share)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            //fragmentTransaction.replace(R.id.container_fragment,new FragmentShare());
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String body="Download this App";
            String sub="https/play.google.com/Gosafe";
            intent.putExtra(Intent.EXTRA_TEXT,body);
            intent.putExtra(Intent.EXTRA_TEXT,sub);
            startActivity(Intent.createChooser(intent,"share using"));

            fragmentTransaction.commit();

        }
      else  if(item.getItemId()==R.id.help)
        {
            fragmentManager=getSupportFragmentManager();
            fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment,new FragmentHelp());
            fragmentTransaction.commit();

        }
        else  if(item.getItemId()==R.id.logout){

            AlertDialog.Builder alert=new AlertDialog.Builder(this);
            alert.setTitle("Are you sure You want to log out?");
            alert.setPositiveButton("YES", (dialog, which) -> {
                try {
           firebaseAuth.getInstance().signOut();
            Toast.makeText(getApplicationContext(), "Logged Out successfully", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(() -> startActivity(new Intent(getApplicationContext(),Login.class)),3000);
            startActivity(new Intent(getApplicationContext(),Login.class));

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Error Occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

            });
            alert.setNegativeButton("NO", (dialog, which) -> {

                //cancel log out

            });
            alert.create().show();




        }
        else
        {

            // no code here
        }
        return true;
    }
    @Override
    public void record_method()
    {

        if(ActivityCompat.checkSelfPermission(DashBoard.this, Manifest.permission.RECORD_AUDIO ) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(DashBoard.this, new String[]{Manifest.permission.RECORD_AUDIO}, RECORD_AUDIO);
        }
        else{
           // recording here

            recorder=new MediaRecorder();
            recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            recorder.setOutputFile("/data/data/" + getPackageName()+ "/music.3gp");
            int startAmplitude=0;
            int finishAmplitude;
            int amplitudeThreshold=18000;
            try{
                if(recorder==null) {
                    recorder.prepare();
                    recorder.start();
                    startAmplitude=recorder.getMaxAmplitude();
                    Toast.makeText(getApplicationContext(), ""+startAmplitude, Toast.LENGTH_SHORT).show();




                }




            } catch (IOException e) {
                e.printStackTrace();
            }








        }



    }

    @Override
    public void stop_recording() {

        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Are You sure you what to cancel recording?");
        alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(recorder!=null){
                    recorder.release();
                    recorder.stop();


                }


               // listener.stop_recording();
            }
        });

        alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // cancels the alert dialogue

            }
        });
        alertDialogBuilder.show();


    }

    @SuppressLint("MissingPermission")
    @Override
    public void getLocation() {
       // Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();

      if(ActivityCompat.checkSelfPermission(DashBoard.this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
      {
          // when permission is granted
          fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
              @Override
              public void onComplete(@NonNull Task<Location> task) {
                  // initialize location
                  Location location=task.getResult();
                  if(location!=null)
                  {


                      try {
                          //initialise decoder
                          Geocoder geocoder=new Geocoder(DashBoard.this,
                                  Locale.getDefault());

                          //initialize list
                          List<Address> addresses=geocoder.getFromLocation(
                                  location.getLatitude(),location.getLongitude(),1
                          );

                          //set latitude and Longitude

                           longitude=addresses.get(0).getLongitude();
                           latitude=addresses.get(0).getLatitude();
                           country_name=addresses.get(0).getCountryName();
                           country_admin_name=addresses.get(0).getAdminArea();
                          address_line=addresses.get(0).getAddressLine(0);
                          link="Latitude "+addresses.get(0).getLatitude()+"" +
                                  "\nLongitude :"+addresses.get(0).getLongitude()+"\n Country : "+addresses.get(0).getCountryName()
                                  +"\n Address :"+addresses.get(0).getAdminArea()+", "+addresses.get(0).getAddressLine(0)+" https://maps.google.com/?q= "+addresses.get(0).getLatitude()+","+addresses.get(0).getLongitude();
                           current_user=firebaseAuth.getCurrentUser().getEmail();

                        AlertDialog.Builder message_dialoq =new AlertDialog.Builder(DashBoard.this);
                        message_dialoq.setTitle("Emergency Notification");
                        message_dialoq.setMessage("Are you really in trouble send alert to your primary contacts?");
                        message_dialoq.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // dismiss


                            }
                        });

                        message_dialoq.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {



                                // Send information to guardian Contacts
                                reference=rootNode.getReference("Guardian");
                             reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                                            GuardianPojo guardianPojo=dataSnapshot.getValue(GuardianPojo.class);
                                         if(current_user.equalsIgnoreCase(guardianPojo.getPersonEmail())){

                                                number=guardianPojo.getGuardianPhone();

                                                // send sms

                                                try {

                                                  SmsManager smsManager=SmsManager.getDefault();
                                                    smsManager.sendTextMessage(number,null,""+link,null,null);
                                                    Toast.makeText(getApplicationContext(), "Notification message sent to Guardian Successfully\n sending to nearest police station", Toast.LENGTH_SHORT).show();

                                                }
                                                catch (Exception e){
                                                    Toast.makeText(getApplicationContext(), "Not sent "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }


                                            }



                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(getApplicationContext(), "Error occured", Toast.LENGTH_SHORT).show();

                                    }
                                });

                                // Send information to nearest Police stations

                                reference=rootNode.getReference("Police");

                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                            Police police=dataSnapshot.getValue(Police.class);

                                            police_town=police.getLocation();
                                            String[] str=police_town.split(" ");
                                            String[] county=country_admin_name.split(" ");
                                            number=police.getEmergency_contacts();

                                           if(county[0].equalsIgnoreCase(str[0]))
                                            {
                                               // send sms

                                                String police_text="It seems an Accident has occured around following location\n";

                                                String action="Please take an immediate action to rescue the victims \n. Regards\n\n Gosafe App";



                                                SmsManager smsManager=SmsManager.getDefault();
                                                smsManager.sendTextMessage(number,null,""+link,null,null);
                                                Toast.makeText(getApplicationContext(), "Notification message sent to nearest police station \n sending to nearest Hospital", Toast.LENGTH_SHORT).show();

                                            }

                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });



                                // send to nearest Hospitals

                                reference=rootNode.getReference("Hospital");
                                reference.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                          Hospital hospital=dataSnapshot.getValue(Hospital.class);

                                           hospital_town=hospital.getLocation();
                                            String[] str=hospital_town.split(" ");
                                            String[] county=country_admin_name.split(" ");
                                            number=hospital.getEmergency_contacts();

                                          if(county[0].equalsIgnoreCase(str[0]))
                                            {
                                                // send sms

                                                String police_text="It seems an Accident has occured around following location\n";

                                                String action="Please take an immediate action to rescue the victims \n. Regards\n\n Gosafe App";
                                                SmsManager smsManager=SmsManager.getDefault();
                                                smsManager.sendTextMessage(number,null,""+link,null,null);
                                                Toast.makeText(getApplicationContext(), "Notification message sent to nearest Hospital \n sending to nearest Hospital", Toast.LENGTH_SHORT).show();

                                            }

                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                                // save details of victim and the location to firebase
                                reference=rootNode.getReference("Victim");



                                Victim victim=new Victim(latitude,longitude,link,address_line,country_admin_name);

                                reference.child(country_name).setValue(victim).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error occurred "+e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });















                            }
                        });
                        message_dialoq.show();



                      } catch (IOException e) {
                          e.printStackTrace();
                      }


                  }
              }
          });
      }
      else
      {
          ActivityCompat.requestPermissions(DashBoard.this
          ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},44);
      }



    }

    @Override
    public void saveGuardian() {
        // Save guardian Details to FireBase

        guardianName=findViewById(R.id.guardianName);
        guardianPhone=findViewById(R.id.guadianPhoneNumber);

        String phone=guardianPhone.getText().toString().trim();
        String name=guardianName.getText().toString().toUpperCase().trim();
        currentUser=firebaseAuth.getCurrentUser().getEmail();

        if (TextUtils.isEmpty(phone))
        {
            guardianPhone.setError("Guardian contact number is required");
            return;

        }
        else if (TextUtils.isEmpty(name))
        {
            guardianName.setError("Guardian name is required");
            return;
        }
        else
        {
            // Authenticate a phone number
            //save to firebase
            progressDialog.setMessage("Saving ...");
            progressDialog.show();
            progressDialog.setCanceledOnTouchOutside(false);

            reference=rootNode.getReference("Guardian");
            GuardianPojo guardianPojo=new GuardianPojo(name,phone,currentUser);
            reference.child(phone).setValue(guardianPojo).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), " Data not saved. "+e.getMessage() , Toast.LENGTH_SHORT).show();
                }
            });




        }











           }

    @Override
    public void saveProfile() {
        profileName=findViewById(R.id.personName);
        profilePhone=findViewById(R.id.txtNumber);
        String phone=profilePhone.getText().toString().trim();
        String name=profileName.getText().toString().toUpperCase().trim();
        if(TextUtils.isEmpty(phone))
        {
            profilePhone.setError("Your mobile number is required");
            return;
        }
        else if(TextUtils.isEmpty(name))
        {
            profileName.setError("Enter the name to be associated with a phone Number");
        }
        else if(!(android.util.Patterns.PHONE.matcher(phone).matches()) && phone.length()!=10)
        {
            profilePhone.setError("please provide a valid phone number");
            return;

        }
        else {

           // currentUser =firebaseAuth.getCurrentUser().getUid();
            progressDialog.setMessage(" Saving ...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            currentUser=firebaseAuth.getCurrentUser().getEmail();
           reference=rootNode.getReference("userContact");
            PojoContact profileContact=new PojoContact(name,phone,currentUser);

            reference.child(phone).setValue(profileContact).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Saved Successfully", Toast.LENGTH_SHORT).show();
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
    protected void onDestroy() {
        super.onDestroy();

    }

    private void done()
    {

        if(recorder != null){
            recorder.stop();
            recorder.release();
        }

    }

    private void waitSome()
    {
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onPause() {
        super.onPause();
    }




}