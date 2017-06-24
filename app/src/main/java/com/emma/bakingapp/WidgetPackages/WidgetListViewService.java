package com.emma.bakingapp.WidgetPackages;


import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetListViewService extends RemoteViewsService{


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        Log.e("I Got Here: "," yay!!!!!!!!");

        return new WidgetRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
