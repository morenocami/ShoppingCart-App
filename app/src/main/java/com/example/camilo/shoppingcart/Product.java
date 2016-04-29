package com.example.camilo.shoppingcart;

import android.media.Image;

/**
 * Created by Camilo on 4/25/2016.
 */
public class Product implements Comparable {

    Product(String name, double sellPrice, int imageResource, int qty){
        this.name=name;
        this.sellPrice=sellPrice;
        this.imageResource = imageResource;
        this.qty=qty;
    }

    public int getQty() {
        return qty;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getSeller() {
        return seller;
    }
    public double getSellPrice() {
        return sellPrice;
    }
    public double getCost() {
        return cost;
    }
    public int getImageResource() {
        return imageResource;
    }

    public void decrementQuantity(){
        qty--;
    }

    public void incrementQuantity(){
        qty++;
    }

    @Override
    public int compareTo(Object other) {
        return 0;
    }

    private int qty;
    private String name;
    private String description;
    private String seller;
    private double sellPrice;
    private double cost;
    private int imageResource;

}