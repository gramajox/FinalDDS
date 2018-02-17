package com.example.xgramajo.tabbedproject;


import java.io.Serializable;

public class ProductClass implements Serializable{
    private String name;
    private String description;
    private String category;
    private int price;

    public ProductClass(String name, String description, String category, int price) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
