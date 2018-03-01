package com.example.dimitri.helpdeal.azureClasses.azureAdapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.azureModels.BranchOfferView;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.net.URL;

public class EmployeeOffer_Adapter extends ArrayAdapter<BranchOfferView> {

    private Context mContext;
    private int mLayoutResourceId;


    public EmployeeOffer_Adapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final BranchOfferView branchOfferView = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.row_list_employee_offer, parent, false);
            calculateRanking(calculateRanking(branchOfferView), row);
            calculateVarification(branchOfferView,row);
        }

        final TextView txt_employee_name = (TextView) row.findViewById(R.id.txt_employee_name);
        txt_employee_name.setText(branchOfferView.getUserFirstname()+" " + getCutName(branchOfferView.getUserName())+".");

        final TextView job_Experience = (TextView) row.findViewById(R.id.txt_job_experience);

        if (branchOfferView.getEmp_experience().toString().equals("1")) {
            job_Experience.setText(branchOfferView.getEmp_experience() + " Jahr");
        } else {
            job_Experience.setText(branchOfferView.getEmp_experience() + " Jahre");
        }

        final TextView salary_per_hour = (TextView) row.findViewById(R.id.txt_salary_per_hour);
        salary_per_hour.setText("Ab " + branchOfferView.getEmp_salary_per_hour() + "€/St");
        row.setTag(branchOfferView);

        if(branchOfferView.getPhoto().length() >0) {
            ImageView photo = (ImageView) row.findViewById(R.id.userPhoto);
            Picasso.with(mContext).load(branchOfferView.getPhoto()).into(photo);
        }

        return row;
    }

    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
            logo = BitmapFactory.decodeStream(is);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }


    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        String s= "";
    }
    private void calculateVarification(BranchOfferView offerView,View row) {
        int varificationCount = 0;

        if (offerView.getVarificationEmail() == true) {
            varificationCount++;
            ImageView imageMail = (ImageView) row.findViewById(R.id.email_varification);
            imageMail.setImageResource(R.drawable.email_checked);
        }
        if (offerView.getVarificationPhone() == true) {
            varificationCount++;
            ImageView imageMail = (ImageView) row.findViewById(R.id.telefon_varification);
            imageMail.setImageResource(R.drawable.smartphone_checked);

        }
        if (offerView.getVarificationPass() == true) {
            varificationCount++;
            ImageView imageMail = (ImageView) row.findViewById(R.id.pass_varification);
            imageMail.setImageResource(R.drawable.pass_checked);
        }
       // TextView countCarification = (TextView) row.findViewById(R.id.txt_ranking_count);
      //  countCarification.setText("Stufe " + varificationCount);
    }

    private void calculateRanking(int ranking, View row) {
        if (ranking == 1) {
            ImageView rankinImage = (ImageView) row.findViewById(R.id.ranking_image);
            rankinImage.setImageResource(R.drawable.stern_1);
            TextView satisfaction = (TextView) row.findViewById(R.id.txt_satisfaction);
            satisfaction.setText("Unbefriedigend");
            satisfaction.setTextColor(row.getResources().getColor(R.color.red_satisfaction));
        } else if (ranking == 2) {
            ImageView rankinImage = (ImageView) row.findViewById(R.id.ranking_image);
            rankinImage.setImageResource(R.drawable.stern_2);
            TextView satisfaction = (TextView) row.findViewById(R.id.txt_satisfaction);
            satisfaction.setText("Befriedigend");
            satisfaction.setTextColor(row.getResources().getColor(R.color.red_satisfaction));
        } else if (ranking == 3) {
            ImageView rankinImage = (ImageView) row.findViewById(R.id.ranking_image);
            rankinImage.setImageResource(R.drawable.stern_3);
            TextView satisfaction = (TextView) row.findViewById(R.id.txt_satisfaction);
            satisfaction.setText("Befriedigend");
            satisfaction.setTextColor(row.getResources().getColor(R.color.yellow_satisfaction));
        } else if (ranking == 4) {
            ImageView rankinImage = (ImageView) row.findViewById(R.id.ranking_image);
            rankinImage.setImageResource(R.drawable.stern_4);
            TextView satisfaction = (TextView) row.findViewById(R.id.txt_satisfaction);
            satisfaction.setText("Gut");
            satisfaction.setTextColor(row.getResources().getColor(R.color.yellow_satisfaction));
        } else if (ranking == 5) {
            ImageView rankinImage = (ImageView) row.findViewById(R.id.ranking_image);
            rankinImage.setImageResource(R.drawable.stern_5);
            TextView satisfaction = (TextView) row.findViewById(R.id.txt_satisfaction);
            satisfaction.setText("Sehr gut");
            satisfaction.setTextColor(row.getResources().getColor(R.color.green_satisfaction));
        }

      /*  TextView rankingText = (TextView) row.findViewById(R.id.txt_ranking);
        rankingText.setText("" + ranking);*/
    }

    public int calculateRanking(BranchOfferView offerView) {
        return Integer.parseInt(offerView.getSummeOfRating()) / Integer.parseInt(offerView.getCountOfRating());
    }

    private String getCutName(String name) {
        return name.substring(0,1);
    }

    /* public static int rnd(int min, int max) {
        max -= min;
        return (int) (Math.random() * ++max) + min;
    }*/
}