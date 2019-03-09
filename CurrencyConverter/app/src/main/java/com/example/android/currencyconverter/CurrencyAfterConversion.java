package com.example.android.currencyconverter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CurrencyAfterConversion extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Receive the broadcast random number
        if(intent.getAction().equals("Currency_Transfer_after_conversion"))
        {

            float received_rate = intent.getFloatExtra("Converted_Rate",-1);
           // String currency_type = intent.getStringExtra("Currency");
            MainActivity.myflag = true;
            MainActivity.currency_converted = received_rate;
           // MainActivity.mycurrency = currency_type;



        }

    }
}