package com.example.camilo.shoppingcart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/25/2016.
 */
public abstract class User implements Serializable{

    public User(String username, String password, boolean isSeller){
        this.username=username;
        this.password=password;
        this.isSeller = isSeller;
    }

    public void login(){
        ShoppingSession.getInstance().userLogin(this);
    }

    public boolean checkUsername(String username){
        return username.equals(this.username);
    }

    public boolean checkPassword(String password){
        return password.equals(this.password);
    }

    public boolean isSeller(){return isSeller;}

    private String username;
    private String password;
    private boolean isSeller;
}
