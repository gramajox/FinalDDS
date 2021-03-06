package com.example.xgramajo.tabbedproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.xgramajo.tabbedproject.MenuFragment;
import com.example.xgramajo.tabbedproject.ProductClass;
import com.example.xgramajo.tabbedproject.R;

import java.util.ArrayList;

public class SelectedListAdapter extends ArrayAdapter<ProductClass> {

    private ArrayList<ProductClass> products;
    private Context context;
    private int layoutResourceId;

    private SparseBooleanArray mSelectedItemsIds;

    /** Adapter con Holder */
    public SelectedListAdapter(Context context, int layoutResourceId, ArrayList<ProductClass> products) {
        super(context, layoutResourceId, products);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.products = products;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        SelectedListAdapter.PaymentHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        view = inflater.inflate(layoutResourceId, parent, false);

        holder = new SelectedListAdapter.PaymentHolder();
        holder.Product = products.get(position);
        holder.button = (Button) view.findViewById(R.id.product_button);

        holder.name = (TextView) view.findViewById(R.id.product_name);
        holder.price = (TextView) view.findViewById(R.id.product_price);

        view.setTag(holder);

        holder.name.setText(holder.Product.getName());
        holder.price.setText("$" + String.valueOf(holder.Product.getPrice()));

        holder.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                MenuFragment.removeFromSelected(products.get(position));
            }
        });

        return view;
    }

    public static class PaymentHolder {
        ProductClass Product;
        TextView name;
        Button button;
        TextView price;
    }

    @Override
    public int getCount() {
        return products.size();
    }
}