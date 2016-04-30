package com.example.camilo.shoppingcart;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/28/2016.
 */
public class Customer extends User {

    public Customer(String username, String password, boolean isSeller) {
        super(username, password, isSeller);
        shoppingCart = new ArrayList<>();
    }

    public Iterator getIterator(){
        return shoppingCart.iterator();
    }

    public void addToCart(Product newProduct){
        if(shoppingCart.isEmpty())
            shoppingCart.add((Product)newProduct.clone());
        else{
            for(Product cartItem : shoppingCart){
                if(newProduct.getName().equals(cartItem.getName())) {
                    cartItem.incrementQuantity();
                    return;
                }
            }
            shoppingCart.add((Product)newProduct.clone());
        }
    }
    public boolean removeFromCart(String productToRemove){
        for(Product cartItem : shoppingCart){
            if(productToRemove.equals(cartItem.getName())) {
                if(!cartItem.decrementfromCart()) {
                    shoppingCart.remove(cartItem);
                    return true;
                }
            }
        }
        return true;
    }

    public double getCartTotal(){
        double sum=0;
        for (Product p:shoppingCart) {
            sum += (p.getSellPrice()*p.getQty());
        }
        return sum;
    }

    public int getCartSize(){
        int count=0;
        for(Product p : shoppingCart)
            count+=p.getQty();
        return count;
    }

    private ArrayList<Product> shoppingCart;
}
