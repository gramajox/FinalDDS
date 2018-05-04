package com.example.xgramajo.tabbedproject;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class CartaFragment extends Fragment {

    private ListView listViewProducts;
    private ListView listViewSelectedProducts;
    private ArrayList<ProductClass> products = new ArrayList<>();
    private static ArrayList<ProductClass> selectedProducts = new ArrayList<>();
    private ProductListAdapter adapter1;
    private static ProductListAdapter adapter2;

    /**Interfaz 1*/
    private SendProducts sendProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carta_tab, container, false);

        final LinearLayout layoutSelected = (LinearLayout) view.findViewById(R.id.selected_view);
        /*layoutSelected.setVisibility(GONE);*/

        /**LEVANTAR LOS PRODUCTOS DE FIREBASE Y METERLOS EN LA LISTVIEW*/
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectProducts((Map<String,Object>) dataSnapshot.child("Products").getValue());

                Toast.makeText(getContext(), "Cantidad de Productos: " + products.size(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "ERROR EN DATABASE", Toast.LENGTH_LONG).show();
            }
        });

        listViewProducts         = (ListView) view.findViewById(R.id.productsList);
        listViewSelectedProducts = (ListView) view.findViewById(R.id.selProdList);

        adapter1 = new ProductListAdapter(getActivity(), R.layout.adapter_products_view, products);
        adapter2 = new ProductListAdapter(getActivity(), R.layout.adapter_products_view, selectedProducts);

        listViewProducts.setAdapter(adapter1);
        listViewSelectedProducts.setAdapter(adapter2);

        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent myIntent = new Intent(getActivity(), ProductActivity.class);
                myIntent.putExtra("Product Item", products.get(i));

                startActivity(myIntent);
            }
        });

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

        /**BOTON AGREGAR A COMANDA ACTUAL*/
        Button addButton = (Button) view.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendProducts.setSelectedList(selectedProducts);

                selectedProducts.clear();/*
                layoutSelected.setVisibility(GONE);*/
            }
        });

        return view;
    }
    /**AGREGA LOS PRODUCTOS EN LA LISTA*/
    private void collectProducts(Map<String,Object> prod) {

        for (Map.Entry<String,Object> entry : prod.entrySet()) {

            Map singleProduct = (Map) entry.getValue();

            adapter1.add(new ProductClass(
                    (String) singleProduct.get("Name"),
                    (String) singleProduct.get("Description"),
                    (String) singleProduct.get("Category"),
                    Integer.parseInt((String) singleProduct.get("Price")),
                    R.drawable.empanadas));
        }

    }

    /**ESTO SOLUCIONA LA VISTA DE VARIAS LISTVIEW EN LA MISMA PANTALLA*/
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

    /**Interfaz 1*/
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

    /**Funcion que usa el ProductListAdapter para agregar los productos a selectProducts*/
    public static void selectProductFromList(ProductClass p) {
        adapter2.add(p);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
