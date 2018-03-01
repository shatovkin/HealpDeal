package com.example.dimitri.helpdeal.azureClasses.menuActivities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.azureModels.Users;
import com.example.dimitri.helpdeal.azureClasses.classes.SessionManager;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class UserProfile_Activity extends AppCompatActivity {

    private SessionManager session;
    private MobileServiceClient mClient;
    private MobileServiceTable<Users> mUsers;

    TextView firstname, name,password,index,city,street,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_layout);

        firstname = (TextView) findViewById(R.id.txt_firstname_profile);
        name= (TextView) findViewById(R.id.txt_name_profile);
        password= (TextView) findViewById(R.id.txt_password_profile);
        phone = (TextView) findViewById(R.id.txt_phone_profile);

        session = new SessionManager(getApplicationContext());

        String id = "";
        HashMap<String, String> sesionData = session.getUserDetails();

        for (Map.Entry entry : sesionData.entrySet()) {
            Object s = entry.getKey();

         //   firstname.setText();
            if (entry.getKey().equals("user_id")) {
                id = (String) entry.getValue();

            }
        }

        try {
            mClient = new MobileServiceClient("https://helpdeal.azurewebsites.net", UserProfile_Activity.this);

            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(10, TimeUnit.SECONDS);
                    client.setWriteTimeout(10, TimeUnit.SECONDS);
                    return client;
                }
            });
            mUsers = mClient.getTable(Users.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
