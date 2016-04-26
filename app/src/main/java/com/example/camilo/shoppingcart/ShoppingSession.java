package com.example.camilo.shoppingcart;

import java.util.ArrayList;
import java.util.logging.SocketHandler;

/**
 * Created by Camilo on 4/26/2016.
 */
public class ShoppingSession {

    private ShoppingSession(){

    }
    public void addToCart(Product product){

    }

    public static ShoppingSession getInstance(){
        return instance;
    }


    private ArrayList<Product> shoppingCart;
    private User currentUser;
    private static ShoppingSession instance = new ShoppingSession();
}
