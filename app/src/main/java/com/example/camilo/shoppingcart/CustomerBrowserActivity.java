package com.example.camilo.shoppingcart;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Camilo on 4/18/2016.
 */
public class CustomerBrowserActivity extends AppCompatActivity {

    ListView list;
    CustomAdapter adapter;
    public CustomerBrowserActivity customListView = null;
    public ArrayList<ListItemModel> customListViewValuesArr = new ArrayList<ListItemModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_browser_activity);

        customListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        Resources res =getResources();
        list= ( ListView ) findViewById( R.id.browser_listView );  // List defined in XML ( See Below )

        /**************** Create Custom Adapter *********/
        adapter=new CustomAdapter( customListView, customListViewValuesArr,res );
        list.setAdapter(adapter);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.browser_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browser_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /****** Function to set data in ArrayList *************/
    public void setListData()
    {

        for (int i = 0; i < 11; i++) {

            final ListItemModel sched = new ListItemModel();

            /******* Firstly take data in model object ******/
            sched.setProductName("Company " + i);
            sched.setImage("image" + i);
            sched.setPrice("$" + i);

            /******** Take Model Object in ArrayList **********/
            customListViewValuesArr.add(sched);
        }

    }


    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {
        ListItemModel tempValues = ( ListItemModel ) customListViewValuesArr.get(mPosition);


        // SHOW ALERT
        Toast.makeText(customListView,"" +
                        tempValues.getProductName()+
                        "Image:"+tempValues.getImage()+
                        "Url:"+tempValues.getPrice(),
                        Toast.LENGTH_LONG )
                .show();
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