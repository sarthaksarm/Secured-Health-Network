package com.sark.securedhealthnet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
  /*  public void decrypt(View view)
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("Name");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.getValue().toString();
                String decryptedname="";

                try{
                   decryptedname =AESUtils.decrypt(name);
                    Toast.makeText(MainActivity.this, "Decrypted Name= "+decryptedname, Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Exception!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
   */


    public void onClick1(View view)
    {
        Intent i;
        i = new Intent(this, WritingActivity.class);
        startActivity(i);
    }

    public void onClick2(View view)
    {
        Intent i;
        i = new Intent(this, MapsActivity.class);
        startActivity(i);
    }

    public void onClick3(View view)
    {
        Intent i;
        i = new Intent(this, DoctorList.class);
        startActivity(i);
    }

    public void onClick4(View view)
    {
        Intent i;
        i = new Intent(this, UserPrescription.class);
        startActivity(i);
    }

    public void onClick5(View view)
    {
        Intent i;
        i = new Intent(this, reminder.class);
        startActivity(i);
    }

    public void onClick6(View view)
    {
        Intent i;
        i = new Intent(this, ChatMain.class);
        startActivity(i);
    }

}

