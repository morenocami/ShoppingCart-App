package com.example.camilo.shoppingcart;

/**
 * Created by Camilo on 4/28/2016.
 */
public class ListItemModel {
    private  String productName ="";
    private  int image = 0;
    private  String price ="";
    private int qty = 0;

    /*********** Set Methods ******************/

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    /*********** Get Methods ****************/

    public String getProductName() {
        return this.productName;
    }

    public int getImage() {
        return this.image;
    }

    public String getPrice() {
        return this.price;
    }

    public int getQty() {
        return qty;
    }
}
