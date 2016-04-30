package com.example.camilo.shoppingcart;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/28/2016.
 */
public class Seller extends User {

    public Seller(String username, String password, boolean isSeller) {
        super(username, password, isSeller);
        myInventory = new ArrayList<>();
    }

    public Iterator getIterator(){
        return myInventory.iterator();
    }

    public int getInventorySize(){
        int count=0;
        for(Product p : myInventory)
            count+=p.getQty();
        return count;
    }

    public double getInventoryTotal(){
        double sum=0;
        for (Product p:myInventory) {
            sum += (p.getSellPrice()*p.getQty());
        }
        return sum;
    }

    public void addToInventory(Product newProduct){
        if(myInventory.isEmpty())
            myInventory.add((Product)newProduct.clone());
        else{
            for(Product cartItem : myInventory){
                if(newProduct.getName().equals(cartItem.getName())) {
                    cartItem.incrementQuantity();
                    return;
                }
            }
            myInventory.add((Product)newProduct.clone());
        }
    }
    public boolean removeFromInventory(String productToRemove){
        for(Product cartItem : myInventory){
            if(productToRemove.equals(cartItem.getName())) {
                if(!cartItem.decrementfromCart()) {
                    myInventory.remove(cartItem);
                    return true;
                }
            }
        }
        return true;
    }



    private ArrayList<Product> myInventory;
}
