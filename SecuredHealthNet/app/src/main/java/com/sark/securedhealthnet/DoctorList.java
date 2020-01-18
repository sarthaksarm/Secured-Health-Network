package com.sark.securedhealthnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

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
////                   namedecrypt = decrypt(name);
////                   descdecrypt = decrypt(desc);
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


    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mview;

        public BlogViewHolder(View itemView) {
            super(itemView);
            mview = itemView;
        }

        public void setTitle(String title) {
            TextView post_title = (TextView) mview.findViewById(R.id.item_title);
            post_title.setText("Dr. "+title);
        }

        public void setDesc(String desc) {
            TextView post_desc = (TextView) mview.findViewById(R.id.item_desc);
            post_desc.setText(desc);
        }

//        public void setImage(Context ctx, String image) {
//            ImageView post_Image = (ImageView)mview.findViewById(R.id.item_image);
//            Picasso.get().load(image).placeholder(R.drawable.icon).into(post_Image);
//        }
    }
}