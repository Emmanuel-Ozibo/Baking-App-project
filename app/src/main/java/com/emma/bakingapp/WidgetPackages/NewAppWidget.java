package com.emma.bakingapp.WidgetPackages;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.emma.bakingapp.R;

/**
 * Implementation of App Widget functionality.
 */


public class NewAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, WidgetListViewService.class);
//        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);

//        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        //create the remote view
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
        //set the remote view adapter
        remoteViews.setRemoteAdapter(R.id.my_listview, intent);

        //sets the empty list view
        remoteViews.setEmptyView(R.layout.empty_list_view_layout, appWidgetId);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

