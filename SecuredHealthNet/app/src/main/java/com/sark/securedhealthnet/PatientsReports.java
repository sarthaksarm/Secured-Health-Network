package com.sark.securedhealthnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PatientsReports extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mauthStateListener;
    ListView listView;
    DatabaseReference reference;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    FirebaseRecyclerAdapter<BlogPatientPro, BlogViewHolder> firebaseRecyclerAdapter;
    public static int positionclicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_reports);
        recyclerView=findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,2,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

//      reference= FirebaseDatabase.getInstance().getReference("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("report");
//      final DatabaseReference reforig=FirebaseDatabase.getInstance().getReference("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("patients");

        final DatabaseReference reforig = FirebaseDatabase.getInstance().getReference("doctors").child("+918095030481").child("patients");

                    try {
                        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogPatientPro, PatientsReports.BlogViewHolder>
                                (BlogPatientPro.class, R.layout.cardviewnotific, PatientsReports.BlogViewHolder.class, reforig) {
                            @Override
                            protected void populateViewHolder(PatientsReports.BlogViewHolder viewHolder, BlogPatientPro model, int position) {
                                viewHolder.setTitle(model.getName());
                                viewHolder.setDesc(model.getLocation());

                                // viewHolder.setImage(getApplicationContext(), model.getImage());
                            }

                            @Override
                            public void onBindViewHolder(BlogViewHolder viewHolder, final int position) {
                                super.onBindViewHolder(viewHolder, position);
                                    positionclicked=viewHolder.getAdapterPosition();

                                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            try {
                                                Intent i = new Intent(PatientsReports.this,UserProfile.class);
                                                i.putExtra("pos",position);
                                                Toast.makeText(PatientsReports.this, "Position= "+position, Toast.LENGTH_SHORT).show();
                                                startActivity(i);

                                                //open users profile page
                                            }
                                            catch (Exception e)
                                            {
                                                View parentlayout = view.findViewById(android.R.id.content);
                                                Snackbar snackbar = Snackbar.make(parentlayout,"Try again after sometime!", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        }
                                    });
                                }
                        };
                    }
                    catch (DatabaseException e) {
                        Intent i=new Intent(PatientsReports.this,MainActivity.class);
                        Log.e("EEEE",e+"");
                        try{
                            Thread.sleep(2000);
                        }
                        catch (InterruptedException e1)
                        {
                            Log.e("AAAA",e1+"");

                        }
                        startActivity(i);

                    }
                    recyclerView.setAdapter(firebaseRecyclerAdapter);

                }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
////        final DatabaseReference reforig=FirebaseDatabase.getInstance().getReference("doctors").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("patients");
//        final DatabaseReference reforig=FirebaseDatabase.getInstance().getReference("doctors").child("+918095030481").child("patients");
//
//        reforig.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                for(DataSnapshot ds: dataSnapshot.getChildren())
//                {
//                    String userno= ds.getKey();
//                    reference= reforig.child(userno).child("report");
//                    DatabaseReference refpatient = FirebaseDatabase.getInstance().getReference("users").child(userno);
//
//                    try {
//                        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogPatientPro, PatientsReports.BlogViewHolder>
//                                (BlogPatientPro.class, R.layout.cardviewnotific, PatientsReports.BlogViewHolder.class, refpatient) {
//                            @Override
//                            protected void populateViewHolder(PatientsReports.BlogViewHolder viewHolder, BlogPatientPro model, int position) {
//                                viewHolder.setTitle(model.getName());
//                                viewHolder.setDesc(model.getLocation());
//
//                                // viewHolder.setImage(getApplicationContext(), model.getImage());
//                            }
//

//                        };
//                    }
//                    catch (Exception e) {
//                        Intent i=new Intent(PatientsReports.this,MainActivity.class);
//                        startActivity(i);
//
//                        Toast.makeText(PatientsReports.this, "Exception: "+e, Toast.LENGTH_SHORT).show();
//                    }
//                }
//                try{
//                    recyclerView.setAdapter(firebaseRecyclerAdapter);
//                }
//                catch (Exception e)
//                {
//                    Intent i=new Intent(PatientsReports.this,MainActivity.class);
//                    startActivity(i);
//
//                    Toast.makeText(PatientsReports.this, "Exception1: "+e, Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public BlogViewHolder(final View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mview.findViewById(R.id.item_title);
            post_title.setText(title);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) mview.findViewById(R.id.item_desc);
            post_desc.setText(desc);
        }
    }
}