package com.example.camilo.shoppingcart;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/29/2016.
 */
public abstract class BrowserActivity extends AppCompatActivity {

    private ArrayList<ListItemModel> listItems = new ArrayList<>();
    private TextView listSize;
    private TextView cartTotal;
    private Button sellerAdder;
    private CustomAdapter adapter;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());

        listSize =(TextView)findViewById(R.id.browser_cart_size);
        cartTotal=(TextView)findViewById(R.id.browser_cart_total);
        sellerAdder = (Button)findViewById(R.id.seller_add_product);

        populateListItems();
        list = (ListView) findViewById(R.id.browser_listView);
        adapter = new CustomAdapter( getContext(), listItems, getRes() );

        if (list != null)
            list.setAdapter(adapter);
        else
            Toast.makeText(this, "UI error", Toast.LENGTH_SHORT).show();

        final Toolbar myToolbar = (Toolbar) findViewById(getToolbar());
        setSupportActionBar(myToolbar);
    }

    @Override
    protected void onResume() {
        if(this instanceof CustomerActivity) {
            listSize.setText(ShoppingSession.getInstance().getCartSize());
            cartTotal.setText(ShoppingSession.getInstance().getCartTotal());
            cartTotal.setVisibility(View.VISIBLE);
            sellerAdder.setVisibility(View.GONE);

            listItems.clear();
            populateListItems();
            adapter.updateList(listItems);
        }
        else if(this instanceof SellerActivity) {
//            listSize.setText(ShoppingSession.getInstance().getMyInventorySize());
            cartTotal.setVisibility(View.GONE);
            sellerAdder.setVisibility(View.VISIBLE);

            listItems.clear();
            populateListItems();
            adapter.updateList(listItems);
        }
        super.onResume();
    }

    private void populateListItems() {
        Iterator iterator = getIterator();
        while (iterator.hasNext()) {
            final ListItemModel listItem = new ListItemModel();
            final Product x = (Product) iterator.next();

            listItem.setProductName(x.getName());
            listItem.setImage(x.getImageResource());
            listItem.setPrice(Double.toString(x.getSellPrice()));
            listItem.setQty(x.getQty());

            listItems.add(listItem);
        }
    }

    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition, boolean addRequest)
    {
        final ListItemModel tempValues = listItems.get(mPosition);

        if(addRequest){
            if(getContext() instanceof CustomerActivity){
                if(ShoppingSession.getInstance().addProduct(tempValues.getProductName())) {
                    listSize.setText(ShoppingSession.getInstance().getCartSize());
                    cartTotal.setText(ShoppingSession.getInstance().getCartTotal());
                }
            }
            else if(getContext() instanceof CartActivity){
                if(ShoppingSession.getInstance().removeProduct(tempValues.getProductName())) {
                    listSize.setText(ShoppingSession.getInstance().getCartSize());
                    cartTotal.setText(ShoppingSession.getInstance().getCartTotal());
                }
                else{
                    listSize.setText(ShoppingSession.getInstance().getCartSize());
                    cartTotal.setText(ShoppingSession.getInstance().getCartTotal());
                }
            }
            else{

            }
            listItems.clear();
            populateListItems();
            if(listItems.isEmpty())
                list.setAdapter(new CustomAdapter( getContext(), listItems, getRes() ));
            else
                adapter.updateList(listItems);
        }
        else {
            Toast.makeText(this, "Name: " + tempValues.getProductName() +
                            "\nImage Resource ID: " + tempValues.getImage() +
                            "\nPrice: " + tempValues.getPrice() +
                            "\nQty: " + tempValues.getQty(),
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    protected abstract int setLayout();
    protected abstract int getToolbar();
    protected abstract android.app.Activity getContext();
    protected abstract Resources getRes();
    protected abstract Iterator getIterator();

}
