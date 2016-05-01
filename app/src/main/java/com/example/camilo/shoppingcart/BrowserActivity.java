package com.example.camilo.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
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

        Session.getInstance().load();

        if(this instanceof SellerActivity) {
            sellerAdder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext(),ProductCreation.class));
                }
            });
        }

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
            if(this instanceof CustomerActivity || this instanceof CartActivity) {
                listSize.setText(Session.getInstance().getCartSize());
                cartTotal.setText(Session.getInstance().getCartTotal());
                cartTotal.setVisibility(View.VISIBLE);
                sellerAdder.setVisibility(View.GONE);

                listItems.clear();
                populateListItems();
                adapter.updateList(listItems);
            }
            else if(this instanceof SellerActivity) {
                listSize.setText(Session.getInstance().getSellerInventorySize());

                cartTotal.setVisibility(View.GONE);
                sellerAdder.setVisibility(View.VISIBLE);

                listItems.clear();
                populateListItems();
                adapter.updateList(listItems);
            }
        }
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Session.getInstance().saveMaster();
        }
        catch (NullPointerException n){
            n.printStackTrace();
        }
    }

    private void populateListItems() {
        Iterator iterator = getIterator();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);

        while (iterator.hasNext()) {
            final ListItemModel listItem = new ListItemModel();
            final Product x = (Product) iterator.next();

            listItem.setProductName(x.getName());
            listItem.setImageResource(x.getImageResource());
            listItem.setPrice(df.format(x.getSellPrice()));
            listItem.setCost(df.format(x.getCost()));
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
            //the button in this activity is to addproduct to cart
            if(getContext() instanceof CustomerActivity){
                if(Session.getInstance().addProductToCart(item.getProductName())) {
                    listSize.setText(Session.getInstance().getCartSize());
                    cartTotal.setText(Session.getInstance().getCartTotal());
                }
            }
            //the button in this activity is to remove from cart
            else if(getContext() instanceof CartActivity){
                if(Session.getInstance().removeFromCartByName(item.getProductName())) {
                    listSize.setText(Session.getInstance().getCartSize());
                    cartTotal.setText(Session.getInstance().getCartTotal());
                }
                else{
                    listSize.setText(Session.getInstance().getCartSize());
                    cartTotal.setText(Session.getInstance().getCartTotal());
                }
            }
            //the button in this activity is to edit an item's quantity
            else if(getContext() instanceof SellerActivity){
                final EditText input = new EditText(getContext());
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                input.setWidth(30);
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.icon_refresh)
                        .setTitle("Confirm quantity")
                        .setMessage("Update product quantity and press \"Update\"")
                        .setView(input)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Session.getInstance().updateProduct(item.getProductName(),
                                        Integer.parseInt(input.getText().toString()));
                            }

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
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
            Bundle b = new Bundle();
            b.putSerializable("content", item);
            startActivity(new Intent(getContext(), ProductView.class).putExtras(b));
        }
    }


    protected abstract int setLayout();
    protected abstract int getToolbar();
    protected abstract android.app.Activity getContext();
    protected abstract Resources getRes();
    protected abstract Iterator getIterator();

}
