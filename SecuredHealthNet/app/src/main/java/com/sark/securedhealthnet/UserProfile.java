package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        String id=getIntent().getExtras().get("pos").toString();

        final int pos=Integer.parseInt(id);

//        final DatabaseReference reforig= FirebaseDatabase.getInstance().getReference("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("patients");
        final DatabaseReference reforig= FirebaseDatabase.getInstance().getReference("doctors").child("+918095030481").child("patients");

        reforig.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int j=0;

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    if(j==pos)
                    {
                      //madi
                        String phonenum= ds.getKey();

                        //Bravo! Now easy! display user profile!!
                        Toast.makeText(UserProfile.this, "You are user: "+ phonenum, Toast.LENGTH_SHORT).show();

                        break;
                    }

                    else
                        j++;

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
