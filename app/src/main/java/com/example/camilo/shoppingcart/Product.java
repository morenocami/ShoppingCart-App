package com.example.camilo.shoppingcart;

import android.media.Image;

import java.io.Serializable;

/**
 * Created by Camilo on 4/25/2016.
 */
public class Product implements Comparable, Serializable {

    Product(int imageResource, String name, double sellPrice, double cost, int qty, String description, String seller){
        this.name=name;
        this.sellPrice=sellPrice;
        this.cost=cost;
        this.imageResource = imageResource;
        this.qty=qty;
        this.description=description;
        this.seller = seller;
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

    public void setQty(int qty){
        this.qty=qty;
    }

    public boolean decrementfromCart(){
        if(qty==1)
            return false;
        qty--;
        return true;
    }
    public void incrementQuantity(){
        qty++;
    }

    @Override
    public int compareTo(Object other) {
        return 0;
    }

    protected Object singleClone() {
        return new Product(this.imageResource, this.name, this.sellPrice, this.cost, 1, this.description, this.seller);
    }

    private int qty;
    private String name;
    private String description;
    private String seller;
    private double sellPrice;
    private double cost;
    private int imageResource;

}