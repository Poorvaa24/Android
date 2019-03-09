package com.example.android.currencyexchange;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
//import android.widget.TextView;
//import android.widget.Toast;

public class CurrencyExchange extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("AmountSent"))
        {
            int receivedNumber = intent.getIntExtra("Amount", -1);
            String currency_type = intent.getStringExtra("Currency");
            MainActivity.myflag = true;
            MainActivity.myvalue = receivedNumber;
            MainActivity.mycurrency = currency_type;



        }

    }
}