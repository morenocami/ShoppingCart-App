package com.example.camilo.shoppingcart;

import java.io.Serializable;

/**
 * Created by Camilo on 4/28/2016.
 */
public class ListItemModel implements Serializable{
    private  String productName ="";
    private  int imageResource = 0;
    private  String price ="";
    private  int qty = 0;
    private  String description="";

    /*********** Set Methods ******************/

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /*********** Get Methods ****************/

    public String getProductName() {
        return this.productName;
    }

    public int getImageResource() {
        return this.imageResource;
    }

    public String getPrice() {
        return this.price;
    }

    public int getQty() {
        return qty;
    }

    public String getDescription() {
        return description;
    }
}
