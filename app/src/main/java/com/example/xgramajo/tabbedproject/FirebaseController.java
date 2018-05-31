package com.example.xgramajo.tabbedproject;

import android.util.Log;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;

public class FirebaseController {

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public String tableNumber = "12341";
    public String userID;

    private ArrayList<String> userHistoryString = new ArrayList<>();
    private ArrayList<String> tableProductsString = new ArrayList<>();

    private ArrayList<ProductClass> userHistory = new ArrayList<>();
    private ArrayList<ProductClass> tableProducts = new ArrayList<>();
    private ArrayList<ProductClass> allProducts = new ArrayList<>();
    private ArrayList<ProductClass> productsInOrder = new ArrayList<>();

    public interface FirebaseCallback {
        void onCallback(ArrayList<ProductClass> list);
    }

    private void readAllProducts(final FirebaseCallback firebaseCallback) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> prod = (Map<String,Object>) dataSnapshot.child("Products").getValue();
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
                        if (allProducts.get(y).getName() == tableProductsString.get(x)) {
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
                orderProducts(listProd, userHistoryString);
                CartaFragment.loadAllProducts(productsInOrder);
            }
        });
    }

    public void getTableProducts() {

        readTableProducts(new FirebaseCallback() {
            @Override
            public void onCallback(ArrayList<ProductClass> listProd) {
                PedidosFragment.loadTableProducts(listProd);
            }
        });
    }

    private void orderProducts(ArrayList<ProductClass> listProd, ArrayList<String> listHistory) {
        //AGREGO TODOS LOS PRODUCTOS DEL HISTORIAL A LA LISTA
        for (int x=0; x < listHistory.size(); x++) {
            for (int y=0; y < listProd.size(); y++) {
                if (listProd.get(y).getName() == listHistory.get(x)) {
                    if (!(productsInOrder.contains(listProd.get(y)))) {
                        productsInOrder.add(listProd.get(y));
                    }
                }
            }
        }
        //AGREGO LOS PRODUCTOS QUE FALTAN DE ALLPRODUCTS
        for (int x=0; x < listProd.size(); x++) {
            if (!(productsInOrder.contains(listProd.get(x)))) {
                productsInOrder.add(listProd.get(x));
            }
        }
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
        /**databaseReference.child("Mesas").child(tableNumber).child("Comanda").removeValue(p.getName());*/
    }

    /*LLAMAR A ESTA FUNCION EN EL START DE HOME ACTIVITY */
    public void getUserHistory() {
        DatabaseReference databaseRef2 = databaseReference.child("Users").child(userID).child("History");
        databaseRef2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    //ORDENAR POR VALOR
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