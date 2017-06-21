package com.emma.bakingapp.Utils;

import android.content.Context;
import android.widget.Toast;


public class ToastMessageUtil {

    public static void getToastMessage(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}