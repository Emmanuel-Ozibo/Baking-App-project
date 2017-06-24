package com.emma.bakingapp.WidgetBackgroundJobs;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;



public class DataBroadCastReciever extends BroadcastReceiver {
    private static final String BOARDCAST_RECIEVED = "com.emma.bakingapp.getdataforwidget";

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();

        if (action.equals(BOARDCAST_RECIEVED)){
            Intent serviceIntent = new Intent(context, DataFetchService.class);
            context.startService(serviceIntent);
        }
    }
}
