package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        int pos=Integer.parseInt(getIntent().getStringExtra("pos"));



        switch(pos)
        {

        }


    }
}
