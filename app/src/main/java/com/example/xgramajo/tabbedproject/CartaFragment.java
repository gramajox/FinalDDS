package com.example.xgramajo.tabbedproject;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CartaFragment extends Fragment {

    private ListView listViewProducts;
    private ArrayList<ProductClass> products = new ArrayList<>();
    private ProductListAdapter adapter;

    private static final String TAG = "Tab1Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.carta_tab, container, false);

        populateListView();

        return view;
    }

    public void populateListView() {
        listViewProducts = (ListView) getActivity().findViewById(R.id.listOfProducts);
        products.add(new ProductClass("milanesa","Alta Milanga","Minutas",135));
        products.add(new ProductClass("milanesa","Alta Milanga","Minutas",135));
        products.add(new ProductClass("milanesa","Alta Milanga","Minutas",135));
        products.add(new ProductClass("milanesa","Alta Milanga","Minutas",135));
        products.add(new ProductClass("milanesa","Alta Milanga","Minutas",135));


        adapter = new ProductListAdapter(getActivity(), products);
        listViewProducts.setAdapter(adapter);
    }

}