package com.example.camilo.shoppingcart;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Camilo on 4/29/2016.
 */
public class SellerSession {

    private SellerSession(){
        inventory = new ArrayList<>();
        try{
            final FileInputStream fis = new FileInputStream("");
            final ObjectInputStream ois = new ObjectInputStream(fis);
            inventory = (ArrayList<Product>)ois.readObject();
            ois.close();
        }
        catch(Exception ex){

            ex.printStackTrace();
        }
    }

    public void sellerAdd(Product newProduct){
        inventory.add(newProduct);
        try{
            final FileOutputStream fos = new FileOutputStream("");
            final ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(inventory);
            oos.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }


    public SellerSession getInstance(){
        if(instance==null)
            instance = new SellerSession();
        return instance;
    }

    private ArrayList<Product> inventory;
    private User currentUser;
    private static SellerSession instance;

}
