package com.example.xgramajo.tabbedproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PedidosFragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    private ListView listViewActiveCommand;
    private ListView listViewCommands;
    private ArrayList<String> namesSelectedList;
    private ArrayList<ProductClass> selectedList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pedidos_tab,container,false);



        /**Interface*/
        /*Bundle bundle = getArguments();

        ArrayList<ProductClass> selectedList = bundle.getParcelableArrayList("products");

        for(int x=0; x == selectedList.size() ; x++) {
            namesSelectedList.add(selectedList.get(x).getName());
        }

/*
        listViewActiveCommand = (ListView) view.findViewById(R.id.active_command_list);
        listViewCommands = (ListView) view.findViewById(R.id.commands_list);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_list_item_1,namesSelectedList);
        listViewActiveCommand.setAdapter(adapter);
*/

        return view;
    }

    public void setProducts(ArrayList<ProductClass> selectedList){

    }
}