package com.example.camilo.shoppingcart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * Created by Camilo on 4/25/2016.
 */
public abstract class User implements Serializable{

    public User(String username, String password, boolean isSeller){
        this.username=username;
        this.password=password;
        this.isSeller = isSeller;
        shoppingCart = new ArrayList<>();
    }

    public boolean checkUsername(String username){
        return username.equals(this.username);
    }

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }

    public ListIterator getIterator(){
        return shoppingCart.listIterator();
    }

    public void addToCart(Product product){
        shoppingCart.add(product);
    }

    public double getCartTotal(){
        double result=0;
        for (Product p:shoppingCart) {
            result += p.getSellPrice();
        }
        return result;
    }

    public int getCartSize(){
        return shoppingCart.size();
    }

    private String username;
    private String password;
    private boolean isSeller;
    private ArrayList<Product> shoppingCart;
}
