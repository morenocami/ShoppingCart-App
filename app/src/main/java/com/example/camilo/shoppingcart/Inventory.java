package com.example.camilo.shoppingcart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by Camilo on 4/29/2016.
 */
public class Inventory extends ArrayList<Product>{

    private final static String MASTER_LIST = "master";
    public final static String SAMPLE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur\n" +
            "        adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore\n" +
            "        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco\n" +
            "        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor\n" +
            "        in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla\n" +
            "        pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa\n" +
            "        qui officia deserunt mollit anim id est laborum.";


    Inventory(){

    }

    public void load(){
        try {
            FileInputStream fis = new FileInputStream(MASTER_LIST);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<Product> temp = ((ArrayList<Product>)ois.readObject());
            this.clear();
            this.addAll(temp);
            return;
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //no master inventory exists, populate with fake items
        this.add(new Product("Add", 100.79,
                R.drawable.icon_add_to_cart, 1, SAMPLE_DESCRIPTION));
        this.add(new Product("Remove", 225.24,
                R.drawable.icon_remove_from_cart, 2, SAMPLE_DESCRIPTION));
        this.add(new Product("Edit", 49.22,
                R.drawable.icon_edit, 3, SAMPLE_DESCRIPTION));
        this.add(new Product("Check", 71.98,
                R.drawable.icon_green_check, 4, SAMPLE_DESCRIPTION));
        this.add(new Product("Settings", 35.50,
                R.drawable.icon_settings, 5, SAMPLE_DESCRIPTION));
        this.add(new Product("Cart", 15.14,
                R.drawable.icon_cart, 6, SAMPLE_DESCRIPTION));
    }

    public Product getCloneByName(String name){
        for(Product p : this){
            if(p.getName().equals(name)) {
                return (Product)p.clone();
            }
        }
        return null;
    }

    public void addByName(String name){
        for(Product p : this){
            if(p.getName().equals(name)) {
                p.incrementQuantity();
            }
        }
    }

    public boolean removeByName(String name){
        for(Product p : this){
            if(p.getName().equals(name)) {
                return p.decrementfromInventory();
            }
        }
        return false;
    }


}
