package com.sark.securedhealthnet;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

public class PhoneLogin extends AppCompatActivity {
    private static final String TAG = "PhoneLogin";
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    TextView t1,t2;
    ImageView i1;
    EditText e1,e2;
    Button b1,b2;
    ProgressBar progressVerify;
    CountryCodePicker ccp;
    String number;
    String link="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

        e1 = (EditText) findViewById(R.id.Phonenoedittext);
        b1 = (Button) findViewById(R.id.PhoneVerify);
        t1 = (TextView) findViewById(R.id.textView2Phone);

        progressVerify=findViewById(R.id.progressVerify);
        e2 = (EditText) findViewById(R.id.OTPeditText);
        b2 = (Button) findViewById(R.id.OTPVERIFY);
        t2 = (TextView) findViewById(R.id.textViewVerified);
        mAuth = FirebaseAuth.getInstance();
        progressVerify.setVisibility(View.INVISIBLE);

        ccp=(CountryCodePicker)findViewById(R.id.countrycodepicker);
        ccp.registerCarrierNumberEditText(e1);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                mVerificationInProgress = false;
                View parentlayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentlayout,"Verification completed!",Snackbar.LENGTH_LONG);
                snackbar.show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // Log.w(TAG, "onVerificationFailed", e);
                progressVerify.setVisibility(View.INVISIBLE);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    View parentlayout2 = findViewById(android.R.id.content);
                    Snackbar snackbar2 = Snackbar.make(parentlayout2,"Invalid phone number!",Snackbar.LENGTH_LONG);
                    snackbar2.show();                }
                else if(e instanceof FirebaseTooManyRequestsException) {
                    View parentlayout3 = findViewById(android.R.id.content);
                    Snackbar snackbar3 = Snackbar.make(parentlayout3,"Too many requests right now to process!",Snackbar.LENGTH_LONG);
                    snackbar3.show();
                }
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                progressVerify.setVisibility(View.INVISIBLE);
                // Log.d(TAG, "onCodeSent:" + verificationId);
                View parentlayout = findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar.make(parentlayout,"Verification code has been sent on your number.",Snackbar.LENGTH_LONG);
                snackbar.show();

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
                e1.setVisibility(View.GONE);
                b1.setVisibility(View.GONE);
                t1.setVisibility(View.GONE);
                ccp.setVisibility(View.GONE);
                t2.setVisibility(View.VISIBLE);
                e2.setVisibility(View.VISIBLE);
                b2.setVisibility(View.VISIBLE);
                // ...
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                number=ccp.getFullNumberWithPlus();
                progressVerify.setVisibility(View.VISIBLE);

                if (e1.getText().toString().equals(""))
                {
                    Snackbar snackbar = Snackbar.make(v,"Please enter no.",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                else
                {
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                number,
                                60,
                                java.util.concurrent.TimeUnit.SECONDS,
                                PhoneLogin.this,
                                mCallbacks);

                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e2.getText().toString().equals(""))
                {
                    Snackbar snackbar = Snackbar.make(v,"Please enter Code. It can't be left empty!",Snackbar.LENGTH_LONG);
                    snackbar.show();
                }

                else {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, e2.getText().toString());
                    // [END verify_with_code]
                    progressVerify.setVisibility(View.VISIBLE);
                    signInWithPhoneAuthCredential(credential);
                }
            }
        });
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressVerify.setVisibility(View.INVISIBLE);

                            String userphone="";

                            dbHelper db=new dbHelper(PhoneLogin.this);
                            Cursor cursor=db.alldata();

                            if(cursor.getCount()!=0)
                            {
                                while(cursor.moveToNext()){
                                    userphone=cursor.getString(4);
                                }
                            }

                            if(userphone.equals(number))              //user logging in again.
                            {
                                Intent intent=new Intent(PhoneLogin.this, MainActivity.class);
                                View parentlayout = findViewById(android.R.id.content);
                                Snackbar snackbar = Snackbar.make(parentlayout,"Welcome Back..!",Snackbar.LENGTH_LONG);
                                snackbar.show();

                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                Intent i=new Intent(PhoneLogin.this, Register.class);
                                i.putExtra("phone",number);

                                View parentlayout3 = findViewById(android.R.id.content);
                                Snackbar snackbar3 = Snackbar.make(parentlayout3,"Verification Done!",Snackbar.LENGTH_LONG);
                                snackbar3.show();

                                startActivity(i);
                                finish();
                            }

                            // ...
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                View parentlayout3 = findViewById(android.R.id.content);
                                Snackbar snackbar3 = Snackbar.make(parentlayout3,"Invalid Verification!",Snackbar.LENGTH_LONG);
                                snackbar3.show();
                            }
                        }
                    }
                });
    }
}

