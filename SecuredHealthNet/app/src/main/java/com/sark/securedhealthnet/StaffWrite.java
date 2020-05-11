package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StaffWrite extends AppCompatActivity {
    Button nextButton;
    Button previousButton;
    private FirebaseAuth.AuthStateListener mauthStateListener;

    int titleStyle[] = {0,0,0};   //Indices:  Bold : 0   Italics : 1    TextStyle: 2
    int contentStyle[]= {0,0,0};  //Indices:  Bold : 0   Italics : 1    TextStyle: 2

    EditText titleEditText;
    int writingCount;
    EditText contentEditText;
    DatabaseReference init,contentWritingRef, titleRef, userWritingsRef,writingCountRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_staff_write);

        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);

        previousButton.setEnabled(false);

        titleEditText = (EditText)findViewById(R.id.titleEditText);
        contentEditText = (EditText) findViewById(R.id.contentEditText);

        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String id=System.currentTimeMillis()+"";
                //Send data to firebase
                contentWritingRef = FirebaseDatabase.getInstance().getReference("staff");
                try
                {
                    titleRef = contentWritingRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("report").child(id).child("title");

                    String sTitle = titleEditText.getText().toString().trim();
                    String content = contentEditText.getText().toString().trim();

                    if (sTitle.equals("") || content.equals(""))
                    {
                        Toast.makeText(StaffWrite.this,"Title or Content cannot be empty",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        titleRef.setValue(sTitle);
                        //id-> FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()
                        userWritingsRef = contentWritingRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("report").child(id).child("desc");
                        userWritingsRef.setValue(content);

                        Intent intent = new Intent(StaffWrite.this, StaffHome.class);
                        Toast.makeText(StaffWrite.this, "Report saved!", Toast.LENGTH_LONG).show();

                        startActivity(intent);
                    }
                }
                catch (Exception e)
                {
                    View parentlayout = findViewById(android.R.id.content);
                    Snackbar snackbar = Snackbar.make(parentlayout,"Can't process your request right now! Try again later.",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Intent intent = new Intent(StaffWrite.this, StaffHome.class);
                    startActivity(intent);
                }
            }
        });

    }
}



