package com.example.dimitri.helpdeal.azureClasses.activities.modulCustomer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dimitri.helpdeal.R;

import java.io.File;

public class Gallery_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_MEDIA = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView gallery_view, camera_view;
    private TextView txt_categorie, txt_subcategorie, txt_endDate, txt_startDate,
                     txt_job_description, txt_jobtitel,
                     txt_photo1, txt_photo2, txt_photo3, txt_pictureNumber, txt_index, txt_city;
    private static final int IMAGE_GALLERY_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_layout);

        gallery_view = (ImageView) findViewById(R.id.gallery_imageview);
        gallery_view.setOnClickListener(this);
        camera_view = (ImageView) findViewById(R.id.camera_imageview);
        camera_view.setOnClickListener(this);

        txt_categorie = (TextView) findViewById(R.id.txt_categorie);
        txt_subcategorie = (TextView) findViewById(R.id.txt_subcategorie);
        txt_startDate = (TextView) findViewById(R.id.txt_startDate);
        txt_endDate = (TextView) findViewById(R.id.txt_endDate);
        txt_job_description = (TextView) findViewById(R.id.txt_job_description);
        txt_jobtitel = (TextView) findViewById(R.id.txt_jobtitel);
        txt_index = (TextView) findViewById(R.id.txt_index);
        txt_city = (TextView) findViewById(R.id.txt_city);
        txt_photo1 = (TextView) findViewById(R.id.txt_photo1);
        txt_photo2 = (TextView) findViewById(R.id.txt_photo2);
        txt_photo3 = (TextView) findViewById(R.id.txt_photo3);
        txt_pictureNumber = (TextView) findViewById(R.id.txt_pictureNumber);

        Intent intent = getIntent();
        txt_categorie.setText(intent.getStringExtra("categorie"));
        txt_subcategorie.setText(intent.getStringExtra("subcategorie"));
        txt_jobtitel.setText(intent.getStringExtra("edt_jobtitel"));
        txt_job_description.setText(intent.getStringExtra("job_description"));
        txt_index.setText(intent.getStringExtra("index"));
        txt_city.setText(intent.getStringExtra("city"));
        txt_startDate.setText(intent.getStringExtra("startdate"));
        txt_endDate.setText(intent.getStringExtra("enddate"));
        txt_photo1.setText(intent.getStringExtra("photo1"));
        txt_photo2.setText(intent.getStringExtra("photo2"));
        txt_photo3.setText(intent.getStringExtra("photo3"));
        txt_pictureNumber.setText(intent.getStringExtra("pictureNumber"));

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = 600;
        int height = 350;
        getWindow().setLayout((int) width, (int) height);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gallery_imageview:
                onImageGalleryClicked(v);
                break;
            case R.id.camera_imageview:
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_MEDIA);
        } else {

            Intent cat = new Intent(this, CategorieCustomer_Activity.class);
            startActivity(cat);

            Intent pop_activity = new Intent(this, PopUp_Activity.class);
            pop_activity.putExtra("edt_jobtitel", txt_jobtitel.getText().toString());
            pop_activity.putExtra("job_description", txt_job_description.getText().toString());
            pop_activity.putExtra("startdate", txt_startDate.getText().toString());
            pop_activity.putExtra("enddate", txt_endDate.getText().toString());
            pop_activity.putExtra("categorie", txt_categorie.getText().toString());
            pop_activity.putExtra("subcategorie", txt_subcategorie.getText().toString());
            pop_activity.putExtra("pictureNumber", txt_pictureNumber.getText().toString());
            pop_activity.putExtra("index", txt_index.getText().toString());
            pop_activity.putExtra("city", txt_city.getText().toString());

            if (resultCode == RESULT_OK) {
                // if we are here, we are hearing back from the image gallery.
                if (requestCode == IMAGE_GALLERY_REQUEST || requestCode == CAMERA_REQUEST) {
                    //the adress of the image on the sdCard
                    Uri imageUri = data.getData();

                    if (txt_photo1.getText().length() > 0) {
                        pop_activity.putExtra("photo1", txt_photo1.getText().toString());
                    }
                    if (txt_photo2.getText().length() > 0) {
                        pop_activity.putExtra("photo2", txt_photo2.getText().toString());
                    }
                    if (txt_photo3.getText().length() > 0) {
                        pop_activity.putExtra("photo3", txt_photo3.getText().toString());
                    }

                    if (txt_pictureNumber.getText().equals("picture1") && imageUri != null) {
                        txt_photo1.setText(imageUri.toString());
                        pop_activity.putExtra("photo1", txt_photo1.getText());
                    } else if (txt_pictureNumber.getText().equals("picture2") && imageUri != null) {
                        txt_photo2.setText(imageUri.toString());
                        pop_activity.putExtra("photo2", txt_photo2.getText());
                    } else if (txt_pictureNumber.getText().equals("picture3") && imageUri != null) {
                        txt_photo3.setText(imageUri.toString());
                        pop_activity.putExtra("photo3", txt_photo3.getText());
                    }

                    try {
                        Bitmap photo = (Bitmap) data.getExtras().get("data");
                        if (photo != null) {
                            pop_activity.putExtra("imageDate", photo);
                        }
                    } catch (Exception e) {

                    }
                    startActivity(pop_activity);
                }
            }
        }
    }

    public void onImageGalleryClicked(View v) {
        // invoke the image gallery using an implict intent
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        //where do we want to find the data
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        //finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);
        //set the date and typ , get all image types
        photoPickerIntent.setDataAndType(data, "image/*");
        //we will invoke this activity, and get something back from it.
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }
}

