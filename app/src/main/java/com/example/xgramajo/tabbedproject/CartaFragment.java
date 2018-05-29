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
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class CartaFragment extends Fragment {

    private ListView listViewProducts;
    private ListView listViewSelectedProducts;

    private static ProductListAdapter adapter1;
    private static SelectedListAdapter adapter2;

    private static ArrayList<ProductClass> products = new ArrayList<>();
    private static ArrayList<ProductClass> selectedProducts = new ArrayList<>();

    public static ArrayList<String> userHistoryString = new ArrayList<>();

    /**Interfaz 1*/
    private SendProducts sendProducts;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.carta_tab, container, false);

        /**LEVANTAR LOS PRODUCTOS DE FIREBASE Y METERLOS EN LA LISTVIEW*/
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Products");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectProducts((Map<String,Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "ERROR EN DATABASE " + databaseError, Toast.LENGTH_LONG).show();
            }
        });
/**
        DatabaseReference databaseRef2 = databaseReference.child("Users").child(FirebaseController.userID).child("History");
        databaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String prodName = data.getValue().toString();
                    userHistoryString.add(prodName);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        listViewProducts         = (ListView) view.findViewById(R.id.productsList);
        listViewSelectedProducts = (ListView) view.findViewById(R.id.selProdList);

        adapter1 = new ProductListAdapter(getActivity(), R.layout.adapter_products_view, products);
        adapter2 = new SelectedListAdapter(getActivity(), R.layout.adapter_selected_view, selectedProducts);

        listViewProducts.setAdapter(adapter1);
        listViewSelectedProducts.setAdapter(adapter2);
/**
        products.addAll(FirebaseController.getAllProducts());*/

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

        /**BOTON AGREGAR A COMANDA ACTUAL*/
        Button addButton = (Button) view.findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseController.addProductsInTable(selectedProducts);
                sendProducts.setSelectedList(selectedProducts);

                adapter2.clear();
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

    public static void removeFromSelected(ProductClass p) {
        adapter2.remove(p);
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseController.loadAllProducts(products);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}