package com.example.xgramajo.tabbedproject;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;

public class FirebaseController {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static String tableNumber;
    public static String userID;

    private static ArrayList<String> userHistoryString = new ArrayList<>();
    private static ArrayList<String> tableProductsString = new ArrayList<>();

    private static ArrayList<ProductClass> tableProducts = new ArrayList<>();
    private static ArrayList<ProductClass> allProducts = new ArrayList<>();
    private static ArrayList<ProductClass> productsInOrder = new ArrayList<>();

    public interface FirebaseCallback {
        void onCallback(ArrayList<ProductClass> list);
    }

    private void readAllProducts(final FirebaseCallback firebaseCallback) {
        DatabaseReference databaseReference1 = databaseReference.child("Products");
        databaseReference1.orderByChild("Name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> prod = (Map<String,Object>) dataSnapshot.getValue();

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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void readTableProducts(final FirebaseCallback firebaseCallback) {
        DatabaseReference databaseRef3 = databaseReference.child("Tables").child(tableNumber).child("Command");
        databaseRef3.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tableProductsString.clear();
                tableProducts.clear();

                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String prodName = data.getValue(String.class);
                    tableProductsString.add(prodName);
                }

                Log.d("readTableProducts","allProducts.size() = "+allProducts.size());

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
                    Log.d("getAllProducts", "allProducts.size() = "+allProducts.size());
                    getTableProducts();
                    orderProducts(listProd, userHistoryString);
                    MenuFragment.loadAllProducts(productsInOrder);
                } else {
                    MenuFragment.loadAllProducts(listProd);
                }
            }
        });
    }

    private void getTableProducts() {

        readTableProducts(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ProductClass> listProd) {
                CommandFragment.loadTableProducts(listProd);
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
        ArrayList<ProductClass> auxListProducts = new ArrayList<>(allProducts);
        auxListProducts.removeAll(productsInOrder);
        productsInOrder.addAll(auxListProducts);
    }

    public void setUserTable(String qrResult) {
        if (userID != null) {
            databaseReference.child("Users").child(userID).child("Table").setValue(qrResult);
            databaseReference.child("Tables").child(qrResult).child("Status").setValue("Activa");
        }
    }

    public void setTableFree() {
        if (userID != null && tableNumber != null) {
            databaseReference.child("Tables").child(tableNumber).child("Status").setValue("Libre");
            databaseReference.child("Users").child(userID).child("Table").setValue("");
        }
    }

    public void addProductsInTable(ArrayList<ProductClass> list) {
        if (list != null && tableNumber != null) {
            for (int x = 0; x < list.size(); x++) {
                databaseReference.child("Tables").child(tableNumber).child("Command").push().setValue(list.get(x).getName());
                databaseReference.child("Users").child(userID).child("History").push().setValue(list.get(x).getName());
            }
        }
    }

    public void clearTableProducts() {
        if (tableNumber != null) {
            databaseReference.child("Tables").child(tableNumber).child("Command").removeValue();
        }
    }

    public void saveProducts(ArrayList<ProductClass> list) {
        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(System.currentTimeMillis());
        for (int x = 0; x < list.size(); x++) {
            databaseReference.child("Orders").child(dayOfWeek).push().setValue(list.get(x).getName());
        }
    }

    public void removeOneProduct(ProductClass p) {
        DatabaseReference databaseRef = databaseReference.child("Tables").child(tableNumber).child("Command");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                int control =0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    String prodName = data.getValue(String.class);
                    if (prodName.equals(p.getName()) && control == 0) {
                        databaseRef.child(data.getKey()).removeValue();
                        control = 1;
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    /*LLAMAR A ESTA FUNCION EN EL START DE HOME ACTIVITY*/
    public void getUserHistory() {
        if (userID != null) {

            userHistoryString.clear();

            DatabaseReference databaseRef2 = databaseReference.child("Users").child(userID).child("History");
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