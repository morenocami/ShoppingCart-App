package com.example.camilo.shoppingcart;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/29/2016.
 */
public class Inventory extends ArrayList<Product> implements Serializable{

    private final static String MASTER_LIST = "master";
    public final static String SAMPLE_DESCRIPTION = "Lorem ipsum dolor sit amet, consectetur\n" +
            "        adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore\n" +
            "        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco\n" +
            "        laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor\n" +
            "        in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla\n" +
            "        pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa\n" +
            "        qui officia deserunt mollit anim id est laborum.";


    Inventory(){
        if(master==null) {
            master=new ArrayList<>();
            master = this.loadMaster();
        }
    }

    private ArrayList<Product> loadMaster(){
        try {
            FileInputStream fis = new FileInputStream(MASTER_LIST);
            ObjectInputStream ois = new ObjectInputStream(fis);
            return (ArrayList<Product>)ois.readObject();
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
        final ArrayList<Product> temp= new ArrayList<>();
        final String username = "sample seller";
        temp.add(new Product(R.drawable.icon_add_to_cart,
                "Add", 100.79, 80, 1, SAMPLE_DESCRIPTION, username));
        temp.add(new Product(R.drawable.icon_remove_from_cart,
                "Remove", 225.24, 180, 2, SAMPLE_DESCRIPTION, username));
        temp.add(new Product(R.drawable.icon_edit,
                "Edit", 49.22, 30, 3, SAMPLE_DESCRIPTION, username));
        temp.add(new Product(R.drawable.icon_green_check,
                "Check", 71.98, 50, 4, SAMPLE_DESCRIPTION, username));
        temp.add(new Product(R.drawable.icon_settings,
                "Settings", 35.50, 20, 5, SAMPLE_DESCRIPTION, username));
        temp.add(new Product(R.drawable.icon_cart,
                "Cart", 15.14, 10, 6, SAMPLE_DESCRIPTION, username));
        return temp;
    }
    public Iterator masterIterator(){
        return master.iterator();
    }


//customer functions
    public Product getCloneByName(String name){
        for(Product p : master){
            if(p.getName().equals(name)) {
                return (Product)p.singleClone();
            }
        }
        return null;
    }
    public void incrementByName(String name){
        for(Product p : master){
            if(p.getName().equals(name)) {
                p.incrementQuantity();
            }
        }
    }
    public boolean decrementByName(String name){
        for(Product p : master){
            if(p.getName().equals(name)) {
                return p.decrementfromInventory();
            }
        }
        return false;
    }


//seller functions
    public boolean addToBoth(Product object) {
        master.add(object);
        return super.add(object);
    }
    public void load() {
        this.clear();
        for(int x=0; x<master.size();x++){
            if(master.get(x).getSeller().equals(Session.getInstance().getSellerName()))
                this.add(master.get(x));
        }
    }


    private static ArrayList<Product> master=null;
}