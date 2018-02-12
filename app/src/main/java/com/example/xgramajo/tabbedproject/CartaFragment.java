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

        listViewProducts = (ListView) view.findViewById(R.id.listOfProducts);

        ProductClass BifeChorizo = new ProductClass("Bife de chorizo","Una descripción del plato, con los ingredientes y presentación.","Carnes",135);
        ProductClass EmpanadaCarne = new ProductClass("Empanadas de carne","Una descripción del plato, con los ingredientes y presentación.","Minutas",135);
        ProductClass Sorrentinos = new ProductClass("Sorrentinos","Una descripción del plato, con los ingredientes y presentación.","Pastas",135);
        ProductClass ChorizoCriollo = new ProductClass("Chorizo criollo","Una descripción del plato, con los ingredientes y presentación.","Minutas",135);
        ProductClass Milanesas = new ProductClass("Milanesas","Una descripción del plato, con los ingredientes y presentación.","Minutas",135);

        products.add(BifeChorizo);
        products.add(EmpanadaCarne);
        products.add(Sorrentinos);
        products.add(ChorizoCriollo);
        products.add(Milanesas);
        products.add(BifeChorizo);
        products.add(EmpanadaCarne);
        products.add(Sorrentinos);
        products.add(ChorizoCriollo);
        products.add(Milanesas);
        products.add(BifeChorizo);
        products.add(EmpanadaCarne);
        products.add(Sorrentinos);
        products.add(ChorizoCriollo);
        products.add(Milanesas);

        adapter = new ProductListAdapter(getActivity(), products);
        listViewProducts.setAdapter(adapter);

        //populateListView();

        return view;
    }
/**
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
*/
}