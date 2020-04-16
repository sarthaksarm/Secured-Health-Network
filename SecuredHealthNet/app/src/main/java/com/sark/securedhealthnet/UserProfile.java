package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
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
    TextView nametxt,agetxt,locattxt;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    ProgressBar progressBar;
    FirebaseRecyclerAdapter<BlogReport, PatientsReports.BlogViewHolder> firebaseRecyclerAdapter;
    RadioGroup rg;
    RadioButton rbaccept,rbreject;
    String phone;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        recyclerView=findViewById(R.id.recycler_viewreport);
        gridLayoutManager=new GridLayoutManager(this,2,RecyclerView.HORIZONTAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        String id=getIntent().getExtras().get("pos").toString();

        final int pos=Integer.parseInt(id);
        setphone(pos);

//      final DatabaseReference reforig= FirebaseDatabase.getInstance().getReference("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("patients");
        final DatabaseReference reforig= FirebaseDatabase.getInstance().getReference("doctors").child("+918095030481").child("patients");


        nametxt=findViewById(R.id.nameedittxt);
        agetxt=findViewById(R.id.ageedittxt);
        locattxt=findViewById(R.id.locatedittxt);
        rg=findViewById(R.id.appointradiogrp);
        rbaccept=findViewById(R.id.setrdbtn);
        rbreject=findViewById(R.id.unsetrdbtn);

        rg.clearCheck();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton checkbtn = radioGroup.findViewById(i);
                boolean check = checkbtn.isChecked();
                int value=0;

                if (check) {

                    if(checkbtn.getText().equals("ACCEPT"))
                        value=1;

                    reforig.child(phone).child("Appointment Acceptance").setValue(value);

                    if(value==1)
                    Toast.makeText(UserProfile.this, "Accepted", Toast.LENGTH_SHORT).show();

                    else
                        Toast.makeText(UserProfile.this, "Rejected", Toast.LENGTH_SHORT).show();

                }
            }

        });

    }

    public void setphone(final int pos)
    {
        final DatabaseReference reforig= FirebaseDatabase.getInstance().getReference("doctors").child("+918095030481").child("patients");

        reforig.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int j=0;

                for(DataSnapshot ds: dataSnapshot.getChildren()) {
                    if (j == pos) {
                        //madi
                        String phonenum = ds.getKey();
                        phone=phonenum;

                        load(phone);
                        loadreports(phone);

                        break;
                    } else
                        j++;

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void loadreports(final String phone)
    {
        //        final DatabaseReference reforig=FirebaseDatabase.getInstance().getReference("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("patients");

        final DatabaseReference refpatient=FirebaseDatabase.getInstance().getReference("doctors").child("+918095030481").child("patients").child(phone).child("report");

                    try {
                        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogReport, PatientsReports.BlogViewHolder>
                                (BlogReport.class, R.layout.cardviewnotific, PatientsReports.BlogViewHolder.class, refpatient) {
                            @Override
                            protected void populateViewHolder(PatientsReports.BlogViewHolder viewHolder, BlogReport model, int position) {
                                viewHolder.setTitle(model.getTitle());
                                viewHolder.setDesc(model.getDesc());

                                // viewHolder.setImage(getApplicationContext(), model.getImage());
                            }
                        };

                    } catch (Exception e) {
                        //  Toast.makeText(this, "No reports received till now!", Toast.LENGTH_SHORT).show();
                    }

                recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public void load(String phone)
    {
        DatabaseReference refname=FirebaseDatabase.getInstance().getReference("users").child(phone).child("Name");

        refname.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.getValue().toString();
                nametxt.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference refage=FirebaseDatabase.getInstance().getReference("users").child(phone).child("Age");

        refage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String age=dataSnapshot.getValue().toString();
                agetxt.setText(age);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference refloc=FirebaseDatabase.getInstance().getReference("users").child(phone).child("Location");

        refloc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String loc=dataSnapshot.getValue().toString();
                locattxt.setText(loc);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
