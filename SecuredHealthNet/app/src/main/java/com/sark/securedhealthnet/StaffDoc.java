package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaffDoc extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mauthStateListener;
    ListView listView;
    DatabaseReference reference;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    ProgressBar progressBar;
    FirebaseRecyclerAdapter<Blog, PatientsReports.BlogViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_doc);

        recyclerView=findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager =new GridLayoutManager(this,1,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        final DatabaseReference reforig = FirebaseDatabase.getInstance().getReference("doctors");

        try {
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, PatientsReports.BlogViewHolder>
                    (Blog.class, R.layout.cardviewnotific, PatientsReports.BlogViewHolder.class, reforig) {
                @Override
                protected void populateViewHolder(PatientsReports.BlogViewHolder viewHolder, Blog model, int position) {
                    viewHolder.setTitle("Name: Dr. "+model.getName());
                    viewHolder.setDesc("Area/Locality: "+model.getLocation());
                    viewHolder.setDate("Registration Time: "+model.getDate());

                }

//                @Override
//                public void onBindViewHolder(PatientsReports.BlogViewHolder viewHolder, final int position) {
//                    super.onBindViewHolder(viewHolder, position);
//                    positionclicked=viewHolder.getAdapterPosition();
//
//                    viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                            try {
//                                Intent i = new Intent(PatientsReports.this,UserProfile.class);
//                                i.putExtra("pos",position);
//                                Toast.makeText(PatientsReports.this, "Position= "+position, Toast.LENGTH_SHORT).show();
//                                startActivity(i);
//
//                                //open users profile page
//                            }
//                            catch (Exception e)
//                            {
//                                View parentlayout = view.findViewById(android.R.id.content);
//                                Snackbar snackbar = Snackbar.make(parentlayout,"Try again after sometime!", Snackbar.LENGTH_LONG);
//                                snackbar.show();
//                            }
//                        }
//                    });
//                }
            };
        }
        catch (DatabaseException e) {

            Intent i=new Intent(StaffDoc.this,StaffDoc.class);

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

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public BlogViewHolder(final View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setDate(String Date)
        {
            TextView date=mview.findViewById(R.id.item_date);
            date.setText(Date);
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

