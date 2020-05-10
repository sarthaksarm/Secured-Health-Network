package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class StaffHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);




    }


    public void onClickreport(View view)
    {
        Intent i=new Intent(StaffHome.this,WritingActivity.class);
        startActivity(i);
    }
    public void onClickpat(View view)
    {

    }
    public void onClickdoc(View view)
    {

    }
    public void onClickrem(View view)
    {
        Intent i=new Intent(StaffHome.this,reminder.class);
        startActivity(i);
    }
    public void onClickch(View view)
    {
        Intent i=new Intent(StaffHome.this,ChatMain.class);
        startActivity(i);
    }
    public void onClickab(View view)
    {
        Intent i=new Intent(StaffHome.this,about.class);
        startActivity(i);
    }
}
