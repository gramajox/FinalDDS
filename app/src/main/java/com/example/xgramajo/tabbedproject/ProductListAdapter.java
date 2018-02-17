package com.example.xgramajo.tabbedproject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductListAdapter /*extends BaseAdapter*/ extends ArrayAdapter<ProductClass> {

    private ArrayList<ProductClass> products;

    private Context context;
    private int layoutResourceId;

    /**ESTO ES CON HOLDER*/

    public ProductListAdapter(Context context, int layoutResourceId, ArrayList<ProductClass> products) {
        super(context, layoutResourceId, products);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.products = products;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        PaymentHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new PaymentHolder();
        holder.Product = products.get(position);
        holder.ProductButton = (ImageButton)row.findViewById(R.id.product_button);
        holder.ProductButton.setTag(holder.Product);

        holder.name = (TextView)row.findViewById(R.id.product_name);
        holder.description = (TextView)row.findViewById(R.id.product_description);
        holder.price = (TextView)row.findViewById(R.id.product_price);

        row.setTag(holder);

        setupItem(holder);
        return row;
    }

    private void setupItem(PaymentHolder holder) {
        holder.name.setText(holder.Product.getName());
        holder.description.setText(holder.Product.getDescription());
        holder.price.setText("$"+String.valueOf(holder.Product.getPrice()));
    }

    public static class PaymentHolder {
        ProductClass Product;
        TextView name;
        TextView description;
        ImageButton ProductButton;
        TextView price;
    }

    @Override
    public int getCount() {
        return products.size();
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

}