package com.example.camilo.shoppingcart;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Camilo on 4/25/2016.
 */
public class User implements Serializable{

    private User(String username, String password){
        this.username=username;
        this.password=password;
        savedCart = new ArrayList<>();
    }

    public void login(){

    }


    private void restoreCart(){

        savedCart = new ArrayList<>(); //retrieve from storage (physical/cloud)
    }

    private String username;
    private String password;
    private ArrayList<Product> savedCart;
}
