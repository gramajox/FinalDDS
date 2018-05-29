package com.example.xgramajo.tabbedproject;

import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FirebaseController {

    private static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public static String tableNumber = "12341";
    public static String userID;

    private static ArrayList<String> userHistoryString = new ArrayList<>();
    private static ArrayList<String> tableProductsString = new ArrayList<>();

    private static ArrayList<ProductClass> userHistory = new ArrayList<>();
    private static ArrayList<ProductClass> tableProducts = new ArrayList<>();
    private static ArrayList<ProductClass> allProducts = new ArrayList<>();


    public static void loadAllProducts(ArrayList<ProductClass> prod) {
        allProducts.addAll(prod);
    }

    public static void setUserTable(String qrResult) {
        if (userID != null) {
            databaseReference.child("Users").child(userID).child("Mesa").setValue(qrResult);
            databaseReference.child("Mesas").child(qrResult).child("Estado").setValue("Activa");
        }
    }

    public static void setTableFree() {
        if (userID != null && tableNumber != null) {
            databaseReference.child("Mesas").child(tableNumber).child("Estado").setValue("Libre");
            databaseReference.child("Users").child(userID).child("Mesa").setValue("");
        }
    }
/**
    public static ArrayList<ProductClass> getTableProducts() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot.child("Mesas").child(tableNumber).child("Comanda").getChildren()
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return tableProducts;
    }


    public static ArrayList<String> getUserHistory() {

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

        return userHistoryString;
    }*/

    public static void addProductsInTable(ArrayList<ProductClass> list) {
        if (list != null && tableNumber != null) {
            for (int x = 0; x < list.size(); x++) {
                databaseReference.child("Mesas").child(tableNumber).child("Comanda").push().setValue(list.get(x).getName());
                databaseReference.child("Users").child(userID).child("Historial").push().setValue(list.get(x).getName());
            }
        }
    }

    public static void clearTableProducts() {
        if (tableNumber != null) {
            databaseReference.child("Mesas").child(tableNumber).child("Comanda").removeValue();
        }
    }

    public static void removeOneProduct(ProductClass p) {
        /**databaseReference.child("Mesas").child(tableNumber).child("Comanda").removeValue(p.getName());*/
    }

    /**
    public static ArrayList<ProductClass> getAllProducts() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectProducts((Map<String,Object>) dataSnapshot.child("Products").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return allProducts;
    }

    private static void collectProducts(Map<String, Object> prod) {

        for (Map.Entry<String,Object> entry : prod.entrySet()) {

            Map singleProduct = (Map) entry.getValue();

            allProducts.add(new ProductClass(
                    (String) singleProduct.get("Name"),
                    (String) singleProduct.get("Description"),
                    (String) singleProduct.get("Category"),
                    Integer.parseInt((String) singleProduct.get("Price")),
                    R.drawable.empanadas));
        }

    }*/

}