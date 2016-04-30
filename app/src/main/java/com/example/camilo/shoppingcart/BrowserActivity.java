package com.example.camilo.shoppingcart;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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

    protected FragmentManager fm = getSupportFragmentManager();


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
    public void onWindowFocusChanged(boolean hasFocus) {
        if(hasFocus){
            if(this instanceof CustomerActivity) {
                listSize.setText(Session.getInstance().getCartSize());
                cartTotal.setText(Session.getInstance().getCartTotal());
                cartTotal.setVisibility(View.VISIBLE);
                sellerAdder.setVisibility(View.GONE);

                listItems.clear();
                populateListItems();
                adapter.updateList(listItems);
            }
            else if(this instanceof SellerActivity) {
                cartTotal.setVisibility(View.GONE);
                sellerAdder.setVisibility(View.VISIBLE);

                listItems.clear();
                populateListItems();
                adapter.updateList(listItems);
            }
        }
        super.onWindowFocusChanged(hasFocus);
    }

    private void populateListItems() {
        Iterator iterator = getIterator();
        while (iterator.hasNext()) {
            final ListItemModel listItem = new ListItemModel();
            final Product x = (Product) iterator.next();

            listItem.setProductName(x.getName());
            listItem.setImageResource(x.getImageResource());
            listItem.setPrice(Double.toString(x.getSellPrice()));
            listItem.setQty(x.getQty());
            listItem.setDescription(x.getDescription());

            listItems.add(listItem);
        }
    }

    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition, boolean request)
    {
        final ListItemModel item = listItems.get(mPosition);

        //a request is when the right button is pressed
        if(request){
            //the button in this activity is to add to cart
            if(getContext() instanceof CustomerActivity){
                if(Session.getInstance().addProductToCart(item.getProductName())) {
                    listSize.setText(Session.getInstance().getCartSize());
                    cartTotal.setText(Session.getInstance().getCartTotal());
                }
            }
            //the button in this activity is to remove from cart
            else if(getContext() instanceof CartActivity){
                if(Session.getInstance().removeProductFromCart(item.getProductName())) {
                    listSize.setText(Session.getInstance().getCartSize());
                    cartTotal.setText(Session.getInstance().getCartTotal());
                }
                else{
                    listSize.setText(Session.getInstance().getCartSize());
                    cartTotal.setText(Session.getInstance().getCartTotal());
                }
            }
            //the button in this activity is to edit an item
            else if(getContext() instanceof SellerActivity){


            }
            listItems.clear();
            populateListItems();
            if(listItems.isEmpty())
                list.setAdapter(new CustomAdapter( getContext(), listItems, getRes() ));
            else
                adapter.updateList(listItems);
        }
        //touching the rest of the list item results in the product's full view
        else {
            startActivity(new Intent(getContext(), ProductViewFull.class).
                    putExtra("content", item));
        }
    }


    protected abstract int setLayout();
    protected abstract int getToolbar();
    protected abstract android.app.Activity getContext();
    protected abstract Resources getRes();
    protected abstract Iterator getIterator();

}
