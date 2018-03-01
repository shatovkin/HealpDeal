package com.example.dimitri.helpdeal.azureClasses.activities.modulEmployee;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.azureModels.BranchOfferView;
import com.example.dimitri.helpdeal.azureClasses.azureModels.ObservedPerson;
import com.example.dimitri.helpdeal.azureClasses.classes.ConnectionToInternet;
import com.example.dimitri.helpdeal.azureClasses.classes.ProgressFilter;
import com.example.dimitri.helpdeal.azureClasses.classes.SessionManager;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.mikepenz.materialdrawer.Drawer;
import com.squareup.okhttp.OkHttpClient;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dimitri on 15.01.2018.
 */

public class PersonalProfile extends AppCompatActivity{

    private Drawer.Result drawerResult = null;
    private MobileServiceClient mClient;
    private MobileServiceTable<BranchOfferView> mEmployeeOffers;
    private MobileServiceTable<ObservedPerson> mObservedPerson;
    private List<BranchOfferView> personalProfile;
    private ProgressFilter progressFilter;
    private ProgressBar progressBar;
    private SessionManager sessionManager;
    private String personalUserID;
    private String observedPersonID;

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private BranchOfferView branchOfferView;


    private TextView txt_name;
    private ImageView bewertung;
    private TextView txt_bewertung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalprofilelayout);
        /*beobachten = (Button) findViewById(R.id.btn_observer);
        beobachten.setOnClickListener(this);*/

        txt_name = (TextView) findViewById(R.id.txt_name_persprofile);
        bewertung =(ImageView) findViewById(R.id.bewertung) ;


        //region expandableList
        listView = (ExpandableListView) findViewById(R.id.expandableList);
        initData();
        listAdapter  = new ExpandableListAdapter(this, listDataHeader,listHash);


        int item_size =150;
        final int sub_item_size = 150;
        listView.getLayoutParams().height = item_size*listAdapter.getGroupCount();
        listView.setAdapter(listAdapter);

        // ListView Group Expand Listener
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int nb_children = listAdapter.getChildrenCount(groupPosition);
                listView.getLayoutParams().height += sub_item_size*nb_children;
            }
        });

        // Listview Group collapsed listener
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                int nb_children = listAdapter.getChildrenCount(groupPosition);
                listView.getLayoutParams().height -= sub_item_size*nb_children;
            }
        });
       //endregion

        sessionManager = new SessionManager(this);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_personalProfile);
        progressFilter = new ProgressFilter(progressBar, this);

        Bundle intent = getIntent().getExtras();
        personalUserID = (String) intent.get("personalProfileID");

        personalProfile = new ArrayList<BranchOfferView>();



        if (!ConnectionToInternet.isNetworkConnected(this)) {
            Toast.makeText(this, "Check the connection to internet", Toast.LENGTH_LONG).show();
        }

        try {
            mClient = new MobileServiceClient("https://helpdeal.azurewebsites.net", PersonalProfile.this).withFilter(progressFilter);

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
            showProfile();
            //endregion
        } catch (
                MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("About Sandra");
        listDataHeader.add("Qualifications");
        listDataHeader.add("Service");

        List<String> aboutSandra = new ArrayList<>();
        aboutSandra.add("* Weiblich");
        aboutSandra.add("* 20 Jahre alt");
        aboutSandra.add("* Liebe Eltern, mein Name ist Sandra, 20 Jahre alt. Ich habe noch keine Kinder, jedoch liebe ich die Kinder sehr.");

        List<String> qualifications= new ArrayList<>();
        qualifications.add("Erfahrung 5 Jahre");
        qualifications.add("Höchste Abschluss: Realschule");
        qualifications.add("Sprachen: Deutsch , Russisch");

        List<String> service= new ArrayList<>();
        service.add("Ab €12 pro Stunde");
        service.add("Köln +10 km Umgebung");
        service.add("Gartenarbeiten \n - Reisebereitschaft\nHecken schneiden \n Rasen Vertikulieren");

        listHash.put(listDataHeader.get(0),aboutSandra);
        listHash.put(listDataHeader.get(1),qualifications);
        listHash.put(listDataHeader.get(2),service);
    }


    public void showProfile() {

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                final MobileServiceList<BranchOfferView> result;
                try {
                    result = mEmployeeOffers.where().field("emp_user_id").eq(personalUserID).execute().get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (BranchOfferView person : result) {
                                setData(person);
                                branchOfferView = person;
                               // observedPersonID = person.getEmp_user_id();
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Log.d("TraceSQL:", "trace!!!:" + e.toString());
                } catch (ExecutionException e) {
                    Log.d("TraceSQL:", "trace!!!:" + e.toString());
                }
                return null;
            }
        }.execute();
    }

    private void setData(BranchOfferView person) {

        txt_name.setText(""+person.getUserFirstname()+" "+person.getUserName());

    }

    @Override
    protected void onStart()
    {
        super.onStart();

//        txt_name.setText( "pers:" +branchOfferView.getUserFirstname());


    }

    private void calculateRanking(String rank, String summe) {

        int sum = Integer.parseInt(summe);
        int ran = Integer.parseInt(rank);
        int ranking = sum/ran;


        if (ranking == 1) {
            ImageView rankinImage = (ImageView) findViewById(R.id.bewertung);
            rankinImage.setImageResource(R.drawable.stern_ohne_hintergrund_1);
        } else if (ranking == 2) {
            ImageView rankinImage = (ImageView) findViewById(R.id.bewertung);
            rankinImage.setImageResource(R.drawable.stern_ohne_hintergrund_2);
        } else if (ranking == 3) {
            ImageView rankinImage = (ImageView) findViewById(R.id.bewertung);
            rankinImage.setImageResource(R.drawable.stern_ohne_hintergrund_3);
        } else if (ranking == 4) {
            ImageView rankinImage = (ImageView) findViewById(R.id.bewertung);
            rankinImage.setImageResource(R.drawable.stern_ohne_hintergrund_4);
        } else if (ranking == 5) {
            ImageView rankinImage = (ImageView) findViewById(R.id.bewertung);
            rankinImage.setImageResource(R.drawable.stern_ohne_hintergrund_5);
        }

      /*  TextView rankingText = (TextView) row.findViewById(R.id.txt_ranking);
        rankingText.setText("" + ranking);*/
    }

    private ObservedPerson createOberservedPerson() {

        ObservedPerson observedPerson = new ObservedPerson();
        observedPerson.setCurrentUserID(personalUserID);
        observedPerson.setObservedUserID(observedPersonID);
        return observedPerson;
    }

    private void saveObservedPerson() {
      /*  mObservedPerson = mClient.getTable(ObservedPerson.class);
        // Insert the new item
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    mObservedPerson.insert(createOberservedPerson()).get();
                } catch (Exception exception) {
                    Log.d("Observed Person", "Observed Person wurde nicht gespeichert.");
                }
                return null;
            }
        }.execute();*/
    }


    //region Menue Backbutton pressed and menue Selected
    /*@Override
    public void onBackPressed() {
        if (drawerResult.isDrawerOpen()) {
            drawerResult.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }*/

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        if (sessionManager.isLoggedIn()) {
            inflater.inflate(R.menu.logout, menu);
        } else {
            inflater.inflate(R.menu.login, menu);
        }
        return true;
    }*/

    /*@Override
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
        }
        return false;
    }*/
}




/* listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int height = 0;

                for (int i = 0; i < listView.getChildCount(); i++) {
                    int height1 = listView.getChildAt(i).getMeasuredHeight();
                    int height2 = listView.getDividerHeight();

                    height += listView.getChildAt(i).getMeasuredHeight();
                    height += listView.getDividerHeight();
                }
                listView.getLayoutParams().height = (height);
           }
        });

        // Listview Group collapsed listener
        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                listView.getLayoutParams().height = listView.getLayoutParams().height-90;
            }
        });
*/