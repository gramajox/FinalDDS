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
import com.google.zxing.Result;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class HomeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView zXingScannerView;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 0;

    Button btCarta, btReserva, btScan;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btCarta = findViewById(R.id.button_carta);
        btReserva = findViewById(R.id.button_reservar);
        btScan = findViewById(R.id.button_scan);

        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scan(view);
            }
        });

        btCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseController.tableNumber == null) {
                    Toast.makeText(getApplicationContext(), "Escanear mesa por favor", Toast.LENGTH_LONG).show();

                } else {
                    Intent myIntent = new Intent(HomeActivity.this, ManagerActivity.class);
                    startActivity(myIntent);
                    finish();
                }

            }
        });

        btReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Llama al Restaurante", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void scan(View view) {

        if (ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(HomeActivity.this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            }
        }
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(HomeActivity.this);
        zXingScannerView.startCamera();
    }

        @Override
        public void handleResult(Result result) {
            Toast.makeText(getApplicationContext(),"Mesa: " + result.getText(),Toast.LENGTH_LONG).show();

            FirebaseController.setUserTable(result.getText());
            FirebaseController.tableNumber = result.getText();
            
            Intent myIntent = new Intent(HomeActivity.this, ManagerActivity.class);
            startActivity(myIntent);
            finish();

            zXingScannerView.resumeCameraPreview(this);
        }

    @Override
    protected void onStart() {
        super.onStart();

        if (LoginActivity.getCurrentUser() != null) {
            FirebaseController.userID = mAuth.getCurrentUser().getUid();

        } else {
            Intent myIntent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(myIntent);
            finish();
        }
    }

}
