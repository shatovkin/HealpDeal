package com.example.dimitri.helpdeal.azureClasses.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.activities.modulEmployee.CategorieEmployee_Activity;
import com.example.dimitri.helpdeal.azureClasses.azureAdapter.UserAdapter;
import com.example.dimitri.helpdeal.azureClasses.azureModels.Users;
import com.example.dimitri.helpdeal.azureClasses.classes.ProgressFilter;
import com.example.dimitri.helpdeal.azureClasses.classes.SessionManager;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class Login_Activity extends AppCompatActivity implements OnClickListener {

    private EditText mPasswordView;
    private EditText mLoginView;
    Button mEmailSignInButton;
    private MobileServiceClient mClient;
    private MobileServiceTable<Users> mUsers;
    private UserAdapter mAdapter;
    private ProgressBar progressBar;
    private ProgressFilter progressFilter;
    private String password, login;
    private TextView createAccount;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        try {


            session = new SessionManager(getApplicationContext());
            if (session.isLoggedIn()) {
                Intent i = new Intent(this,CategorieEmployee_Activity.class);
                startActivity(i);
            }

            progressBar = (ProgressBar) findViewById(R.id.login_progress);
            progressFilter = new ProgressFilter(progressBar, this);
            mPasswordView = (EditText) findViewById(R.id.password);

            createAccount = (TextView) findViewById(R.id.createNewAccount);
            createAccount.setOnClickListener(this);

            mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(this);

            mLoginView = (EditText) findViewById(R.id.email);
            mPasswordView = (EditText) findViewById(R.id.password);

            mClient = new MobileServiceClient("https://helpdeal.azurewebsites.net", Login_Activity.this).withFilter(progressFilter);

            // Extend timeout from default of 10s to 20s
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

          //  mLoginView.setText("dimitri.shat@gmail.com");
           // mPasswordView.setText("Dimitri");
            mAdapter = new UserAdapter(this, R.layout.login_layout);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                checkLoginData();
                break;
            case R.id.createNewAccount:
                Intent i = new Intent(this,Register_activity.class);
                startActivity(i);
                break;
        }
    }

    private void checkLoginData() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<Users> userList = getUserEmail();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (userList.size() == 0) {
                                mLoginView.setError(getString(R.string.error_email_not_exists));
                                mPasswordView.setError(getString(R.string.error_email_not_exists));
                            } else {
                                for (Users user : userList) {
                                    if (user.getPassword().equals(mPasswordView.getText().toString()) && user.getEmail().equals(mLoginView.getText().toString())) {
                                        session.createLoginSession(user);
                                        Intent i = new Intent(Login_Activity.this, CategorieEmployee_Activity.class);
                                        startActivity(i);
                                    } else {
                                        mLoginView.setError(getString(R.string.error_email_not_exists));
                                        mPasswordView.setError(getString(R.string.error_email_not_exists));
                                    }
                                }
                            }
                        }
                    });
                } catch (Exception exception) {
                    Log.d("ExceptionLog", "ExceptionTur:" + exception.toString());
                }
                return null;
            }
        }.execute();
    }

    @Override
    public void onBackPressed() {
        if (!session.isLoggedIn()) {
        } else {
            super.onBackPressed();
        }
    }
    private List<Users> getUserEmail() {
        List<Users> lis = null;
        try {
            lis = mUsers.where().field("u_email").eq(val(mLoginView.getText().toString())).execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return lis;
    }
}
