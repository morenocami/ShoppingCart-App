package com.example.camilo.shoppingcart;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/26/2016.
 */
public class Session extends AppCompatActivity{

    private Session(){
        master = new Inventory();
    }

    public static Session getInstance(){
        if(instance==null)
            instance = new Session();
        return instance;
    }



//customer functions
    public boolean addProductToCart(String name){
        return ((Customer) currentUser).addToCart(master.getCloneByName(name), master.getQtyByName(name));
    }
    public void removeFromCartByName(String name){
        ((Customer)currentUser).removeFromCart(name);
    }
    public String getCartTotal(){
        return "$ "+String.format("%.2f", ((Customer) currentUser).getCartTotal());
    }
    public String getCartSize(){
        return "Items: " + ((Customer)currentUser).getCartSize();
    }
    public Iterator getCartIterator(){
        return ((Customer)currentUser).getIterator();
    }
    public void checkout(){
        ArrayList<Pair<String, Integer>> items = ((Customer) currentUser).checkout();
        master.checkout(items);
    }
////////////////////////////


    //seller functions
    public boolean isSeller(){
        return currentUser.isSeller();
    }
    public boolean createProduct(int resource, String name, double price, double cost, int qty, String description){
        //check for item uniqueness by name
        for(Product p: master){
            if(p.getName().equals(name))
                return false;
        }

        final Product newProduct = new Product(resource, name, price, cost, qty, description, Session.getInstance().getSellerName());
        if(((Seller) currentUser).addNewProduct(newProduct)) {
            master.add(newProduct);
            saveMaster();
            return true;
        }
        return false;
    }
    public void deleteProduct(String name){
        ((Seller)currentUser).removeFromInventory(name);
        master.deleteProduct(name);
        master.saveMaster(file);
    }
    public void updateProduct(String name, int newQuantity){
        ((Seller)currentUser).updateProduct(name, newQuantity);
        master.updateQty(name, newQuantity);
    }
    public String getSellerInventorySize(){
        return "Items: " + ((Seller)currentUser).getMyInventorySize();
    }
    public Iterator getSellerIterator(){
        return ((Seller)currentUser).getIterator();
    }
    public String getSellerName(){
        return currentUser.getUsername();
    }
    public double[] getStats(){
        return ((Seller)currentUser).getStats();
        }

    //general functions
    public Iterator getMasterIterator(){
        return master.iterator();
    }
    public void setFilesDir(File f){
        file= new File(f,"master");
    }
    public void load(){
        master.loadMaster(file);
        if(currentUser.isSeller()) {
            for (Product p : master) {
                if (p.getSeller().equals(getSellerName())) {
                    ((Seller) currentUser).addNewProduct(p);
                }
            }
        }
    }
    public void saveMaster(){
        master.saveMaster(file);
    }
    public void userLogin(User user){
        currentUser = user;
    }
    public void userLogout(){
        currentUser=null;
        master =null;
        instance=null;
    }

    private File file;
    private User currentUser;
    private Inventory master;
    private static Session instance;
}
