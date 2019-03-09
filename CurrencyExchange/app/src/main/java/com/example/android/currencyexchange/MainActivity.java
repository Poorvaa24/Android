package com.example.android.currencyexchange;



import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;


public class MainActivity extends AppCompatActivity {

    TextView mTextView;
    public static boolean myflag = false;
    public static int myvalue = 0;
    public static String mycurrency;
    private Button receiver_button;



    public void enableStrictMode()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mTextView = (TextView) findViewById(R.id.dollar_amt_val);
        mTextView.setText(" ");


    }

    @Override
    protected void onResume() {
        super.onResume();
        receiver_button = (Button)findViewById(R.id.apply);
        if(myflag == true)
        {
            // Double converted_value = findExchangeRateAndConvert("USD","INR",myvalue);
            TextView mTextView = (TextView) findViewById(R.id.dollar_amt_val);
            mTextView.setText("" + myvalue);
            TextView currencyTextView = (TextView)findViewById(R.id.convert_to_value);
            currencyTextView.setText((mycurrency));
        }
        // Set a click listener for button
        receiver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String api_response = GetAPIResponse();
                float conversion_rate = GetConversionRate(api_response,mycurrency);
                float final_conversion_rate = myvalue * conversion_rate;
                TextView mTextView = (TextView) findViewById(R.id.exchange_value);
                mTextView.setText("" + final_conversion_rate);

                Intent intent = new Intent();
                intent.setAction("Currency_Transfer_after_conversion");
                intent.putExtra("Converted_Rate",final_conversion_rate);
                sendBroadcast(intent);


            }
        });


    }

   public float GetConversionRate(String apiResponse, String currency)
    {
        //Log.v("***func", "GetConversionRate");
        //Log.v("api value", " " + apiResponse + " currency = " + currency );
        Object sObject = null;
        String currency_type = " ";
        JSONParser myparser = new JSONParser();
        if(currency.equals("Indian Rupee"))
        {
            currency_type = "INR";
        }
        else if(currency.equals("British Pound"))
        {
            currency_type = "GBP";
        }
        else if(currency.equals("Euro"))
        {
            currency_type = "EUR";
        }

        try
        {
            JSONObject myobj = (JSONObject)myparser.parse(apiResponse);
            JSONObject myRateObj = (JSONObject)myobj.get("rates");
            String[] symbols = {"INR", "GBP", "EUR"};
            sObject =  myRateObj.get(currency_type);
        }
        catch (org.json.simple.parser.ParseException e)
        {
            e.printStackTrace();
        }

        String convertedToString = sObject.toString();
        float conversion_rate = Float.parseFloat(convertedToString);

        return conversion_rate;
    }

    public String GetAPIResponse()
    {
       // Log.v("***func", "GetAPIResponse");
        enableStrictMode();

        URL urlForGetRequest = null;
        String apiResonse = null;
        try {
            urlForGetRequest = new URL("https://api.openrates.io/latest?base=USD");
            String readLine = null;
            HttpURLConnection conection = (HttpURLConnection) urlForGetRequest.openConnection();
            conection.setRequestMethod("GET");
            int responseCode = conection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                //Log.v("***func", "Message okay");
                BufferedReader in = new BufferedReader(new InputStreamReader(conection.getInputStream()));
                StringBuffer response = new StringBuffer();
                while ((readLine = in.readLine()) != null)
                {
                    response.append(readLine);
                }
                in.close();
               // Log.v("**resposne**", response.toString());
                apiResonse = response.toString();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return apiResonse;

    }



    public void finishActivity(View v) {
        MainActivity.this.finish();
    }



}

