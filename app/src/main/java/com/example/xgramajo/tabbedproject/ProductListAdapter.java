package com.example.xgramajo.tabbedproject;

import android.app.Activity;
import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ProductListAdapter extends ArrayAdapter<ProductClass> {

    private ArrayList<ProductClass> products;
    private Context context;
    private int layoutResourceId;

    private SparseBooleanArray mSelectedItemsIds;

    /**Adapter con Holder*/

    public ProductListAdapter(Context context, int layoutResourceId, ArrayList<ProductClass> products) {
        super(context, layoutResourceId, products);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.products = products;
        mSelectedItemsIds = new SparseBooleanArray();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        PaymentHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        view = inflater.inflate(layoutResourceId, parent, false);

        holder = new PaymentHolder();
        holder.Product = products.get(position);
        holder.checkBox = (CheckBox)view.findViewById(R.id.checkbox_select);
        holder.checkBox.setTag(holder.Product);

        holder.name = (TextView)view.findViewById(R.id.product_name);
        holder.description = (TextView)view.findViewById(R.id.product_description);
        holder.price = (TextView)view.findViewById(R.id.product_price);

        view.setTag(holder);

        /**setupItem(holder);*/

        holder.name.setText(holder.Product.getName());
        holder.description.setText(holder.Product.getDescription());
        holder.price.setText("$"+String.valueOf(holder.Product.getPrice()));

        holder.checkBox.setChecked(mSelectedItemsIds.get(position));


        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**ACA TENGO QUE HACER QUE SE AGREGUE A selProdList
                 * AGREGAR VARIABLE EN CLASE PRODUCTO? QUE SETEE UN VALOR...*/
                checkCheckBox(position, !mSelectedItemsIds.get(position));
            }
        });

        return view;
    }
/**ESTO FUNCIONA.....
    private void setupItem(PaymentHolder holder) {
        holder.name.setText(holder.Product.getName());
        holder.description.setText(holder.Product.getDescription());
        holder.price.setText("$"+String.valueOf(holder.Product.getPrice()));

    }
 */
    public static class PaymentHolder {
        ProductClass Product;
        TextView name;
        TextView description;
        CheckBox checkBox;
        TextView price;
    }

    /**
     * Check the Checkbox if not checked
     **/
    public void checkCheckBox(int position, boolean value) {
        if (value)
            mSelectedItemsIds.put(position, true);
        else
            mSelectedItemsIds.delete(position);

        notifyDataSetChanged();
    }

    /**
     * Return the selected Checkbox IDs
     **/
    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }
        /*********************************************/
    @Override
    public int getCount() {
        return products.size();
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

}