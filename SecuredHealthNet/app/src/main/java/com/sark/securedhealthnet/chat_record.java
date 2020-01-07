package com.sark.securedhealthnet;


import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class chat_record extends RecyclerView.ViewHolder  {



    TextView leftText,rightText;

    public chat_record(View itemView){
        super(itemView);

        leftText = (TextView)itemView.findViewById(R.id.leftText);
        rightText = (TextView)itemView.findViewById(R.id.rightText);


    }
}
