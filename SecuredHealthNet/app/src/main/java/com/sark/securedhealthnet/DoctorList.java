package com.sark.securedhealthnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DoctorList extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    RecyclerView.LayoutManager layoutManager;
    private Boolean moving=true;
    TextView nettextdevelop;
    CountDownTimer countDownTimer;
    int timeValue = 5;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    ProgressBar progressBar;
    TextView loadtxt;
    String namedecrypt,descdecrypt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        progressBar=findViewById(R.id.progressbar);

        mDatabase = FirebaseDatabase.getInstance().getReference("doctors");
        mDatabase.keepSynced(true);

        nettextdevelop=findViewById(R.id.nettxtdevelop);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

//    private String decrypt(String data) throws Exception
//    {
//        SecretKeySpec key=generatekey(data);
//        Cipher c= Cipher.getInstance("AES");
//        c.init(Cipher.DECRYPT_MODE,key);
//        byte[] decodedValue= Base64.decode(data,Base64.DEFAULT);
//        byte[] decValue= c.doFinal(decodedValue);
//        String decryptedvalue= new String(decValue);
//
//        return decryptedvalue;
//    }
//
//    private SecretKeySpec generatekey(String data) throws Exception
//    {
//        final MessageDigest digest=MessageDigest.getInstance("SHA-256");
//        byte[] bytes=data.getBytes("UTF-8");
//        digest.update(bytes,0,bytes.length);
//
//        byte[] key=digest.digest();
//
//        SecretKeySpec secretKeySpec=new SecretKeySpec(key,"AES");
//
//        return secretKeySpec;
//    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog, DoctorList.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, DoctorList.BlogViewHolder>
                (Blog.class, R.layout.cardview, DoctorList.BlogViewHolder.class, mDatabase) {
            @Override
            protected void populateViewHolder(DoctorList.BlogViewHolder viewHolder, Blog model, int position) {
                String name=model.getName();
                String desc=model.getLocation();

//               try {
//                   namedecrypt = decrypt(name);
//                   descdecrypt = decrypt(desc);
//
//                   namedecrypt=AESUtils.decrypt(name);
//                   descdecrypt=AESUtils.decrypt(desc);
//
//               }
//               catch (Exception e)
//               {
//                   e.printStackTrace();
//                   Toast.makeText(DoctorList.this, "Exception occurred!", Toast.LENGTH_SHORT).show();
//               }
                viewHolder.setTitle(name);
                viewHolder.setDesc(desc);
            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        progressBar.setVisibility(View.INVISIBLE);

    }


    public static class BlogViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        View mview;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mview = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    builder.setMessage("Are you sure you want to send report to this doctor and request appointment?");
                    builder.setCancelable(false);

                    builder.setNegativeButton("Send", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            final int pos= getLayoutPosition();
                            Toast.makeText(v.getContext(), "Report sent! Please wait for Doctor to accept appointment!", Toast.LENGTH_SHORT).show();

                            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("doctors");

                            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {

                                    int j=0;
                                    String name="",area="", uphone="", desc="", title="";
                                    dbHelper db = new dbHelper(v.getContext());
                                    Cursor cursor = db.alldata();

                                    if (cursor.getCount() != 0) {
                                        while (cursor.moveToNext()) {
                                            name = cursor.getString(1);
                                            area=cursor.getString(2);
                                            uphone = cursor.getString(3);
                                            title = cursor.getString(4);
                                            desc = cursor.getString(5);

                                        }
                                    }

                                    for(DataSnapshot ds: dataSnapshot.getChildren())
                                    {
                                        if(j==pos)
                                        {
                                            String key=ds.getKey().toString();        //phone number of doctor selected
                                            String uid=uphone+"";
                                            DatabaseReference refsave=FirebaseDatabase.getInstance().getReference("doctors").child(key);
                                            final String date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault()).format(new Date());

                                            String ids=System.currentTimeMillis()+"";
                                            refsave.child("patients").child(uid).child("Name").setValue(name);
                                            refsave.child("patients").child(uid).child("Date").setValue(date);
//                                            refsave.child("patients").child(uid).child("Location").setValue(area);
//                                            refsave.child("patients").child(uid).child("Phone").setValue(uphone);
                                            refsave.child("patients").child(uid).child("Appointment").setValue(0);
                                            refsave.child("patients").child(uid).child("report").child(date).child("desc").setValue(desc);  //desc
                                            refsave.child("patients").child(uid).child("report").child(date).child("title").setValue(title);  //title


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
                    });

                    builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog alertdialog = builder.create();
                    alertdialog.show();

                }
            });
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mview.findViewById(R.id.item_title);
            post_title.setText("Dr. "+title);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) mview.findViewById(R.id.item_desc);
            post_desc.setText(desc);
        }

        @Override
        public void onClick(View v) {

        }

//        public void setImage(Context ctx, String image) {
//            ImageView post_Image = (ImageView                             )mview.findViewById(R.id.item_image);
//            Picasso.get().load(image).placeholder(R.drawable.icon).into(post_Image);
//        }
    }
}