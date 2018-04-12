package com.example.xgramajo.tabbedproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PedidosFragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    private ListView listViewActiveCommand;
    private ListView listViewCommands;
    private ArrayList<ProductClass> selectedList;

    private ProductListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pedidos_tab,container,false);

        listViewActiveCommand = (ListView) view.findViewById(R.id.active_command_list);
        listViewCommands = (ListView) view.findViewById(R.id.commands_list);


        return view;
    }

    /**Interfaz 1*/
    public void setProducts(ArrayList<ProductClass> products){

        if (products == null) {

        } else {

            selectedList = products;

            adapter = new ProductListAdapter(getActivity(), R.layout.adapter_products_view, selectedList);
            listViewActiveCommand.setAdapter(adapter);

            Toast.makeText(getContext(), "Llegaron: "+selectedList.size()+" productos a Pedidos.", Toast.LENGTH_LONG).show();
        }
    }
}