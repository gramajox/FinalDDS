package com.example.xgramajo.tabbedproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapter extends BaseAdapter{

    private Activity context;
    private ArrayList<ProductClass> products;
    private static LayoutInflater inflater = null;

    public ProductListAdapter(Activity context, ArrayList<ProductClass> products) {
        this.context = context;
        this.products = products;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = convertView;

        itemView = (itemView == null) ? inflater.inflate(R.layout.adapter_products_view, null): itemView;

        TextView textViewName = (TextView) itemView.findViewById(R.id.textView1);
        TextView textViewDescription = (TextView) itemView.findViewById(R.id.textView2);

        ProductClass selectedProduct = products.get(position);

        textViewName.setText(selectedProduct.getName());
        textViewDescription.setText(selectedProduct.getDescription());

        return itemView;
    }
}