package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
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

public class UserPrescription extends AppCompatActivity {
    TextView nametxt,agetxt,locattxt,phonenumtxt,presheadtxt,presdettxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_prescription);

        nametxt=findViewById(R.id.nameedittxt);
        agetxt=findViewById(R.id.ageedittxt);
        locattxt=findViewById(R.id.locatedittxt);
        phonenumtxt=findViewById(R.id.phonedittxt);
        presheadtxt=findViewById(R.id.prescripheadtxt);
        presdettxt=findViewById(R.id.prescripdetailstxt);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        try{
            if(ref.child("prescription")==null)
            {
                //set NULL
                nametxt.setText("NIL");
                agetxt.setText("NIL");
                locattxt.setText("NIL");
                phonenumtxt.setText("NIL");
                presheadtxt.setText("No prescription at this stage! Try again later!");
                presdettxt.setText("");

            }
            else
            {
                //set values

                DatabaseReference ref1=ref.child("prescription").child("DocID");


                ref1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String docID=dataSnapshot.getValue().toString();
                        phonenumtxt.setText(docID);

                        load(docID);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference ref2=ref.child("prescription").child("highlights");
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String high=dataSnapshot.getValue().toString();
                        presheadtxt.setText(high);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                DatabaseReference ref3=ref.child("prescription").child("details");
                ref3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String con=dataSnapshot.getValue().toString();
                        presdettxt.setText(con);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }
        }
        catch (Exception e)
        {
            Intent i=new Intent(UserPrescription.this,MainActivity.class);
            Toast.makeText(this, "No prescription at this stage! Try again later!", Toast.LENGTH_LONG).show();
        }

    }
    public void load(String docid)
    {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("doctors").child(docid);


        ref.child("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String con=dataSnapshot.getValue().toString();
                nametxt.setText(con);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("Age").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String con=dataSnapshot.getValue().toString();
                agetxt.setText(con);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ref.child("Location").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String con=dataSnapshot.getValue().toString();
                locattxt.setText(con);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
