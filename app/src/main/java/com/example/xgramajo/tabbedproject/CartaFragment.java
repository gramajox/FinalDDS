package com.example.xgramajo.tabbedproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class CartaFragment extends Fragment {

    private ListView listViewProducts;
    private ListView listViewSelectedProducts;
    private ArrayList<ProductClass> products = new ArrayList<>();
    private ArrayList<ProductClass> selectedProducts = new ArrayList<>();
    private ProductListAdapter adapter1;
    private ProductListAdapter adapter2;

    /**Comunicaciones fragments*/
    private SendProducts sendProducts;

    /*private static final String TAG = "Tab1Fragment";*/


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carta_tab, container, false);

        /**SE CREAN LOS PRODUCTOS Y SE COMPLETA LA LISTA*/
        final ProductClass Rabas =
                new ProductClass("Rabas", "Con Alioli.",
                        "Minutas", 135, R.drawable.rabas);
        final ProductClass EmpanadaCarne =
                new ProductClass("Empanadas de carne", "Carne suave, cortada a cuchillo, picante.",
                        "Minutas", 200, R.drawable.empanadas);
        final ProductClass TablaFiambres =
                new ProductClass("Tabla: Friambres y Quesos", "Jamon cocido y crudo, lomito ahumado, bondiola, salamines picado fino, grueso y candelario, Queso sardo, gruyere y gouda",
                        "Pastas", 230, R.drawable.tabla_fiambres);
        final ProductClass HamburguesaCasera =
                new ProductClass("Hamburguesa Casera", "Con cebolla caramelizada, rucula y cheddar.",
                        "Minutas", 100, R.drawable.hamburguesa);
        final ProductClass PizzaCapresse =
                new ProductClass("Pizza Capresse", "Mozzarella, tomate, albahaca, aceitunas verdes.",
                        "Minutas", 150, R.drawable.pizza);

        products.add(Rabas);products.add(EmpanadaCarne);products.add(TablaFiambres);products.add(HamburguesaCasera);products.add(PizzaCapresse);
        products.add(Rabas);products.add(EmpanadaCarne);products.add(TablaFiambres);products.add(HamburguesaCasera);products.add(PizzaCapresse);
        products.add(Rabas);products.add(EmpanadaCarne);products.add(TablaFiambres);products.add(HamburguesaCasera);products.add(PizzaCapresse);


        /**ADAPTER CON HOLDER LISTA PRODUCTOS*/
        listViewProducts = (ListView) view.findViewById(R.id.productsList);
        adapter1 = new ProductListAdapter(getActivity(), R.layout.adapter_products_view, products);
        listViewProducts.setAdapter(adapter1);

        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(getActivity(), ProductActivity.class);
                myIntent.putExtra("Product Item", products.get(i));

                startActivity(myIntent);
            }
        });


        /**ADAPTER CON HOLDER LISTA SELECCIONADOS*/
        listViewSelectedProducts = (ListView) view.findViewById(R.id.selProdList);
        adapter2 = new ProductListAdapter(getActivity(), R.layout.adapter_products_view, selectedProducts);
        listViewSelectedProducts.setAdapter(adapter2);

        listViewSelectedProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(getActivity(), ProductActivity.class);
                myIntent.putExtra("Product Item", selectedProducts.get(i));

                startActivity(myIntent);
            }
        });

        /**ESTO SOLUCIONA LA VISTA DE VARIAS LISTVIEW EN LA MISMA PANTALLA*/
        ListUtils.setDynamicHeight(listViewProducts);
        ListUtils.setDynamicHeight(listViewSelectedProducts);

        /**CHECKBOXES*/
        Button addButton = (Button) view.findViewById(R.id.add_button);
        Button testButton = (Button) view.findViewById(R.id.testing_button);

        final LinearLayout layoutSelected = (LinearLayout) view.findViewById(R.id.selected_view);
        layoutSelected.setVisibility(GONE);

        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //adapter2.clear();
                selectedProducts.add(Rabas);
                adapter2.notifyDataSetChanged();
                layoutSelected.setVisibility(View.VISIBLE);

                Toast.makeText(getActivity(), "Productos seleccionados: " + selectedProducts.size(), Toast.LENGTH_LONG).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**MANDAR AL FRAGMENT PEDIDOS LA LISTA DE SELECCIONADOS

                sendProducts.setSelectedList(selectedProducts);
*/
                selectedProducts.clear();
                layoutSelected.setVisibility(GONE);
            }
        });

        return view;
    }

    public static class ListUtils {
        public static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

    /**COMUNICACION CON OTROS FRAGMENTS*/
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

    @Override
    public void onResume() {
        super.onResume();
    }
}