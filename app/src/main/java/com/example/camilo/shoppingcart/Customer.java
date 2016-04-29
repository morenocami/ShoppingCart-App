package com.example.camilo.shoppingcart;

import java.util.ArrayList;

/**
 * Created by Camilo on 4/28/2016.
 */
public class Customer extends User {

    public Customer(String username, String password, boolean isSeller) {
        super(username, password, isSeller);
        shoppingCart = new ArrayList<>();
    }

    private ArrayList<Product> shoppingCart;
}
