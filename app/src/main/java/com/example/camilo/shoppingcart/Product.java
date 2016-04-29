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


    public boolean decrementfromInventory(){
        if(qty==0)
            return false;
        qty--;
        return true;
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

    @Override
    protected Object clone() {
        return new Product(this.name, this.sellPrice, this.imageResource, 1);
    }

    private int qty;
    private String name;
    private String description;
    private String seller;
    private double sellPrice;
    private double cost;
    private int imageResource;

}