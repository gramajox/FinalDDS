package com.example.xgramajo.tabbedproject;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class FirebaseController {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static String tableNumber = "12341";
    public static String userID;
    public static String userEmail;

    private static ArrayList<String> userHistoryString = new ArrayList<>();
    private static ArrayList<String> tableProductsString = new ArrayList<>();

    private static ArrayList<ProductClass> tableProducts = new ArrayList<>();
    private static ArrayList<ProductClass> allProducts = new ArrayList<>();
    private static ArrayList<ProductClass> productsInOrder = new ArrayList<>();

    public interface FirebaseCallback {
        void onCallback(ArrayList<ProductClass> list);
    }

    private void readAllProducts(final FirebaseCallback firebaseCallback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> prod = (Map<String,Object>) dataSnapshot.child("Products").getValue();

                allProducts.clear();

                for (Map.Entry<String,Object> entry : prod.entrySet()) {

                    Map singleProduct = (Map) entry.getValue();

                    allProducts.add(new ProductClass(
                            (String) singleProduct.get("Name"),
                            (String) singleProduct.get("Description"),
                            (String) singleProduct.get("Category"),
                            Integer.parseInt((String) singleProduct.get("Price")),
                            R.drawable.empanadas));
                }
                firebaseCallback.onCallback(allProducts);

                getTableProducts();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void readTableProducts(final FirebaseCallback firebaseCallback) {
        DatabaseReference databaseRef3 = databaseReference.child("Mesas").child(tableNumber).child("Comanda");
        databaseRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String prodName = data.getValue(String.class);
                    tableProductsString.add(prodName);
                }
                for (int x=0; x < tableProductsString.size(); x++) {
                    for (int y=0; y < allProducts.size(); y++) {
                        if (allProducts.get(y).getName().equals(tableProductsString.get(x))) {
                            tableProducts.add(allProducts.get(y));
                        }
                    }
                }

                firebaseCallback.onCallback(tableProducts);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getAllProducts() {

        readAllProducts(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ProductClass> listProd) {
                if (userHistoryString.size() > 0) {
                    orderProducts(listProd, userHistoryString);
                    CartaFragment.loadAllProducts(productsInOrder);
                } else {
                    CartaFragment.loadAllProducts(listProd);
                }

            }
        });
    }

    private void getTableProducts() {

        readTableProducts(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ProductClass> listProd) {
                PedidosFragment.loadTableProducts(listProd);
            }
        });
    }

    private void orderProducts(ArrayList<ProductClass> listProd, ArrayList<String> listHistoryString) {
        productsInOrder.clear();

        //CREO LISTAS PARALELAS PARA MANEJAR LA CANTIDAD DE DE VECES QUE SE REPITE UN PRODUCTO
        ArrayList<String> auxListString = new ArrayList<>();
        ArrayList<Integer> auxListCounts = new ArrayList<>();
        for (int x=0; x < listHistoryString.size(); x++) {
            for (int y=0; y < listProd.size(); y++) {

                if (listProd.get(y).getName().equals(listHistoryString.get(x))) {

                    if (!auxListString.contains(listHistoryString.get(x))) {
                        auxListString.add(listHistoryString.get(x));
                        auxListCounts.add(howMany(listHistoryString,listHistoryString.get(x)));
                    }
                }
            }
        }
        //VACIO LA LISTA PARA VOLVER A LLENARLA EN ORDEN Y SIN REPETIDOS
        listHistoryString.clear();

        //ORDENO SEGUN CANTIDAD DE PEDIDOS
        for (int k=0; k < auxListString.size() ;k++) {

            for (int x=0; x < auxListString.size()-1; x++) {
                if (auxListCounts.get(x) < auxListCounts.get(x+1)) {
                    Collections.swap(auxListCounts,x,(x+1));
                    Collections.swap(auxListString,x,(x+1));
                }
            }
        }

        //AGREGO LOS PRODUCTOS ORDENADOS SEGUN EL HISTORIAL
        for (int x=0; x < auxListString.size(); x++) {
            for (int y=0; y < listProd.size(); y++) {

                if (listProd.get(y).getName().equals(auxListString.get(x))) {

                    if (!productsInOrder.contains(listProd.get(y))) {
                        productsInOrder.add(listProd.get(y));
                    }
                }
            }
        }

        //AGREGO LOS PRODUCTOS QUE FALTAN
        allProducts.removeAll(productsInOrder);
        productsInOrder.addAll(allProducts);
    }

    public void setUserTable(String qrResult) {
        if (userID != null) {
            databaseReference.child("Users").child(userID).child("Mesa").setValue(qrResult);
            databaseReference.child("Mesas").child(qrResult).child("Estado").setValue("Activa");
        }
    }

    public void setTableFree() {
        if (userID != null && tableNumber != null) {
            databaseReference.child("Mesas").child(tableNumber).child("Estado").setValue("Libre");
            databaseReference.child("Users").child(userID).child("Mesa").setValue("");
        }
    }

    public void addProductsInTable(ArrayList<ProductClass> list) {
        if (list != null && tableNumber != null) {
            for (int x = 0; x < list.size(); x++) {
                databaseReference.child("Mesas").child(tableNumber).child("Comanda").push().setValue(list.get(x).getName());
                databaseReference.child("Users").child(userID).child("Historial").push().setValue(list.get(x).getName());
            }
        }
    }

    public void clearTableProducts() {
        if (tableNumber != null) {
            databaseReference.child("Mesas").child(tableNumber).child("Comanda").removeValue();
        }
    }

    public void removeOneProduct(ProductClass p) {
        //databaseReference.child("Mesas").child(tableNumber).child("Comanda").removeValue(p.getName());
    }

    /*LLAMAR A ESTA FUNCION EN EL START DE HOME ACTIVITY*/
    public void getUserHistory() {
        if (userID != null) {

            userHistoryString.clear();

            DatabaseReference databaseRef2 = databaseReference.child("Users").child(userID).child("Historial");
            databaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        String prodName = data.getValue(String.class);
                        userHistoryString.add(prodName);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    private int howMany(ArrayList<String> list, String prod) {
        int count = 0;
        for (int x = 0; x < list.size(); x++) {
            if (prod.equals(list.get(x))) {
                count++;
            }
        }
        return count;
    }

}