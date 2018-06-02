package com.example.xgramajo.tabbedproject;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class CartaFragment extends Fragment {

    private static ProductListAdapter adapter1;
    private static SelectedListAdapter adapter2;

    private static ArrayList<ProductClass> products = new ArrayList<>();
    private static ArrayList<ProductClass> selectedProducts = new ArrayList<>();

    public static ArrayList<String> userHistoryString = new ArrayList<>();

    public FirebaseController mFirebaseController = new FirebaseController();

    /**Interfaz 1*/
    private SendProducts sendProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carta_tab, container, false);

        NonScrollListView listViewProducts = (NonScrollListView) view.findViewById(R.id.productsList);
        NonScrollListView listViewSelectedProducts = (NonScrollListView) view.findViewById(R.id.selProdList);

        adapter1 = new ProductListAdapter(getActivity(), R.layout.adapter_products_view, products);
        adapter2 = new SelectedListAdapter(getActivity(), R.layout.adapter_selected_view, selectedProducts);

        listViewProducts.setAdapter(adapter1);
        listViewSelectedProducts.setAdapter(adapter2);

        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(getActivity(), ProductActivity.class);
                myIntent.putExtra("Product Item", products.get(i));

                startActivity(myIntent);
            }
        });

        listViewSelectedProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(getActivity(), ProductActivity.class);
                myIntent.putExtra("Product Item", selectedProducts.get(i));

                startActivity(myIntent);
            }
        });

        Button addButton = (Button) view.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mFirebaseController.addProductsInTable(selectedProducts);
                sendProducts.setSelectedList(selectedProducts);
                adapter2.clear();
            }
        });

        return view;
    }

    /**Interfaz 1*/
    public interface SendProducts {
        public void setSelectedList(ArrayList<ProductClass> selectedList);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            sendProducts = (SendProducts) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement SendProducts");
        }
    }

    /**Funcion que usa el ProductListAdapter para agregar los productos a selectProducts*/
    public static void selectProductFromList(ProductClass p) {
        adapter2.add(p);
    }

    public static void removeFromSelected(ProductClass p) {
        adapter2.remove(p);
    }

    public static void loadAllProducts(ArrayList<ProductClass> list) {
        adapter1.clear();
        adapter1.addAll(list);
    }

    @Override
    public void onStart() {
        super.onStart();
        mFirebaseController.getAllProducts();
        Log.d("CartaFragment", "products.size()" + products.size());
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}