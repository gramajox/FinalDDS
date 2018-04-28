package com.example.xgramajo.tabbedproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PedidosFragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    private ListView listViewActiveCommand;
    private ListView listViewCommands;
    private ArrayList<ProductClass> selectedList;
    private ArrayList<CommandClass> commandList;

    private ProductListAdapter adapter;

    Button sendBtn, cancel1Btn, countBtn;

    final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    final DatabaseReference mDatabaseUsers = FirebaseDatabase.getInstance().getReference("Users");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pedidos_tab,container,false);

        View addButtonView = view.findViewById(R.id.send_button);
        addButtonView.setVisibility(View.GONE);
        View cancelButton1View = view.findViewById(R.id.cancel_button_1);
        cancelButton1View.setVisibility(View.GONE);/**
        View countButtonView = view.findViewById(R.id.count_button);
        countButtonView.setVisibility(View.GONE);*/

        listViewActiveCommand = (ListView) view.findViewById(R.id.active_command_list);
        listViewCommands = (ListView) view.findViewById(R.id.commands_list);

        CartaFragmentFB.ListUtils.setDynamicHeight(listViewActiveCommand);
        CartaFragmentFB.ListUtils.setDynamicHeight(listViewCommands);

        sendBtn = (Button) view.findViewById(R.id.send_button);
        cancel1Btn = (Button) view.findViewById(R.id.cancel_button_1);
        countBtn = (Button) view.findViewById(R.id.count_button);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        cancel1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedList.clear();
            }
        });

        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                mDatabase.child("Mesas").child("12345").child("Estado").setValue("Libre");
                mDatabase.child("Users").child(userId).child("Mesa").setValue("");
*/
                FirebaseController.setTableFree();
                }
        });

        return view;
    }

    /**Interfaz 1*/
    public void setProducts(ArrayList<ProductClass> products){

        if (products == null) {

        } else {

            for(int x = 0; x <= products.size(); x++){
                selectedList.add(products.get(x));
            }

            adapter = new ProductListAdapter(getActivity(), R.layout.adapter_products_view, selectedList);
            listViewActiveCommand.setAdapter(adapter);

            Toast.makeText(getContext(), "Llegaron: "+selectedList.size()+" productos a Pedidos.", Toast.LENGTH_LONG).show();
        }
    }
}