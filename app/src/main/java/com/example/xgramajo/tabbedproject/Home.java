package com.example.xgramajo.tabbedproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Scanner;

public class Home extends AppCompatActivity {

    Button btCarta, btReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btCarta = findViewById(R.id.button_carta);
        btReserva = findViewById(R.id.button_reservar);


        //Crea una nueva Activity para ir a la interfaz de Gestor de Pedidos

        btCarta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Esto debería FUNCIONARR.",Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(Home.this, Manager.class);
                startActivity(myIntent);
            }
        });

        //El boton hace saltar un mensaje

        btReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Esto llama a ScannerQR.",Toast.LENGTH_LONG).show();
                /*Intent scanner = new Intent(HomeActivity.this, Scanner.class);
                startActivity(scanner);*/
            }
        });
    }
}
