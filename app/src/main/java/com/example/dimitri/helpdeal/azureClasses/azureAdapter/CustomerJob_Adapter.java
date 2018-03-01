package com.example.dimitri.helpdeal.azureClasses.azureAdapter;

import android.app.Activity;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.net.ParseException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.azureModels.OfferCustomer;

import java.util.Date;

public class CustomerJob_Adapter extends ArrayAdapter<OfferCustomer> {

    Context mContext;
    int mLayoutResourceId;

    public CustomerJob_Adapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final OfferCustomer offerCustomer = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.row_list_jobs, parent, false);
        }

        final TextView jobtitel = (TextView) row.findViewById(R.id.txt_job_titel);
        jobtitel.setText(offerCustomer.getJobTitel());

        final TextView index = (TextView) row.findViewById(R.id.txtrow_index);
        index.setText(offerCustomer.getJob_index());

        final TextView city = (TextView) row.findViewById(R.id.txtrow_city);
        city.setText(offerCustomer.getJob_city());

        final TextView startdate = (TextView) row.findViewById(R.id.txtrow_startdate);
        startdate.setText(parseDate(offerCustomer.getJob_startdate()));

        final TextView enddate = (TextView) row.findViewById(R.id.txtrow_enddate);
        enddate.setText(parseDate(offerCustomer.getJob_enddate()));
        row.setTag(offerCustomer);

        return row;
    }

    public String parseDate(Date date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
            String result = format.format(date);
            return result;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}