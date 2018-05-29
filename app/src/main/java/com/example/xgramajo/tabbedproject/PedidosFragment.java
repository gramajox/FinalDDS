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
import java.util.ArrayList;

public class PedidosFragment extends Fragment {
    private static final String TAG = "Tab3Fragment";

    private ListView listViewActiveCommand;
    private ListView listViewCommands;
    private ArrayList<ProductClass> selectedList = new ArrayList<>();
    private ArrayList<CommandClass> commandList = new ArrayList<>();

    private static ActiveCommandAdapter adapter;

    Button sendBtn, cancel1Btn, countBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pedidos_tab,container,false);

        listViewActiveCommand = (ListView) view.findViewById(R.id.active_command_list);
        listViewCommands = (ListView) view.findViewById(R.id.commands_list);

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
                Toast.makeText(getContext(),"Size de Historial: " + CartaFragment.userHistoryString.size(), Toast.LENGTH_LONG).show();

                }
        });

        return view;
    }

    /**Interfaz 1*/
    public void setProducts(ArrayList<ProductClass> products){

        if (products == null) {
            Toast.makeText(getContext(),"La lista enviada esta vacia.", Toast.LENGTH_LONG).show();
        }
        else {
            adapter.addAll(products);
        }

        Toast.makeText(getContext(), "Hay "+selectedList.size()+" productos en ComandaActual.", Toast.LENGTH_LONG).show();

    }
    public static void removeFromCommand(ProductClass p) {
        FirebaseController.removeOneProduct(p);
        adapter.remove(p);
    }
}