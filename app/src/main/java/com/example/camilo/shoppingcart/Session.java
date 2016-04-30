package com.example.camilo.shoppingcart;

import android.support.v7.app.AppCompatActivity;

import java.util.Iterator;

/**
 * Created by Camilo on 4/26/2016.
 */
public class Session extends AppCompatActivity{

    public static final String INVENTORY_FILE = "master_inventory";

    private Session(){
        inventory = new Inventory();
        inventory.load();
    }

    public static Session getInstance(){
        if(instance==null)
            instance = new Session();
        return instance;
    }




    public String getCartSize(){
        return "Items: " + ((Customer)currentUser).getCartSize();
    }
    public String getCartTotal(){
        return "$ "+String.format("%.2f", ((Customer) currentUser).getCartTotal());
    }

    public boolean addProductToCart(String name){
        if(inventory.removeByName(name)) {
            ((Customer) currentUser).addToCart(inventory.getCloneByName(name));
            return true;
        }
        return false;
    }
    public boolean removeProductFromCart(String name){
        ((Customer)currentUser).removeFromCart(name);
        inventory.addByName(name);
        return true;
    }

    public Iterator getInventoryIterator(){
        return inventory.iterator();
    }

    public Iterator getCartIterator(){
        return ((Customer)currentUser).getIterator();
    }

    public Iterator getSellerIterator(){
        return ((Seller)currentUser).getIterator();
    }

    public int getMyInventorySize(){
        return ((Seller)currentUser).getInventorySize();
    }




    public void userLogin(User user){
        currentUser = user;
    }
    public void userLogout(){
        currentUser=null;
        inventory =null;
        instance=null;
    }


    private Inventory inventory;
    private User currentUser;
    private static Session instance;
}
