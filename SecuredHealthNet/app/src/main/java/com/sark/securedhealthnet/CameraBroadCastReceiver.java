package com.sark.securedhealthnet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CameraBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "You clicked a pic!! #Secured Health Net!", Toast.LENGTH_SHORT).show();

        Intent i=new Intent(context,Notification.class);
        context.startActivity(i);
    }
}
