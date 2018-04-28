package com.example.xgramajo.tabbedproject;

import java.util.ArrayList;

public class CommandClass {

    private ArrayList commandProducts;
    private long commandNumber;
    private int commandMesa;
    private long commandPrice;

    public CommandClass(int mesa, ArrayList<ProductClass> products) {

        this.commandNumber = System.currentTimeMillis();
        this.commandMesa = mesa;
        this.commandProducts = products;
        this.commandPrice = sumCost(products);
    }

    private long sumCost(ArrayList<ProductClass> array){
        int x;
        long cost = 0;

        for (x=0 ; x <= array.size() ; x++ ){
            cost = cost + array.get(x).getPrice();
        }
        return cost;
    }
}
