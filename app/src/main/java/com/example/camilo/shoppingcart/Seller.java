package com.example.camilo.shoppingcart;


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
import java.util.Iterator;

/**
 * Created by Camilo on 4/28/2016.
 */
public class Seller extends User {

    private static final long serialVersionUID = 315684651;

    public Seller(String username, String password, boolean isSeller) {
        super(username, password, isSeller);
        myInventory = new Inventory();
    }

    public Iterator getIterator(){
        return myInventory.iterator();
    }

    public int getMyInventorySize(){
        int count=0;
        for(Product p : myInventory)
            count+=p.getQty();
        return count;
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
        currentCost = getInventoryTotal();
        return true;
    }
    public boolean removeFromInventory(String productToRemove){
        for(Product product : myInventory){
            if(product.getName().equals(productToRemove)) {
                myInventory.remove(product);
            }
        }
        currentCost = getInventoryTotal();
        return true;
    }

    public void updateProduct(String name, int newQuantity){
        for(Product p : myInventory){
            if(p.getName().equals(name)) {
                p.setQty(newQuantity);
            }
        }
        currentCost = getInventoryTotal();
    }


    public void makeSaveState(){
        lastInventoryState = new ArrayList<>();
        for(Product p : myInventory){
            lastInventoryState.add(new SerializableStringPair<>(p.getName(), p.getQty()));
        }
        
        try {
            FileInputStream fis = new FileInputStream(Session.getInstance().getFilesDir());
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<ArrayList> userList = ((ArrayList<ArrayList>) ois.readObject());

            for (User user :userList) {
                if (user.checkUsername(name)) {
                    ((Seller)user).setLastInventoryState(state);
                }
            }


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

        try {
            FileOutputStream fos = new FileOutputStream(LoginActivity.file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(userList);
        }

    }

    public void updateStatistics(){
        if(lastInventoryState==null){
            return;
        }
        for(SerializableStringPair<String, Integer> last : lastInventoryState){
            for(Product p : myInventory){
                if(last.first.equals(p.getName())){
                    if(p.getQty()<last.second) {
                        revenue += (last.second - p.getQty()) * p.getSellPrice();
                        costs += (last.second - p.getQty()) * p.getCost();
                    }
                }
            }
        }
        profit += revenue - costs;
        currentCost = getInventoryTotal();
    }

    private double getInventoryTotal(){
        double sum=0;
        for (Product p:myInventory) {
            sum += (p.getCost()*p.getQty());
        }
        return sum;
    }

    public double[] getStats(){
        return new double[]{profit, revenue, costs, currentCost};
    }



    private Inventory myInventory;
    private ArrayList<SerializableStringPair<String,Integer>> lastInventoryState;
    private double profit=0, revenue=0, costs=0, currentCost=0;
}
