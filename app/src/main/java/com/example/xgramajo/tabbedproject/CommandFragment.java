package com.example.xgramajo.tabbedproject;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.xgramajo.tabbedproject.Adapters.ActiveCommandAdapter;
import com.example.xgramajo.tabbedproject.Adapters.NonScrollListView;

import java.util.ArrayList;

public class CommandFragment extends Fragment {
    private final String TAG = "Tab3Fragment";

    private ArrayList<ProductClass> selectedList = new ArrayList<>();

    private static ActiveCommandAdapter adapter;

    Button sendBtn, cancel1Btn, countBtn;

    public static FirebaseController mFirebaseController = new FirebaseController();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pedidos_tab,container,false);

        NonScrollListView listViewActiveCommand = (NonScrollListView) view.findViewById(R.id.active_command_list);
        NonScrollListView listViewCommands = (NonScrollListView) view.findViewById(R.id.commands_list);

        adapter = new ActiveCommandAdapter(getActivity(), R.layout.adapter_selected_view, selectedList);
        listViewActiveCommand.setAdapter(adapter);

        sendBtn = (Button) view.findViewById(R.id.send_button);
        cancel1Btn = (Button) view.findViewById(R.id.cancel_button_1);
        countBtn = (Button) view.findViewById(R.id.count_button);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseController.saveProducts(selectedList);
                mFirebaseController.clearTableProducts();
                adapter.clear();
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.finish_dialog, null);
                Button backBtn = (Button) mView.findViewById(R.id.button2);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                backBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent backHome = new Intent(getContext(), HomeActivity.class);
                        startActivity(backHome);
                    }
                });
            }
        });

        cancel1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFirebaseController.clearTableProducts();
                adapter.clear();
            }
        });

        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedList.size() == 0) {
                    mFirebaseController.setTableFree();
                } else {
                    Toast.makeText(getContext(), "Existe una Comanda Activa.", Toast.LENGTH_LONG).show();
                }

            }
        });

        return view;
    }

    /**Interfaz 1*/
    public void setProducts(ArrayList<ProductClass> products){
        if (products.size() != 0) {
            adapter.addAll(products);
        }
    }

    public static void removeFromCommand(ProductClass p) {
        mFirebaseController.removeOneProduct(p);
        adapter.remove(p);
    }

    public static void loadTableProducts(ArrayList<ProductClass> list) {
        adapter.clear();
        adapter.addAll(list);
    }

}