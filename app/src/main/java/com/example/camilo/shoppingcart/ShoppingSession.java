package com.example.camilo.shoppingcart;

import android.support.v7.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/26/2016.
 */
public class ShoppingSession extends AppCompatActivity{

    public static final String INVENTORY_FILE = "master_inventory";

    private ShoppingSession(){
        inventory = new ArrayList<>();
        try{
            final FileInputStream fis = new FileInputStream(INVENTORY_FILE);
            final ObjectInputStream ois = new ObjectInputStream(fis);
            inventory = (ArrayList<Product>)ois.readObject();
            ois.close();
        }
        catch(Exception ex){
            inventory.add(new Product("Add", 100.79, R.drawable.icon_add_to_cart,1));
            inventory.add(new Product("Remove", 225.24, R.drawable.icon_remove_from_cart,2));
            inventory.add(new Product("Edit", 49.22, R.drawable.icon_edit,3));
            inventory.add(new Product("Check", 71.98, R.drawable.icon_green_check,4));
            inventory.add(new Product("Settings", 35.50, R.drawable.icon_settings,5));
            inventory.add(new Product("Cart", 15.14, R.drawable.icon_cart,6));
            ex.printStackTrace();
        }
    }

    public String getCartSize(){
        return "Items: " + currentUser.getCartSize();
    }
    public String getCartTotal(){
        return "$ " + String.format("%.2f", currentUser.getCartTotal());
    }

    public boolean addProduct(String name){
        for(Product p : inventory){
            if(p.getName().equals(name)) {
                if(p.decrementfromInventory()){
                    currentUser.addToCart(p);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean removeProduct(String name){
        for(Product p : inventory){
            if(p.getName().equals(name)) {
                if(currentUser.removeFromCart(name)){
                    p.incrementQuantity();
                    return true;
                }
            }
        }
        return true;
    }

    public void userLogin(User user){
        currentUser = user;
    }
    public void userLogout(){
        currentUser=null;
        inventory=null;
        instance=null;
    }

    public static ShoppingSession getInstance(){
        if(instance==null)
            instance = new ShoppingSession();
        return instance;
    }

    public Iterator getInventoryIterator(){
        return inventory.iterator();
    }

    public Iterator getCartIterator(){
        return currentUser.getIterator();
    }

    private ArrayList<Product> inventory;
    private User currentUser;
    private static ShoppingSession instance;
}
