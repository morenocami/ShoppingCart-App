package com.example.camilo.shoppingcart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

/**
 * Created by Camilo on 4/25/2016.
 */
public abstract class User implements Serializable{

    public User(String username, String password, boolean isSeller){
        this.username=username;
        this.password=password;
        this.isSeller = isSeller;
        myItems = new ArrayList<>();
    }

    public boolean checkUsername(String username){
        return username.equals(this.username);
    }

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }

    public ListIterator getIterator(){
        return myItems.listIterator();
    }

    public void login(){

    }


    private void restoreCart(){

        myItems = new ArrayList<>(); //retrieve from storage (physical/cloud)
    }

    private String username;
    private String password;
    private boolean isSeller;
    private ArrayList<Product> myItems;
}
