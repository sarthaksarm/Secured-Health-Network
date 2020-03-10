package com.sark.securedhealthnet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class WritingActivity extends AppCompatActivity {
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_writing);

        nextButton = (Button) findViewById(R.id.nextButton);
        previousButton = (Button) findViewById(R.id.previousButton);

        previousButton.setEnabled(false);

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
                    titleRef = contentWritingRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("report").child(id).child("title");

                String sTitle = titleEditText.getText().toString().trim();
                String content = contentEditText.getText().toString().trim();

                if (sTitle.equals("") || content.equals(""))
                {
                    Toast.makeText(WritingActivity.this,"Title or Content cannot be empty",Toast.LENGTH_LONG).show();
                }
                else
                {
                    titleRef.setValue(sTitle);
                            //id-> FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()
                    userWritingsRef = contentWritingRef.child(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber()).child("report").child(id).child("desc");
                    userWritingsRef.setValue(content);

                    //save in local db(phone)
                    User user=new User();
                    dbHelper db=new dbHelper(v.getContext());
                    Cursor cursor=db.alldata();
                    String area="",name="", phone="";

                    if(cursor.getCount()!=0)
                    {
                        while(cursor.moveToNext())
                        {
                            name=cursor.getString(1);
                            area=cursor.getString(2);
                            phone=cursor.getString(3);
                        }
                    }

                    user.setPhone(phone);
                    user.setName(name);
                    user.setArea(area);
                    user.setTitle(sTitle);
                    user.setDesc(content);

                     db.addUser(user);

                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    Toast.makeText(WritingActivity.this, "Report saved!", Toast.LENGTH_LONG).show();


                    startActivity(intent);

                }
                }
                catch (Exception e)
                {
                    View parentlayout = findViewById(android.R.id.content);
                    Snackbar snackbar = Snackbar.make(parentlayout,"Can't process your request right now! Try again later.",Snackbar.LENGTH_LONG);
                    snackbar.show();
                    Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        titleEditText = (EditText)findViewById(R.id.titleEditText);
        contentEditText =  findViewById(R.id.contentEditText) ;
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(WritingActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}



