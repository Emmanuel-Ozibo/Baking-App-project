package com.emma.bakingapp.WidgetPackages;


import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViewsService;

public class WidgetListViewService extends RemoteViewsService{


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {

        return new WidgetRemoteViewFactory(this.getApplicationContext(), intent);
    }
}
