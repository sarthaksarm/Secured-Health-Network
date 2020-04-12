package com.sark.securedhealthnet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.app.RemoteInput;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;


public class Notification extends AppCompatActivity {
    private static final String KEY_TEXT_REPLY = "key_text_reply";
    TextView batterytxt;
    IntentFilter intentFilter;
    ChargingBroadcastReceiver chargingBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        batterytxt=findViewById(R.id.txt);

        intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        chargingBroadcastReceiver=new ChargingBroadcastReceiver();

        createNotificationChannel();

        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,new Intent(this,DoctorHome.class),0);

        RemoteInput remoteInput=new RemoteInput.Builder(KEY_TEXT_REPLY)
                .setLabel("Reply")
                .build();

        NotificationCompat.Action replyaction=new NotificationCompat.Action.Builder(R.drawable.cast_ic_notification_small_icon,
                "Reply",pendingIntent)
                .addRemoteInput(remoteInput)
                .build();



        NotificationCompat.Builder builder=new NotificationCompat.Builder(this,"notify")
                                                .setSmallIcon(R.drawable.icon)
                                                .setContentTitle("Secured Health Net")
                                                .setContentText("Hey! That's notification!!!")
                                                .addAction(R.drawable.aggression,"Open App!",pendingIntent)
                                                .addAction(replyaction)
                                                .setPriority(NotificationCompat.PRIORITY_HIGH);



        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(1,builder.build());


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(chargingBroadcastReceiver,intentFilter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(chargingBroadcastReceiver);

    }

    public void showCharging(boolean ischarging)
    {
        if(ischarging)
            batterytxt.setText("Battery is charging");

        else
            batterytxt.setText("Battery is not charging");
    }

    private class ChargingBroadcastReceiver extends BroadcastReceiver
        {
            @Override
            public void onReceive(Context context, Intent intent) {
                //since we have applied intent filter in oncreate so we can only receive those 2 system intents here.

                String action=intent.getAction();
                boolean ischarging=(action.equals(Intent.ACTION_POWER_CONNECTED));

                showCharging(ischarging);

            }
        }




    private void createNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>=26)
        {
            NotificationChannel channel= new NotificationChannel("notify","channel name", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager notificationManager=getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }

}
