package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Prescription extends AppCompatActivity {
String phone;
    Button nextButton;
    Button previousButton;
    private FirebaseAuth.AuthStateListener mauthStateListener;
    EditText titleEditText;
    int writingCount;
    EditText contentEditText;
    DatabaseReference init,contentWritingRef, titleRef, userWritingsRef,writingCountRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presscription);

        phone=getIntent().getExtras().get("pos").toString();

        nextButton = (Button) findViewById(R.id.nextButton);

        titleEditText = (EditText)findViewById(R.id.titleEditText);
        contentEditText = (EditText) findViewById(R.id.contentEditText);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=System.currentTimeMillis()+"";
                //Send data to firebase
                contentWritingRef = FirebaseDatabase.getInstance().getReference("users");
                try
                {
                    titleRef = contentWritingRef.child(phone).child("prescription");
                    String sTitle = titleEditText.getText().toString().trim();
                    String content = contentEditText.getText().toString().trim();

                    if (sTitle.equals("") || content.equals(""))
                    {
                        Toast.makeText(Prescription.this,"Title or Content cannot be empty",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        titleRef.child("DocID").setValue(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());
                        titleRef.child("highlights").setValue(sTitle);
                        titleRef.child("details").setValue(content);

                        Intent intent = new Intent(Prescription.this, MainActivity.class);
                        Toast.makeText(Prescription.this, "Prescription Sent successfully!", Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                }

                catch (Exception e)
                {
                    View parentlayout = findViewById(android.R.id.content);
                    Snackbar snackbar = Snackbar.make(parentlayout,"Can't process your request right now! Try again later.",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Intent intent = new Intent(Prescription.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Prescription.this,DoctorHome.class);
        startActivity(i);
    }
}
