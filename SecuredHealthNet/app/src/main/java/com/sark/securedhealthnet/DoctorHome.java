package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class DoctorHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);


    }

    public void onClickpatients(View view)
    {
        Intent i;
        i = new Intent(this, PatientsReports.class);
        startActivity(i);

    }

    public void onClickremind(View view)
    {
        Intent i;
        i = new Intent(this, reminder.class);
        startActivity(i);

    }

    public void onClickchat(View view)
    {
        Intent i;
        i = new Intent(this, ChatMain.class);
        startActivity(i);

    }
    public void onClickabout(View view)
    {
        Intent i;
        i = new Intent(this, about.class);
        startActivity(i);

    }

}