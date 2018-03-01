package com.provider;

import android.content.Context;

import com.example.dimitri.helpdeal.azureClasses.azureModels.BranchOfferView;
import com.example.dimitri.helpdeal.azureClasses.classes.ProgressFilter;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.util.List;

/**
 * Created by Dimitri on 17.01.2018.
 */

public class MobileServerAdapter {


    private MobileServiceClient mClient;
    private MobileServiceTable<BranchOfferView> mEmployeeOffers;
    private List<BranchOfferView> offerEmployeeList;
    ProgressFilter progressFilter;


    public MobileServerAdapter(Context context, ProgressFilter progressFilter) {
        try {
            /*mClient = new MobileServiceClient("https://helpdeal.azurewebsites.net", context.this).withFilter(progressFilter);

            //region DB Queries Extend timeout from default of 10s to 20s
            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(10, TimeUnit.SECONDS);
                    client.setWriteTimeout(10, TimeUnit.SECONDS);
                    return client;
                }
            });

            mEmployeeOffers = mClient.getTable(BranchOfferView.class);*/
        }catch (Exception e)
        {

        }
    }
}
