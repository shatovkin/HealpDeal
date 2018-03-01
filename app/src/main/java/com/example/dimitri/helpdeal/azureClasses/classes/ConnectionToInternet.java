package com.example.dimitri.helpdeal.azureClasses.classes;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by Dimitri on 19.01.2018.
 */

public class ConnectionToInternet {

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
