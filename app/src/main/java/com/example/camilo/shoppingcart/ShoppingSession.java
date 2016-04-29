package com.example.camilo.shoppingcart;

import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.SocketHandler;

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
            inventory.add(new Product("Add", 100.79, R.drawable.icon_add_to_cart));
            inventory.add(new Product("Remove", 225.24, R.drawable.icon_remove_from_cart));
            inventory.add(new Product("Edit", 49.22, R.drawable.icon_edit));
            inventory.add(new Product("Check", 71.98, R.drawable.icon_green_check));
            inventory.add(new Product("Settings", 35.50, R.drawable.icon_settings));
            inventory.add(new Product("Cart", 15.14, R.drawable.icon_cart));
            ex.printStackTrace();
        }
    }

    public void sellerAdd(Product newProduct){
        inventory.add(newProduct);
        try{
            final FileOutputStream fos = new FileOutputStream(INVENTORY_FILE);
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(inventory);
            oos.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void userLogin(User user){
        currentUser = user;
    }

    public static ShoppingSession getInstance(){
        if(instance==null)
            instance = new ShoppingSession();
        return instance;
    }



    private ArrayList<Product> inventory;
    private User currentUser;
    private static ShoppingSession instance;
}
