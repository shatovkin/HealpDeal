package com.example.dimitri.helpdeal.azureClasses.classes;

import android.app.Activity;
import android.widget.ProgressBar;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;

/**
 * Created by Dimitri on 08.11.2017.
 */

public class ProgressFilter implements ServiceFilter {

    private ProgressBar mProgressBar;
    private Activity activity;

    public ProgressFilter(ProgressBar progressBar, Activity activity)
    {
        this.mProgressBar = progressBar;
        this.activity = activity;
    }
    @Override
    public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request, NextServiceFilterCallback nextServiceFilterCallback) {

        mProgressBar.setVisibility(ProgressBar.GONE);

        final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();

       activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
            }
        });

        ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

        Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
            @Override
            public void onFailure(Throwable e) {
                resultFuture.setException(e);
            }

            @Override
            public void onSuccess(ServiceFilterResponse response) {
                activity.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                    }
                });
                resultFuture.set(response);
            }
        });
        return resultFuture;
    }
}