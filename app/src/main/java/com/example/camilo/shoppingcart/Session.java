package com.example.camilo.shoppingcart;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.io.File;
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
        if(master.decrementByName(name)) {
            ((Customer) currentUser).addToCart(master.getCloneByName(name));
            return true;
        }
        return false;
    }
    public boolean removeFromCartByName(String name){
        ((Customer)currentUser).removeFromCart(name);
        master.incrementByName(name);
        return true;
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
////////////////////////////


    //seller functions
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
//    public boolean deleteProduct(String name){
//        ((Customer)currentUser).removeFromCart(name);
//        master.incrementByName(name);
//        return true;
//    }
    public void updateProduct(String name, int newQuantity){
        if(newQuantity==0)
            ((Seller)currentUser).removeFromInventory(name);
        else
            ((Seller)currentUser).updateProduct(name, newQuantity);
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
