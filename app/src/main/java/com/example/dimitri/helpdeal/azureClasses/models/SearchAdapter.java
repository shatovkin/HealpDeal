package com.example.dimitri.helpdeal.azureClasses.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.dimitri.helpdeal.R;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter implements Filterable {

    Context context;
    ArrayList<Category> categories;
    CustomFilter filter;
    ArrayList<Category> filterList;

    public SearchAdapter(Context ctx, ArrayList<Category> categories)
    {
        this.context = ctx;
        this.categories= categories;
        this.filterList=categories;
    }
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return categories.indexOf(getItem(position));
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if(convertView==null)
                convertView = inflater.inflate(R.layout.list_category,null);

        TextView filterField = (TextView) convertView.findViewById(R.id.imageTextView);
        ImageView img=(ImageView) convertView.findViewById(R.id.icon);

        filterField.setText(categories.get(pos).getName());
        img.setImageResource(categories.get(pos).getImg());
        return convertView;
    }



    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }
        return filter;
    }

    //INNER CLASS
    private class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results=new FilterResults();
            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();
                ArrayList<Category> filters=new ArrayList<Category>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint))
                    {
                        Category category=new Category(filterList.get(i).getName(), filterList.get(i).getImg());
                        filters.add(category);
                    }
                }
                results.count=filters.size();
                results.values=filters;
            }else
            {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            categories=(ArrayList<Category>) results.values;
            notifyDataSetChanged();
        }
    }
}
