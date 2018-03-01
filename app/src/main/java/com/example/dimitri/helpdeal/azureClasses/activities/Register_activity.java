package com.example.dimitri.helpdeal.azureClasses.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.azureAdapter.UserAdapter;
import com.example.dimitri.helpdeal.azureClasses.azureModels.Users;
import com.example.dimitri.helpdeal.azureClasses.classes.ProgressFilter;
import com.microsoft.windowsazure.mobileservices.MobileServiceActivityResult;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.microsoft.windowsazure.mobileservices.table.query.QueryOperations.val;

public class Register_activity extends AppCompatActivity implements View.OnClickListener {

    //region Variablen
    private Button btn_register;
    private EditText name, firstName, email, password, password_confirm,phone;
    private TextView link_to_google_signin;
    private MobileServiceClient mClient;
    private MobileServiceTable<Users> mUsers;
    private UserAdapter mAdapter;
    private ProgressBar mProgressBar;
    private ProgressFilter progressFilter;
    public static final int GOOGLE_LOGIN_REQUEST_CODE = 1;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        progressFilter = new ProgressFilter(mProgressBar,this);

        name = (EditText) findViewById(R.id.input_name);
        firstName = (EditText) findViewById(R.id.input_firstname);
        phone = (EditText) findViewById(R.id.input_phone);
        email = (EditText) findViewById(R.id.input_email);
        password = (EditText) findViewById(R.id.input_password);
        password_confirm = (EditText) findViewById(R.id.input_password_confirm);

        link_to_google_signin = (TextView) findViewById(R.id.link_to_google_signin);
        link_to_google_signin.setOnClickListener(this);
        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(this);

        try {
            mClient = new MobileServiceClient(
                    "https://helpdeal.azurewebsites.net",
                    Register_activity.this).withFilter(progressFilter);

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

            // Get the Mobile Service Table instance to use
            mUsers = mClient.getTable(Users.class);
            mAdapter = new UserAdapter(this, R.layout.register_layout);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setEditText();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
    }
    private void setEditText() {
       /* name.setText("Shatovkin");
        firstName.setText("Dimitri");
        email.setText("dimitri.shat@gmail.com");
        password.setText("Dimitri");
        password_confirm.setText("Dimitri");*/
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                btn_register.setEnabled(false);
                mProgressBar.setVisibility(ProgressBar.GONE);
                if (validateFieldsBlank() == true) {
                    if (email.getText().toString().contains("@")) {
                        if (password.getText().toString().equals(password_confirm.getText().toString())) {
                            if (password.getText().length() >= 6) {
                                checkUserExistence();
                            } else {
                                btn_register.setEnabled(true);
                                setFocus(password);
                                password.setError(getString(R.string.error_invalid_password));
                            }
                        } else {
                            btn_register.setEnabled(true);
                            password.setError(getString(R.string.error_password_not_confirm));
                            password_confirm.setError(getString(R.string.error_password_not_confirm));
                        }
                    } else {
                        btn_register.setEnabled(true);
                        setFocus(email);
                        email.setError(getString(R.string.error_invalid_email));
                    }
                }
                btn_register.setEnabled(true);
                break;
        }
    }
    private void setFocus(EditText field) {
        field.setFocusableInTouchMode(true);
        field.setFocusable(true);
        field.requestFocus(); }

    private boolean validateFieldsBlank() {
        if (firstName.getText().length() != 0) {
            if (name.getText().length() != 0)
                if (email.getText().length() != 0)
                    if (password.getText().length() != 0) {
                        if (password_confirm.getText().length() != 0) {
                            return true;
                        } else {
                            setFocus(password_confirm);
                            password_confirm.setError(getString(R.string.error_field_required));
                            return false;
                        }
                    } else {
                        setFocus(password);
                        password.setError(getString(R.string.error_field_required));
                        return false;
                    }
                else {
                    setFocus(email);
                    email.setError(getString(R.string.error_field_required));
                    return false;
                }
            else {
                setFocus(email);
                name.setError(getString(R.string.error_field_required));
                return false;
            }
        } else {
            setFocus(firstName);
            firstName.setError(getString(R.string.error_field_required));
            return false;
        }
    }
    //region google authenticator
    private void authenticate() {
        // Login using the Google provider.
        mClient.login("Google", "{https://helpdeal/.authusersgoogle.callback}", GOOGLE_LOGIN_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // When request completes
        if (resultCode == RESULT_OK) {
            // Check the request code matches the one we send in the login request
            if (requestCode == GOOGLE_LOGIN_REQUEST_CODE) {
                MobileServiceActivityResult result = mClient.onActivityResult(data);
                if (result.isLoggedIn()) {
                    // login succeeded
                    try{
                        //hier die Userdaten sammeln und in die addItemInTable methode übergebehn
                    }
                    catch (Exception e)
                    {
                    };
                } else {
                    // login failed, check the error message
                    String errorMessage = result.getErrorMessage();
                }
            }
        }
    }
 //endregion

    private void checkUserExistence() {
        btn_register.setEnabled(false);
        if (mClient == null) {
            return;
        }
          new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final List<Users> result = getEmailToCheckUserExistence();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           mAdapter.clear();
                            for (Users item : result) {
                                mAdapter.add(item);
                            }
                            if (mAdapter.getCount() > 0) {
                                email.setError(getString(R.string.error_email_exists));
                            } else {
                                  new AsyncTask<Void, Void, Void>() {
                                    @Override
                                    protected Void doInBackground(Void... params) {
                                        try {
                                            addItemInTable(createNewUser());
                                            // Der Thread startet die Speicherung des Users
                                            Intent i = new Intent(Register_activity.this,Login_Activity.class);
                                            startActivity(i);
                                        } catch (Exception exception) {
                                            Log.d("ExceptionLog", "Exception:" + exception.toString());
                                        }
                                        return null;
                                    }
                                }.execute();
                            }
                        }
                    });
                } catch (Exception exception) {
                    Log.d("ExceptionLog", "Exception:" + exception.toString());
                }
                return null;
            }
        }.execute();
    }

    private Users createNewUser() {
        Users user = new Users();
        user.setFirstname(firstName.getText().toString());
        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setPhone(phone.getText().toString());
        return user;
    }

    public void addItemInTable(Users user) {
        try {
            mUsers.insert(user).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("ExceptionLog1", "Exception:" + e.toString());
        } catch (ExecutionException e) {
            Log.d("ExceptionLog2", "Exception:" + e.toString());
            e.printStackTrace();
        }
    }
    private List<Users> getEmailToCheckUserExistence() throws ExecutionException, InterruptedException {
        return mUsers.where().field("u_email").eq(val(email.getText().toString())).execute().get();
    }
}

