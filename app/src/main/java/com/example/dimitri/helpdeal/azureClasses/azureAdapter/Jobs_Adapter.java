package com.example.dimitri.helpdeal.azureClasses.azureAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.dimitri.helpdeal.R;
import com.example.dimitri.helpdeal.azureClasses.azureModels.OfferCustomer;

/**
 * Created by Dimitri on 17.11.2017.
 */

public class Jobs_Adapter extends ArrayAdapter<OfferCustomer> {

    private Context mContext;
    private int mLayoutResourceId;

    public Jobs_Adapter(Context context, int layoutResourceId) {
        super(context, layoutResourceId);
        mContext = context;
        mLayoutResourceId = layoutResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;

        final OfferCustomer job = getItem(position);

        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(R.layout.row_list_jobs, parent, false);
        }
        row.setTag(job);
        return row;
    }
}
