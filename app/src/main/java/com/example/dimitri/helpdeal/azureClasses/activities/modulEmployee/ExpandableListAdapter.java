package com.example.dimitri.helpdeal.azureClasses.activities.modulEmployee;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.dimitri.helpdeal.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Dimitri on 23.02.2018.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listdataHaeder;
    private HashMap<String,List<String>>listHashMap;


    public ExpandableListAdapter(Context context, List<String> listdataHaeder, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listdataHaeder = listdataHaeder;
        this.listHashMap = listHashMap;
    }

    @Override
    public int getGroupCount() {
        return listdataHaeder.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listHashMap.get(listdataHaeder.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listdataHaeder.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listHashMap.get(listdataHaeder.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(R.layout.list_group,null);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition,childPosition);

        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,null);
        }

        TextView textListChildView = (TextView) convertView.findViewById(R.id.lblListItem);
        textListChildView.setText(childText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}