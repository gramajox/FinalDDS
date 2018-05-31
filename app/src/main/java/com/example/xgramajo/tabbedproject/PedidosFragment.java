package com.example.xgramajo.tabbedproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import java.util.ArrayList;

public class PedidosFragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    private ArrayList<ProductClass> selectedList = new ArrayList<>();
    private ArrayList<CommandClass> commandList = new ArrayList<>();

    private static ActiveCommandAdapter adapter;

    Button sendBtn, cancel1Btn, countBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pedidos_tab,container,false);

        NonScrollListView listViewActiveCommand = (NonScrollListView) view.findViewById(R.id.active_command_list);
        NonScrollListView listViewCommands = (NonScrollListView) view.findViewById(R.id.commands_list);

        adapter = new ActiveCommandAdapter(getActivity(), R.layout.adapter_selected_view, selectedList);
        listViewActiveCommand.setAdapter(adapter);

/**ACA ESTA EL ASUNTO
        selectedList.addAll(FirebaseController.getTableProducts());
*/
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
                FirebaseController.clearTableProducts();
                adapter.clear();
            }
        });

        countBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseController.setTableFree();
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
        FirebaseController.removeOneProduct(p);
        adapter.remove(p);
    }
}