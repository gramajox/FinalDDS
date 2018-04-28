package com.example.xgramajo.tabbedproject;

import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FirebaseController {

    static DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    static FirebaseUser currentUser = LoginActivity.getCurrentUser();

    private ArrayList<ProductClass> productsList;
    private static int tableNumber;
    private ArrayList<String> userHistory;
    static String userID = currentUser.getUid();

    public void loadProducts() {

        /**LEVANTAR LOS PRODUCTOS DE FIREBASE Y METERLOS EN LA LISTVIEW*/
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tableNumber = Integer.parseInt( (String) dataSnapshot.child("User").child(userID).child("Mesa").getValue() );
                collectProducts((Map<String, Object>) dataSnapshot.child("Products").getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /** AGREGA LOS PRODUCTOS EN LA LISTA*/
    private void collectProducts(Map<String, Object> prod) {

        for (Map.Entry<String, Object> entry : prod.entrySet()) {

            Map singleProduct = (Map) entry.getValue();

            productsList.add(new ProductClass(
                    (String) singleProduct.get("Name"),
                    (String) singleProduct.get("Description"),
                    (String) singleProduct.get("Category"),
                    Integer.parseInt((String) singleProduct.get("Price")),
                    R.drawable.empanadas));
        }

    }

    public ArrayList<ProductClass> getProductsList() {
        return productsList;
    }
    public int getTableNumber() {
        return tableNumber;
    }
    public String getUserID() {
        return userID;
    }

    public void loadToHistory(ArrayList<ProductClass> listProd) {

    }
    public static void setUserTable(String qrResult) {
        databaseReference.child("Users").child(userID).child("Mesa").setValue(qrResult);
        databaseReference.child("Mesas").child(qrResult).child("Estado").setValue("Activa");
    }
    public static void setTableFree() {
        databaseReference.child("Mesas").child(Integer.toString(tableNumber)).child("Estado").setValue("Libre");
        databaseReference.child("Users").child(userID).child("Mesa").setValue("");
    }
}