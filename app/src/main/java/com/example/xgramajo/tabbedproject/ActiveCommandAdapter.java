package com.example.xgramajo.tabbedproject;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ActiveCommandAdapter extends ArrayAdapter<ProductClass> {

    private ArrayList<ProductClass> products;
    private Context context;
    private int layoutResourceId;

    private SparseBooleanArray mSelectedItemsIds;

    /** Adapter con Holder */
    public ActiveCommandAdapter(Context context, int layoutResourceId, ArrayList<ProductClass> products) {
        super(context, layoutResourceId, products);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.products = products;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ActiveCommandAdapter.PaymentHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        view = inflater.inflate(layoutResourceId, parent, false);

        holder = new ActiveCommandAdapter.PaymentHolder();
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
                PedidosFragment.removeFromCommand(products.get(position));
            }
        });

        return view;
    }

    public class PaymentHolder {
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