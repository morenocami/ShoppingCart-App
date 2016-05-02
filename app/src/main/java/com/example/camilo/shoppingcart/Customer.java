package com.example.camilo.shoppingcart;



import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/28/2016.
 */
public class Customer extends User {

    private static final long serialVersionUID = 315684651;

    public Customer(String username, String password, boolean isSeller) {
        super(username, password, isSeller);
        shoppingCart = new ArrayList<>();
    }

    public Iterator getIterator(){
        return shoppingCart.iterator();
    }

    public boolean addToCart(Product newProduct, int qtyAvailable){
        //no items available
        if(qtyAvailable==0) return false;
        //cart empty, add first item
        if(shoppingCart.isEmpty()) {
            shoppingCart.add(newProduct);
            return true;
        }
        //if item found in cart, increment qty by 1
        for(Product cartItem : shoppingCart){
            if(newProduct.getName().equals(cartItem.getName())) {
                if(!(cartItem.getQty()==qtyAvailable)){
                    cartItem.incrementQuantity();
                    return true;
                }
                return false;
            }
        }
        //item not in cart, add it
        shoppingCart.add(newProduct);
        return true;
    }

    public void removeFromCart(String productToRemove){
        for(Product cartItem : shoppingCart){
            if(productToRemove.equals(cartItem.getName())) {
                if(!cartItem.decrementfromCart()) {
                    shoppingCart.remove(cartItem);
                    return;
                }
                break;
            }
        }
    }
    public double getCartTotal(){
        double sum=0;
        for (Product p : shoppingCart) {
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
    public ArrayList<SerializableStringPair<String,Integer>> checkout(){
        ArrayList<SerializableStringPair<String,Integer>> items = new ArrayList<>();

        for(Product p : shoppingCart){
            items.add(new SerializableStringPair<>(p.getName(), p.getQty()));
        }
        shoppingCart.clear();

        return items;
    }



    private ArrayList<Product> shoppingCart;
}
