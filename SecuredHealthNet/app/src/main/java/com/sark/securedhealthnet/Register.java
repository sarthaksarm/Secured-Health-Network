package com.sark.securedhealthnet;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Register extends AppCompatActivity {
    TextInputEditText name, age, area;
    Button register;
    DatabaseReference ref;
    RadioGroup rgrp;
    RadioButton rb1,rb2,rb3;
    RadioButton rbselected;
    String prof;

    String nameencypt,ageencrypt,phoneencrypt,areaencrypt,dateencrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final String phonenum= getIntent().getStringExtra("phone");

        name=findViewById(R.id.textInputEditTextName);
        age=findViewById(R.id.textInputEditTextAge);
        area=findViewById(R.id.textInputEditTextArea);

        register=findViewById(R.id.appCompatButtonRegister);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupfn(phonenum);
            }
        });

        rgrp=findViewById(R.id.radiogrp);

        rb1=findViewById(R.id.rbbtnhome1);
        rb2=findViewById(R.id.rbbtnhome2);
        rb3=findViewById(R.id.rbbtnhome3);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
        builder.setMessage("Do you want to abort One-Time  Registration process ?");
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Surely!",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }
        );

        builder.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }
        );

        AlertDialog alert1=builder.create();
        alert1.show();
    }
//Encryption, Decryption Logic


    private String encrypt(String data) throws Exception
    {
        SecretKeySpec key= generatekey(data);
        Cipher c=Cipher.getInstance("AES");
        c.init(Cipher.ENCRYPT_MODE,key);

        byte[] encVal=c.doFinal(data.getBytes());
        String encryptedValue= Base64.encodeToString(encVal,Base64.DEFAULT);

        return encryptedValue;
    }

    private SecretKeySpec generatekey(String data) throws Exception
    {
        final MessageDigest digest=MessageDigest.getInstance("SHA-256");
        byte[] bytes=data.getBytes("UTF-8");
        digest.update(bytes,0,bytes.length);

        byte[] key=digest.digest();

        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");

        return secretKeySpec;
    }







    private void signupfn(String phonenum) {
        String nameet = name.getText().toString()+"";
        String ageet = age.getText().toString()+"";
        String areaet = area.getText().toString()+"";

        boolean checkerror = false;

        if(nameet.equals("")&&ageet.equals("")&&areaet.equals("")){
            checkerror = true;
            View parentLayout = findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar.make(parentLayout,"You have not entered any data. Please fill form", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

        else {
            if (nameet.equals("")) {
                checkerror = true;
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentLayout,"Name can't be empty",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else if (ageet.equals("")) {
                checkerror = true;
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentLayout,"Age can't be empty",Snackbar.LENGTH_LONG);
                snackbar.show();
            }

            else if (areaet.equals("")) {
                checkerror = true;
                View parentLayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentLayout,"Area/Location can't be empty",Snackbar.LENGTH_LONG);
                snackbar.show();
            }
            else
            {
                int rgsid= rgrp.getCheckedRadioButtonId();

                rbselected=(RadioButton)findViewById(rgsid);

                if(rbselected==rb1)
                {
                   prof="users";
                }
                else if(rbselected==rb2){
                   prof="doctors";
                }
                else if(rbselected==rb3){
                    prof="staff";
                }
                else
                {
                    Toast.makeText(Register.this,"Please select your profession!",Toast.LENGTH_SHORT).show();
                    checkerror = true;
                }

            }
        }
        if(!checkerror) {
            //save data to firebase and sqlite
            User user=new User();
            dbHelper db=new dbHelper(this);
            SQLiteDatabase db1;


           try {
               Toast.makeText(Register.this,"Profession= "+prof,Toast.LENGTH_SHORT).show();

               ref= FirebaseDatabase.getInstance().getReference(prof);

               String id = phonenum;
               final String date = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(new Date());


               try
               {
//                        nameencypt=encrypt(nameet);
//                        ageencrypt=encrypt(ageet);
//                        dateencrypt=encrypt(date);
//                        phoneencrypt=encrypt(phonenum);
//                        areaencrypt=encrypt(areaet);
                   nameencypt=AESUtils.encrypt(nameet);
                   ageencrypt=AESUtils.encrypt(ageet);
                   dateencrypt=AESUtils.encrypt(date);
                   phoneencrypt=AESUtils.encrypt(phonenum);
                   areaencrypt=AESUtils.encrypt(areaet);


               }
               catch (Exception e)
               {
                   e.printStackTrace();
               }


               ref.child(id).child("Name").setValue(nameencypt);
               ref.child(id).child("Age").setValue(ageencrypt);
               ref.child(id).child("Location").setValue(areaencrypt);
               ref.child(id).child("Phone").setValue(phoneencrypt);
               ref.child(id).child("Date").setValue(dateencrypt);
               ref.child(id).child("registered").setValue("0");

               View parentLayout = findViewById(android.R.id.content);
               Snackbar snackbar = Snackbar.make(parentLayout,"Registered successfully!",Snackbar.LENGTH_LONG);
               snackbar.show();

               SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putString("prof= ",prof);

               editor.apply();

                       if(prof.equals("doctors"))
                       {
                           Intent i = new Intent(Register.this, DoctorHome.class);
                           startActivity(i);

                       }
                       else if(prof.equals("users"))
                       {
                           Intent i = new Intent(Register.this, MainActivity.class);
                           startActivity(i);

                       }
                       else
                       {
                           Intent i = new Intent(Register.this, MainActivity.class); //StaffActivity
                           startActivity(i);

                       }

                user.setName(nameet);
                user.setEmail(prof);
                user.setArea(areaet);
                user.setPhone(phonenum);
                user.setWritings(0);

                db.addUser(user);

           }
           catch(Exception e)
           {
               View parentLayout = findViewById(android.R.id.content);
               Snackbar snackbar = Snackbar.make(parentLayout,"Server side error! Try again in some time",Snackbar.LENGTH_LONG);
               snackbar.show();
               Intent i = new Intent(Register.this, PhoneLogin.class);
               startActivity(i);
           }
            finish();
        }
        }
    }