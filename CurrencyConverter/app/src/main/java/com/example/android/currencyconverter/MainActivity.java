package com.example.android.currencyconverter;

import android.content.ComponentName;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button mButtonSendBroadcast;
    private EditText inputAmount;
    private Spinner currencySelection;
    public static boolean myflag = false;
    public static float currency_converted = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSendBroadcast = (Button)findViewById(R.id.btn_send_broadcast);
        inputAmount = (EditText)findViewById(R.id.inputAmount);
        currencySelection = findViewById(R.id.dropdown_currency);
        String[] currency = {"Indian Rupee","British Pound","Euro"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currency);
        currencySelection.setAdapter(adapter);


        mButtonSendBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountConversion();
            }
        });
    }

    public void amountConversion() {


        String amount = inputAmount.getText().toString();
        String currency_type = currencySelection.getSelectedItem().toString();

        if (TextUtils.isEmpty(amount)) {
            Toast t = Toast.makeText(this, "Please enter the amount to be converted", Toast.LENGTH_SHORT);
            t.show();
        } else {
            Intent intent = new Intent();

            intent.setAction("AmountSent");

            int foo = Integer.parseInt(amount);
            intent.putExtra("Amount",foo);
            intent.putExtra("Currency",currency_type);

            sendBroadcast(intent);
        }

    }
     @Override
    protected void onResume()
    {
            super.onResume();
        if(myflag == true)
        {

            TextView mTextView = (TextView) findViewById(R.id.message);
            String amount = inputAmount.getText().toString();
            String currency_type = currencySelection.getSelectedItem().toString();

            mTextView.setText("Dollars amount $"+amount+" converted to " + currency_converted +" "+ currency_type );


        }
    }

    public void finishActivity(View v) {
        MainActivity.this.finish();
    }
}
