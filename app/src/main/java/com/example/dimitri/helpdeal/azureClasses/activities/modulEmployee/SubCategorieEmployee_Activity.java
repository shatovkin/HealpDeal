package com.example.dimitri.helpdeal.azureClasses.activities.modulEmployee;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.activities.Login_Activity;
import com.example.dimitri.helpdeal.azureClasses.activities.Register_activity;
import com.example.dimitri.helpdeal.azureClasses.azureAdapter.EmployeeOffer_Adapter;
import com.example.dimitri.helpdeal.azureClasses.azureModels.BranchOfferView;
import com.example.dimitri.helpdeal.azureClasses.classes.ConnectionToInternet;
import com.example.dimitri.helpdeal.azureClasses.classes.ProgressFilter;
import com.example.dimitri.helpdeal.azureClasses.classes.SessionManager;
import com.example.dimitri.helpdeal.azureClasses.menuActivities.UserProfile_Activity;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.mikepenz.iconics.typeface.FontAwesome;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SubCategorieEmployee_Activity extends AppCompatActivity {

    private Drawer.Result drawerResult = null;
    private ListView listview_jobs;
    private MobileServiceClient mClient;
    private MobileServiceTable<BranchOfferView> mEmployeeOffers;
    private List<BranchOfferView> offerEmployeeList;
    private EmployeeOffer_Adapter mAdapter;
    private ProgressFilter progressFilter;
    private ProgressBar progressBar;
    private Intent categorieIntent;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_employeeoffers);

        offerEmployeeList = new ArrayList<>();

        sessionManager = new SessionManager(this);
        progressBar = (ProgressBar) findViewById(R.id.customerjob_progress);
        progressFilter = new ProgressFilter(progressBar, this);
        categorieIntent = getIntent();

        //region Menue
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.menuToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerResult = new Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_home).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new PrimaryDrawerItem().withName(R.string.drawer_item_custom).withIcon(FontAwesome.Icon.faw_eye).withBadge("6").withIdentifier(2),
                        new SectionDrawerItem().withName(R.string.drawer_item_settings),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_open_source).withIcon(FontAwesome.Icon.faw_question).setEnabled(false),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_contact).withIcon(FontAwesome.Icon.faw_github).withBadge("12+").withIdentifier(1)

                ).withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {

                        if (position == 1) {
                            Intent i = new Intent(SubCategorieEmployee_Activity.this, UserProfile_Activity.class);
                            startActivity(i);
                        }
                        if (position == 2) {
                            Intent i = new Intent(SubCategorieEmployee_Activity.this, ObservedPersonList_Activity.class);
                            startActivity(i);
                        }
                    }
                })
                .build();
        //endregion

       if (!ConnectionToInternet.isNetworkConnected(this))
       {Toast.makeText(this,"Check the connection to internet", Toast.LENGTH_LONG).show();}

        try {
            mClient = new MobileServiceClient("https://helpdeal.azurewebsites.net", SubCategorieEmployee_Activity.this).withFilter(progressFilter);

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


            mEmployeeOffers = mClient.getTable(BranchOfferView.class);

            mAdapter = new EmployeeOffer_Adapter(this, R.layout.row_list_employee_offer);

            listview_jobs = (ListView) findViewById(R.id.listof_jobs);
            listview_jobs.setAdapter(mAdapter);

            listview_jobs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    BranchOfferView job = offerEmployeeList.get(position);
                    Intent intent = new Intent(SubCategorieEmployee_Activity .this, PersonalProfile.class);
                    intent.putExtra("personalProfileID", job.getEmp_user_id());
                    startActivity(intent);
                }
            });
            showAll(listview_jobs, categorieIntent.getStringExtra("category"));
            //endregion
        } catch (
                MalformedURLException e)
        {
            e.printStackTrace();
        }

    }

    public void showAll(View view, final String category) {

        String cat = category;

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {

                final MobileServiceList<BranchOfferView> result;
                //final MobileServiceList<Users> usersOfOffers;
                try {
                    result = mEmployeeOffers.where().field("emp_category").eq(category).select("id","emp_user_id", "emp_salary_per_hour",
                                                                                                "emp_experience","u_firstname","u_name",
                                                                                                "u_summe_of_rating","u_count_of_rating",
                                                                                                "u_photo","u_var_email","u_var_phone",
                                                                                                "u_var_pass").orderBy("createdAt", QueryOrder.Ascending).execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.clear();
                            for (BranchOfferView job : result) {
                                offerEmployeeList.add(job);
                                mAdapter.add(job);
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("TraceSQL:", "trace!!!:" + e.toString());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    Log.d("TraceSQL:", "trace!!!:" + e.toString());
                }
                return null;
            }
        }.execute();
    }


    //region Menue Backbutton pressed and menue Selected
    @Override
    public void onBackPressed() {
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        if (sessionManager.isLoggedIn()) {
            inflater.inflate(R.menu.logout_without_searchview, menu);
        } else {
            inflater.inflate(R.menu.login_without_searchview, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(this, Login_Activity.class);
                startActivity(intent);
                break;
            case R.id.singin:
                Intent singIn = new Intent(this, Register_activity.class);
                startActivity(singIn);
                return true;
            case R.id.logout:
                Intent logout_intent = new Intent(this, Login_Activity.class);
                startActivity(logout_intent);
                sessionManager.logoutUser();
            case R.layout.drawer_header:

        }
        return false;
    }
    //endregion
}

