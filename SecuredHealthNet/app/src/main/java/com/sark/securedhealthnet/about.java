package com.sark.securedhealthnet;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class about extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener mauthStateListener;
    public GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    static final int check=1111;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i=new Intent(about.this,MainActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_voice:
                  // voice();
                    return true;
                case R.id.navigation_doctor:
//                    Intent intent1=new Intent(about.this,MapsActivity.class);
//                    startActivity(intent1);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        WebView webView= (WebView) findViewById(R.id.webviewabt);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        setupFirebaseListener();

        String htmlText = " %s ";
        String myData = "<html><body style=\"text-align:justify\">An Android Mobile Application can serve as a solution for all the patients who want their medical information to be confidential between them and the doctors treating them. \n" +
                "The main purpose of the app is to capture the patient’s basic data and their respective medical record information and give its access only to the related Doctor/clinician and staff of the respective patient. And also, Patient data captured at the time of registration helps in avoiding repetitive work for now and for future as patients’ details are saved against their respective hospital ID. The app will also have a Chat Interface where the patient can chat with the selected doctor regarding all the health-related queries one has. For the patients to guide finding specific doctors and respective hospitals based on their choice of priorities, an AI based chat bot would be integrated in the app which will be developed. <br><br></body></html>";

        myData.replace("\\r\\n", "<br>").replace("\\n", "<br>");
        webView.loadDataWithBaseURL(null, String.format(htmlText, myData), "text/html", "utf-8","utf-8");
        webSettings.setDefaultFontSize(19);
        webView.setBackgroundColor(000000);
        webView.setPaddingRelative(2,2,2,2);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }
    private void setupFirebaseListener(){

        mauthStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user=firebaseAuth.getCurrentUser();
                if(user!=null){
                    //signed-in
                    Log.d("AccountManager","onAuthStateChanged: signed_in: "+user.getUid());
                }
                else{
                    //signout
                    Toast.makeText(about.this,"Signed out", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(about.this, MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                }
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mauthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mauthStateListener!=null)
            FirebaseAuth.getInstance().removeAuthStateListener(mauthStateListener);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.about){
            Intent i= new Intent(about.this, about.class);
            startActivity(i);
            return true;
        }
//        if(id==R.id.signout){
//            Toast.makeText(about.this,"Signing out...", Toast.LENGTH_SHORT).show();
//            FirebaseAuth.getInstance().signOut();
//
//            return true;
//        }

        if(id==R.id.contact){
            Toast.makeText(about.this,"Now You can write to developers directly!", Toast.LENGTH_LONG).show();
            Intent i= new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:08095030481"));
            i.putExtra("address", "08095030481");
            startActivity(i);
            return true;
        }

        if(id==R.id.exit){
            onBackPressed1();
            return true;
        }

        if(id==android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }
//    public void voice(){
//        Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak out where you want to navigate to, in the app among Predictor" +
//                ", Doctor, About, Home and Exit to exit from app. ");
//        startActivityForResult(i,check);
//
//
//
//    }
//    protected void onActivityResult(int requestcode, int resultcode, Intent data)
//    {
//        super.onActivityResult(requestcode,resultcode,data);
//
//        if(requestcode==check && resultcode==RESULT_OK)
//        {
//            ArrayList<String> results=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//            //lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,results));
//            if(results.contains("predictor"))
//            {
//                Intent i=new Intent(about.this,Predictor.class);
//                startActivity(i);
//
//            }
//            if(results.contains("doctor"))
//            {
//                Intent i=new Intent(about.this,MapsActivity.class);
//                startActivity(i);
//
//            }
//            else if(results.contains("about"))
//            {
//                Intent i=new Intent(about.this,about.class);
//                startActivity(i);
//            }
//            else if(results.contains("Home"))
//            {
//                Intent i=new Intent(about.this,Homepage.class);
//                startActivity(i);
//            }
//            else if(results.contains("exit"))
//            {
//                final AlertDialog.Builder builder=new AlertDialog.Builder(about.this);
//                builder.setMessage("Are you sure, want to exit ?");
//                builder.setCancelable(true);
//
//                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//                builder.setPositiveButton("Exit from app!", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(about.this,"Signing out...", Toast.LENGTH_SHORT).show();
//
//                        FirebaseAuth.getInstance().signOut();
//
//                    }
//                });
//                AlertDialog alertdialog=builder.create();
//                alertdialog.show();
//
//            }
//
//            else
//                Toast.makeText(about.this,"None of the suitable choices matched! Try again with appropriate choices.!", Toast.LENGTH_SHORT).show();
//
//
//        }
 //   }
    public void onBackPressed1() {
        final AlertDialog.Builder builder=new AlertDialog.Builder(about.this);
        builder.setMessage("Are you sure, want to exit ?");
        builder.setCancelable(true);

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setPositiveButton("Exit from app!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(about.this,"Signing out...", Toast.LENGTH_SHORT).show();

                FirebaseAuth.getInstance().signOut();

            }
        });
        AlertDialog alertdialog=builder.create();
        alertdialog.show();

    }

}
