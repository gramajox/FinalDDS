package com.example.xgramajo.tabbedproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapter extends ArrayAdapter<ProductClass> {

    private static final String TAG = "ProductListAdapter";

    private Context mContext;
    private int mResource;

    /**
     * Default constructor for the ProductListAdapter
     * @param context
     * @param resource
     * @param objects
     */
    public ProductListAdapter(Context context, int resource, ArrayList<ProductClass> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView description;
        TextView category;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String name = getItem(position).getName();
        String description = getItem(position).getDescription();
        String category = getItem(position).getCategory();
        Integer price = getItem(position).getPrice();

        //Create the product object with the information
        ProductClass product = new ProductClass(name,description,category,price);

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView1);
            holder.description = (TextView) convertView.findViewById(R.id.textView2);
            holder.category = (TextView) convertView.findViewById(R.id.textView3);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());
        holder.category.setText(product.getCategory());

        return convertView;
    }
}