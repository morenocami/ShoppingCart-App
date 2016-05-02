package com.example.camilo.shoppingcart;

import android.support.v7.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/26/2016.
 */
public class Session extends AppCompatActivity{

    private static String MASTER = "master";
    private Session(){
        master = new Inventory();
        setFilepath(LoginActivity.filePath);
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
        ArrayList<SerializableStringPair<String, Integer>> items = ((Customer) currentUser).checkout();
        master.checkout(items);
    }



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
        master.saveMaster(new File(filepath, MASTER));
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
    public void setFilepath(File f){
        filepath = f;
    }
    public File getFilepath(){
        return filepath;
    }
    public void load(){
        master.loadMaster(new File(filepath, MASTER));
        if(currentUser.isSeller()) {
            for (Product p : master) {
                if (p.getSeller().equals(getSellerName())) {
                    ((Seller) currentUser).addNewProduct(p);
                }
            }
        }
    }
    public void saveMaster(){
        master.saveMaster(new File(filepath, MASTER));
    }
    public void userLogin(User user){
        currentUser = user;
        load();
        if(currentUser.isSeller())
            ((Seller)currentUser).updateStatistics();

    }
    public void userLogout(){
        if(currentUser.isSeller())
            ((Seller)currentUser).makeSaveState();
//        currentUser.logout(((Seller)currentUser).getLastInventoryState(), currentUser.getUsername());
        currentUser=null;
        master =null;
        instance=null;
    }



    private File filepath;
    private User currentUser;
    private Inventory master;
    private static Session instance;
}
