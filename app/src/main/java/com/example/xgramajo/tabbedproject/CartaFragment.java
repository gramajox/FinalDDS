package com.example.xgramajo.tabbedproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class CartaFragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.carta_tab,container,false);

        /**ListView mListView = (ListView) getView().findViewById(R.id.listOfProducts);*/

        //Products Objects
        ProductClass milanesa = new ProductClass("milanesa","Alta Milanga","Minutas",135);
        ProductClass fideos = new ProductClass("fideos","Altos Fideos","Pastas",135);
        ProductClass tarta = new ProductClass("tarta","Alta Tarta","Tartas",135);
        ProductClass lomo = new ProductClass("lomo","Alto Lomo","Carnes",135);
        ProductClass pollo = new ProductClass("pollo","Alto Pollo","Pollos",135);

        //Add the Products to an ArrayList

        ArrayList<ProductClass> productList = new ArrayList<>();

        productList.add(milanesa);
        productList.add(fideos);
        productList.add(tarta);
        productList.add(lomo);
        productList.add(pollo);

        ProductListAdapter adapter = new ProductListAdapter(getActivity(),R.layout.adapter_products_view,productList);

        /**mListView.setAdapter(adapter);*/

        return view;
    }

}