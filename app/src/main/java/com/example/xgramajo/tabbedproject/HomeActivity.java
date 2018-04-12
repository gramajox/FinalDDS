package com.example.xgramajo.tabbedproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class HomeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;

    Button btCarta, btReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btCarta = findViewById(R.id.button_carta);
        btReserva = findViewById(R.id.button_reservar);


        /**Accion button_carta*/

        btCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(HomeActivity.this, ManagerActivity.class);
                startActivity(myIntent);
            }
        });

        /**Accion button_reservar*/

        btReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "LLAMAR AL RESTO.", Toast.LENGTH_LONG).show();
            }
        });
    }
    /**Funcion Scanner*/

    public void scan(View view) {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.CAMERA)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(HomeActivity.this);
        zXingScannerView.startCamera();
    }
     /**@Override
        protected void onPause() {
            super.onPause();
            zXingScannerView.stopCamera();
        }
*/
        @Override
        public void handleResult(Result result) {
            Toast.makeText(getApplicationContext(),result.getText(),Toast.LENGTH_LONG).show();
            zXingScannerView.resumeCameraPreview(this);
        }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {

        } else {

            Intent myIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(myIntent);

        }

    }
}
