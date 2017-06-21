package com.emma.bakingapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.emma.bakingapp.Utils.CheckNetworkConnection;
import com.emma.bakingapp.Utils.ToastMessageUtil;

public class WelcomeActivity extends AppCompatActivity {
    private Button start_btn;
    private TextView baking_time, message;

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
                }else {

                    //TODO HANDLE A DIALOG FRAGMENT THAT TAKES CARE OF OPENING THE DATA OR THE WIFI
                    ToastMessageUtil.getToastMessage(getApplicationContext(), "Ooops");
                }

            }
        });
    }
}
