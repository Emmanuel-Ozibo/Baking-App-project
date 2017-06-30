package com.emma.bakingapp.Ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emma.bakingapp.R;
import com.emma.bakingapp.Utils.CheckNetworkConnection;
import com.emma.bakingapp.Utils.ToastMessageUtil;
import com.emma.bakingapp.WidgetBackgroundJobs.DataBroadCastReciever;

public class WelcomeActivity extends AppCompatActivity {
    private Button start_btn;
    private TextView baking_time, message;
    private static final String BROADCAST_ACTION = "com.emma.bakingapp.getdataforwidget";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        start_btn = (Button) findViewById(R.id.start_btn);
        baking_time = (TextView) findViewById(R.id.baking_time);
        message = (TextView) findViewById(R.id.message);


        //custom font styles
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/from_cartoon_block.ttf");
        Typeface typface = Typeface.createFromAsset(getAssets(), "fonts/windsong.ttf");
        baking_time.setTypeface(typeface);
        message.setTypeface(typface);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isNetworkIntact = CheckNetworkConnection.getNetworkInfo(getApplicationContext());
                if (isNetworkIntact){
                    Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);

                    //also send a custom broadcast to fetch data from the internet
                    sendToBroadCast();
                }else {

                    ToastMessageUtil.getToastMessage(getApplicationContext(), getString(R.string.no_data_message));
                }

            }
        });
    }

    private void sendToBroadCast() {
        //create an intent
        Intent broadcastIntent = new Intent(WelcomeActivity.this, DataBroadCastReciever.class);
        broadcastIntent.setAction(BROADCAST_ACTION);
        sendBroadcast(broadcastIntent);
    }


    int no_of_clicks = 0;
    @Override
    public void onBackPressed() {

        no_of_clicks++;

        if (no_of_clicks == 1){
            ToastMessageUtil.getToastMessage(getApplicationContext(), "Press Back again to exit");
        }
        else if(no_of_clicks > 1){
            super.onBackPressed();
            return;
        }
    }
}
