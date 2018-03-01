package com.example.dimitri.helpdeal.azureClasses.activities.modulCustomer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.azureImageStorage.ImageManager;
import com.example.dimitri.helpdeal.azureClasses.azureModels.OfferCustomer;
import com.example.dimitri.helpdeal.azureClasses.azureModels.Documents;
import com.example.dimitri.helpdeal.azureClasses.classes.ProgressFilter;
import com.example.dimitri.helpdeal.azureClasses.classes.SessionManager;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.query.QueryOrder;
import com.squareup.okhttp.OkHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class PopUp_Activity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private Spinner spinner_categorie, spinner_subcategorie;
    private MobileServiceClient mClient;
    private MobileServiceTable<OfferCustomer> mJob;
    private MobileServiceTable<Documents> mDocument;
    private ProgressBar mProgressBar;
    private ProgressFilter progressFilter;
    private Button btn_saveOffer;
    private SessionManager session;
    private HashMap<String, String> user;
    private EditText edt_startdate, edt_enddate, edt_jobtitel, edt_job_description, edt_job_city, edt_job_index;
    private ImageView finishbutton, createPhoto1, createPhoto2, createPhoto3;
    private DatePickerDialog.OnDateSetListener mDatesetListenerStartDate, mDatesetListenerEndDate;
    private TextView txt_photo1, txt_photo2, txt_photo3;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_layout);

        session = new SessionManager(this);
        user = session.getUserDetails();

        edt_jobtitel = (EditText) findViewById(R.id.edt_jobtitel);
        edt_jobtitel.setText("Küche zusammenbauen");
        edt_job_description = (EditText) findViewById(R.id.edt_jobdiscription);
        edt_job_description.setText("die Küche soll zusammengebaut werden");
        edt_job_index = (EditText) findViewById(R.id.edt_jobindex);
        edt_job_city = (EditText) findViewById(R.id.edt_jobcity);

        txt_photo1 = (TextView) findViewById(R.id.photo1);
        txt_photo2 = (TextView) findViewById(R.id.photo2);
        txt_photo3 = (TextView) findViewById(R.id.photo3);

        btn_saveOffer = (Button) findViewById(R.id.btn_savejob);
        btn_saveOffer.setOnClickListener(this);

        //region ImageView
        finishbutton = (ImageView) findViewById(R.id.finishbutton);
        finishbutton.setOnClickListener(this);

        createPhoto1 = (ImageView) findViewById(R.id.createPhoto1);
        createPhoto1.setOnClickListener(this);

        createPhoto2 = (ImageView) findViewById(R.id.createPhoto2);
        createPhoto2.setOnClickListener(this);

        createPhoto3 = (ImageView) findViewById(R.id.createPhoto3);
        createPhoto3.setOnClickListener(this);
        //endregion

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar_job);
        progressFilter = new ProgressFilter(mProgressBar, this);

        spinner_categorie = (Spinner) findViewById(R.id.spinner1);
        spinner_subcategorie = (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array.array_list, android.R.layout.simple_spinner_item);
        spinner_categorie.setAdapter(adapter1);
        spinner_categorie.setOnItemSelectedListener(this);

        try {
            mClient = new MobileServiceClient("https://helpdeal.azurewebsites.net", PopUp_Activity.this).withFilter(progressFilter);

            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(10, TimeUnit.SECONDS);
                    client.setWriteTimeout(10, TimeUnit.SECONDS);
                    return client;
                }
            });
            mJob = mClient.getTable(OfferCustomer.class);
            mDocument = mClient.getTable(Documents.class);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setGravity(Gravity.BOTTOM);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) width - 200, (int) height - 250);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //region Calender OnclickListener
        edt_startdate = (EditText) findViewById(R.id.edt_startdateClickText);
        edt_startdate.setOnClickListener(this);

        edt_enddate = (EditText) findViewById(R.id.edt_enddateClickText);
        edt_enddate.setOnClickListener(this);

        mDatesetListenerStartDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                edt_startdate.setText(dayOfMonth + "-" + month + "-" + year);
            }
        };

        mDatesetListenerEndDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                edt_enddate.setText(dayOfMonth + "-" + month + "-" + year);
            }
        };
        //endregion

        //region Check photos
        Intent intent = getIntent();

        if (intent.getStringExtra("photo1") != null || intent.getStringExtra("photo2") != null || intent.getStringExtra("photo3") != null) {
            edt_jobtitel.setText(intent.getStringExtra("edt_jobtitel"));
            edt_job_description.setText(intent.getStringExtra("job_description"));
            edt_startdate.setText(intent.getStringExtra("startdate"));
            edt_enddate.setText(intent.getStringExtra("enddate"));
            edt_job_index.setText(intent.getStringExtra("index"));
            edt_job_city.setText(intent.getStringExtra("city"));

            if (intent.getStringExtra("categorie") != null)
                spinner_categorie.setSelection(Integer.parseInt(intent.getStringExtra("categorie")));
            if (intent.getStringExtra("subcategorie") != null)
                spinner_subcategorie.setSelection(Integer.parseInt(intent.getStringExtra("subcategorie")));
            txt_photo1.setText(intent.getStringExtra("photo1"));
            txt_photo2.setText(intent.getStringExtra("photo2"));
            txt_photo3.setText(intent.getStringExtra("photo3"));


            String[] photo = {txt_photo1.getText().toString(), txt_photo2.getText().toString(), txt_photo3.getText().toString()};
            for (int i = 0; i < photo.length; i++) {
                setImage(photo[i], i);
            }
        }

        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("imageDate");
        if (bitmap != null) {
            String pictureNumber = intent.getStringExtra("pictureNumber");

            if (pictureNumber.equals("picture1")) {
                createPhoto1.setImageBitmap(bitmap);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                txt_photo1.setText(tempUri.toString());
            } else if (pictureNumber.equals("picture2")) {
                createPhoto2.setImageBitmap(bitmap);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                txt_photo2.setText(tempUri.toString());
            } else if (pictureNumber.equals("picture3")) {
                createPhoto3.setImageBitmap(bitmap);
                Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                txt_photo3.setText(tempUri.toString());
            }
        }
        //endregion
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public void setImage(String photoPath, int zahl) {

        InputStream inputStream;
        try {
            Uri imageUri = Uri.parse(String.valueOf(photoPath));
            inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap image = BitmapFactory.decodeStream(inputStream);

            if (zahl == 0) {
                createPhoto1.setImageBitmap(image);
            } else if (zahl == 1) {
                createPhoto2.setImageBitmap(image);
            } else if (zahl == 2) {
                createPhoto3.setImageBitmap(image);
            }
        } catch (FileNotFoundException e) {
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_savejob:
                //region save job
                if (edt_jobtitel.getText().length() <= 50) {
                    if (edt_jobtitel.getText().length() > 0) {
                        if (checkStartEnddate()) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... params) {
                                    try {
                                        saveOfferInDb(createNewJob());
                                        saveDocumentInDB(uploadImage(), "A");
                                    } catch (Exception exception) {
                                        Log.d("ExceptionLog", "Exception:" + exception.toString());
                                    }
                                    return null;
                                }
                            }.execute();

                        } else {
                            edt_enddate.setError(getString(R.string.errorDate));
                            btn_saveOffer.setEnabled(true);
                            Toast.makeText(PopUp_Activity.this, getString(R.string.errorDate), Toast.LENGTH_LONG).show();
                            break;
                        }
                    } else {
                        edt_jobtitel.setError(getString(R.string.errorTitel));
                        btn_saveOffer.setEnabled(true);
                        break;
                    }
                } else {
                    edt_jobtitel.setError(getString(R.string.errorLongTitel));
                    btn_saveOffer.setEnabled(true);
                    break;
                }
                btn_saveOffer.setEnabled(false);
                Toast.makeText(this, getString(R.string.savejob), Toast.LENGTH_LONG).show();
                //endregion
                break;
            case R.id.edt_startdateClickText:
                getCalenderDialog(R.id.edt_startdateClickText);
                break;
            case R.id.edt_enddateClickText:
                getCalenderDialog(R.id.edt_enddateClickText);
                break;
            case R.id.finishbutton:
                finish();
                break;
            case R.id.createPhoto1:
                Intent i1 = createIntent("picture1");
                startActivity(i1);
                break;
            case R.id.createPhoto2:
                Intent i2 = createIntent("picture2");
                startActivity(i2);
                break;
            case R.id.createPhoto3:
                Intent i3 = createIntent("picture3");
                startActivity(i3);
                break;
        }
    }

    private void saveDocumentInDB(ArrayList<String> docuPath, String typ) {
        try {

            List<OfferCustomer> offerCustomerJobsFromCurrrentUserTop1 = mJob.where().field("job_userId").eq(user.get(session.KEY_ID).toString())
                    .orderBy("createdAt", QueryOrder.Descending).top(1).execute().get();

            String offerID = "";
            for (OfferCustomer offerCustomer : offerCustomerJobsFromCurrrentUserTop1) {
                offerID = offerCustomer.getOfferId();
            }

            if(docuPath.size()>0) {
                for (String docuName : docuPath) {
                    Documents dokument = new Documents(offerID, docuName + ".png", typ);
                    mDocument.insert(dokument).get();
                }
            }
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("ExceptionLog1", "ExceptionTextRRRR:" + e.toString());
        } catch (ExecutionException e) {
            Log.d("ExceptionLog2", "ExceptionTextRRRR:" + e.toString());
            e.printStackTrace();
        }
    }

    private Intent createIntent(String pictureNumber) {

        Intent i = new Intent(this, Gallery_Activity.class);
        i.putExtra("edt_jobtitel", edt_jobtitel.getText().toString());
        i.putExtra("job_description", edt_job_description.getText().toString());
        i.putExtra("startdate", edt_startdate.getText().toString());
        i.putExtra("enddate", edt_enddate.getText().toString());
        i.putExtra("categorie", "" + spinner_categorie.getSelectedItemPosition());
        i.putExtra("subcategorie", "" + spinner_subcategorie.getSelectedItemPosition());
        i.putExtra("pictureNumber", pictureNumber);
        i.putExtra("index", edt_job_index.getText().toString());
        i.putExtra("city", edt_job_city.getText().toString());

        if (txt_photo1.getText().toString().length() > 0) {
            i.putExtra("photo1", txt_photo1.getText());
            Log.d("Logistic", txt_photo1.getText().toString());
        }

        if (txt_photo2.getText().toString().length() > 0) {
            i.putExtra("photo2", txt_photo2.getText());
            Log.d("Logistic", txt_photo2.getText().toString());
        }

        if (txt_photo3.getText().toString().length() > 0) {
            i.putExtra("photo3", txt_photo3.getText());
            Log.d("Logistic", txt_photo3.getText().toString());
        }
        return i;
    }


    private boolean checkStartEnddate() {
        try {
            String myFormatString = "dd-M-yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date enddate = df.parse(edt_enddate.getText().toString());
            Date startDate = df.parse(edt_startdate.getText().toString());

            if (enddate.after(startDate) || enddate.equals(startDate)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    private void saveOfferInDb(OfferCustomer job) {
        try {
            mJob.insert(job).get();
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d("ExceptionLog1", "ExceptionTextRRRR:" + e.toString());
        } catch (ExecutionException e) {
            Log.d("ExceptionLog2", "ExceptionTextRRRR:" + e.toString());
            e.printStackTrace();
        }
    }

    private OfferCustomer createNewJob() throws ParseException {

        String myFormatString = "dd-M-yyyy"; // for example
        SimpleDateFormat df = new SimpleDateFormat(myFormatString);
        Date endDate = df.parse(edt_enddate.getText().toString());
        Date startingDate = df.parse(edt_startdate.getText().toString());

        OfferCustomer job = new OfferCustomer();
        job.setCategorie(spinner_subcategorie.getSelectedItem().toString());
        job.setJob_subCategorie(spinner_categorie.getSelectedItem().toString());
        job.setOfferDescription(edt_job_description.getText().toString());
        job.setUserId(user.get(SessionManager.KEY_ID));
        job.setJob_startdatum(startingDate);
        job.setJob_enddate(endDate);
        job.setJobTitel(edt_jobtitel.getText().toString());
        job.setJob_index(edt_job_index.getText().toString());
        job.setJob_city(edt_job_city.getText().toString());
        return job;
    }

    private void getCalenderDialog(final int elementId) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if (elementId == edt_startdate.getId()) {
            DatePickerDialog dialogStart = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth, mDatesetListenerStartDate, year, month, day);
            dialogStart.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
            dialogStart.show();

            mDatesetListenerStartDate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    edt_startdate.setText(dayOfMonth + "-" + month + "-" + year);
                }
            };
        } else if (elementId == edt_enddate.getId()) {
            DatePickerDialog dialogEnd = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth, mDatesetListenerEndDate, year, month, day);
            dialogEnd.getWindow().setBackgroundDrawable(new ColorDrawable((Color.TRANSPARENT)));
            dialogEnd.show();

            mDatesetListenerEndDate = new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    month = month + 1;
                    edt_enddate.setText(dayOfMonth + "-" + month + "-" + year);
                }
            };
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        if (spinner_categorie.getSelectedItem().equals("Schönheit/Beaty")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.beauty_array, android.R.layout.simple_spinner_item);
            spinner_subcategorie.setAdapter(adapter2);
        } else if (spinner_categorie.getSelectedItem().equals("Handwerker")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.handwerker_array, android.R.layout.simple_spinner_item);
            spinner_subcategorie.setAdapter(adapter2);
        } else if (spinner_categorie.getSelectedItem().equals("Haushaltshilfe")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.haushaltshilfe_array, android.R.layout.simple_spinner_item);
            spinner_subcategorie.setAdapter(adapter2);
        } else if (spinner_categorie.getSelectedItem().equals("Web-/IT-Diensleistungen")) {
            ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this,
                    R.array.webdienstleistungen_array, android.R.layout.simple_spinner_item);
            spinner_subcategorie.setAdapter(adapter2);
        }
    }


    private ArrayList<String> uploadImage() {
        try {
            ArrayList<String> uriArray = new ArrayList<String>();
            if (!txt_photo1.getText().toString().equals("TextView"))
                uriArray.add(txt_photo1.getText().toString());

            if (!txt_photo2.getText().toString().equals("TextView"))
                uriArray.add(txt_photo2.getText().toString());

            if (!txt_photo3.getText().toString().equals("TextView"))
                uriArray.add(txt_photo3.getText().toString());

            ArrayList<String> imageNames = new ArrayList<String>();

            for (String uriString : uriArray) {
                Uri uri = Uri.parse(uriString);
                final InputStream imageStream = getContentResolver().openInputStream(uri);
                final int imageLength = imageStream.available();
                imageNames.add(ImageManager.UploadImage(imageStream, imageLength));
            }
            return imageNames;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}




