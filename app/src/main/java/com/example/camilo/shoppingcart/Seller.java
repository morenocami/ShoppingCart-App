package com.example.camilo.shoppingcart;

import java.util.Iterator;

/**
 * Created by Camilo on 4/28/2016.
 */
public class Seller extends User {

    public Seller(String username, String password, boolean isSeller) {
        super(username, password, isSeller);
        myInventory = new Inventory();
    }

    public Iterator getIterator(){
//        myInventory.load();
        return myInventory.iterator();
    }

    public int getMyInventorySize(){
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


    public boolean addNewProduct(Product newProduct){
        if(myInventory.isEmpty())
            myInventory.add(newProduct);
        else{
            for(Product p : myInventory){
                if(newProduct.getName().equals(p.getName())) {
                    return false;
                }
            }
            myInventory.add(newProduct);
        }
        return true;
    }
    public boolean removeFromInventory(String productToRemove){
        for(Product product : myInventory){
            if(product.getName().equals(productToRemove)) {
                myInventory.remove(product);
            }
        }
        return true;
    }

    public void updateProduct(String name, int newQuantity){
        for(Product p : myInventory){
            if(p.getName().equals(name)) {
                p.setQty(newQuantity);
            }
        }
    }

//    public void loadMyInventory(){
//        myInventory.load();
//    }

    private Inventory myInventory;
}
