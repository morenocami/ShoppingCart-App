package com.example.camilo.shoppingcart;

/**
 * Created by Camilo on 4/28/2016.
 */
public class ListItemModel {
    private  String ProductName ="";
    private  String image ="";
    private  String price ="";

    /*********** Set Methods ******************/

    public void setProductName(String CompanyName)
    {
        this.ProductName = CompanyName;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public void setPrice(String Url)
    {
        this.price = Url;
    }

    /*********** Get Methods ****************/

    public String getProductName()
    {
        return this.ProductName;
    }

    public String getImage()
    {
        return this.image;
    }

    public String getPrice()
    {
        return this.price;
    }
}
