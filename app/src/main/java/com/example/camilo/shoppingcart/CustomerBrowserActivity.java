package com.example.camilo.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Camilo on 4/18/2016.
 */
public class CustomerBrowserActivity extends AppCompatActivity {

    private ArrayList<ListItemModel> listItems = new ArrayList<>();
    private TextView cartSize;
    private TextView cartTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_browser_activity);

        cartSize=(TextView)findViewById(R.id.browser_cart_size);
        cartTotal=(TextView)findViewById(R.id.browser_cart_total);

        populateListItems();
        final ListView list = (ListView) findViewById(R.id.browser_listView);

        if (list != null) {
            list.setAdapter( new CustomAdapter( this, listItems, getResources() ) );
        }
        else
            Toast.makeText(this,"UI error",Toast.LENGTH_SHORT).show();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.browser_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void populateListItems() {
        Iterator iterator = ShoppingSession.getInstance().getIterator();
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
        ListItemModel tempValues = listItems.get(mPosition);

        if(addRequest){
            if(ShoppingSession.getInstance().addToCart(tempValues.getProductName())) {
                cartSize.setText(ShoppingSession.getInstance().getCartSize());
                cartTotal.setText(ShoppingSession.getInstance().getCartTotal());
            }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:

                return true;
            case R.id.action_logout:
                startActivity(new Intent(CustomerBrowserActivity.this, LoginActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}