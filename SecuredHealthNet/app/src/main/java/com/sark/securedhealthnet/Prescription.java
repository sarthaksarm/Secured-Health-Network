package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Prescription extends AppCompatActivity {
String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presscription);

        id=getIntent().getExtras().get("pos").toString();





    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Prescription.this,UserProfile.class);
        i.putExtra("pos",id);
        startActivity(i);
    }
}
