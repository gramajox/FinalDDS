package com.example.xgramajo.tabbedproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        /**esto es para recibir el objeto de la lista*/
        Intent intent = getIntent();
        ProductClass productItem = intent.getParcelableExtra("Product Item");

        int imageRes = productItem.getImageResource();
        String name = productItem.getName();
        String description = productItem.getDescription();

        ImageView imageView = findViewById(R.id.image_product);
        imageView.setImageResource(imageRes);

        TextView textView1 = findViewById(R.id.text_name);
        textView1.setText(name);

        TextView textView2 = findViewById(R.id.text_description);
        textView2.setText(description);
    }

}