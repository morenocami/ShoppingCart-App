package com.example.camilo.shoppingcart;

import android.media.Image;

/**
 * Created by Camilo on 4/25/2016.
 */
public class Product implements Comparable {

    Product(){

    }

    @Override
    public int compareTo(Object other) {

        return 0;
    }

    private int id;
    private String name;
    private String description;
    private double sellPrice;
    private double cost;
    private Image productPhoto;

}