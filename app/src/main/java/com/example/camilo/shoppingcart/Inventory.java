package com.example.camilo.shoppingcart;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by Camilo on 4/29/2016.
 */
public class Inventory extends ArrayList<Product> implements Serializable{

    private static final long serialVersionUID = 315684651;

    public final static String SAMPLE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur\n" +
            "        adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore\n" +
            "        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco\n" +
            "        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor\n" +
            "        in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla\n" +
            "        pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa\n" +
            "        qui officia deserunt mollit anim id est laborum.";


    Inventory(){}

    public void saveMaster(File file){
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadMaster(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            this.clear();
            this.addAll((Inventory) ois.readObject());
            return;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //no master inventory exists, populate with fake items
        final String username = "default";
        this.add(new Product(R.drawable.icon_add_to_cart,
                "Add", 100.79, 80, 1, SAMPLE_DESCRIPTION, username));
        this.add(new Product(R.drawable.icon_remove_from_cart,
                "Remove", 225.24, 180, 2, SAMPLE_DESCRIPTION, username));
        this.add(new Product(R.drawable.icon_edit,
                "Edit", 49.22, 30, 3, SAMPLE_DESCRIPTION, username));
        this.add(new Product(R.drawable.icon_green_check,
                "Check", 71.98, 50, 4, SAMPLE_DESCRIPTION, username));
        this.add(new Product(R.drawable.icon_settings,
                "Settings", 35.50, 20, 5, SAMPLE_DESCRIPTION, username));
        this.add(new Product(R.drawable.icon_cart,
                "Cart", 15.14, 10, 6, SAMPLE_DESCRIPTION, username));
    }


    //////////////////
//master functions
    public Product getCloneByName(String name){
        for(Product p : this){
            if(p.getName().equals(name)) {
                return (Product)p.singleClone();
            }
        }
        return null;
    }

    public int getQtyByName(String name){
        for(Product p : this){
            if(name.equals(p.getName())) {
                return p.getQty();
            }
        }
        return 0;
    }

    public void deleteProduct(String name){
        for(Product p : this){
            if(name.equals(p.getName())) {
                remove(p);
                return;
            }
        }
    }

    public void checkout(ArrayList<SerializableStringPair<String,Integer>> items){
        for(SerializableStringPair pair : items){
            for(Product p : this){
                if(p.getName().equals(pair.first)) {
                    p.setQty(p.getQty() - (Integer)pair.second);
                }
            }
        }

    }

    public void updateQty(String name, int newQuantity){
        for(Product p : this){
            if(p.getName().equals(name)) {
                p.setQty(newQuantity);
            }
        }
    }
}