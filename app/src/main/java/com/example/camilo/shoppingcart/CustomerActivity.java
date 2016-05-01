package com.example.camilo.shoppingcart;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Iterator;

/**
 * Created by Camilo on 4/18/2016.
 */
public class CustomerActivity extends BrowserActivity {

    @Override
    protected int setLayout() {
        return R.layout.browser_activity;
    }

    @Override
    protected int getToolbar() {
        return R.id.browser_toolbar;
    }

    @Override
    protected Activity getContext() {
        return this;
    }

    @Override
    protected Resources getRes() {
        return getResources();
    }

    @Override
    protected Iterator getIterator() {
        return Session.getInstance().getMasterIterator();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK && this==CustomerActivity.this) {
            //Ask the user if they want to quit
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.icon_caution)
                    .setTitle("Confirm logout")
                    .setMessage("Are you sure you want to log out?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Session.getInstance().userLogout();
                            final Intent back = new Intent(CustomerActivity.this, LoginActivity.class);
                            back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(back);
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        }
        else if(event.equals(new KeyEvent(0,0))) {
            return true;
        }
        else{
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_cart:
                startActivity(new Intent(CustomerActivity.this, CartActivity.class));
                return true;
            case R.id.action_settings:

                return true;
            case R.id.action_logout:
                onKeyDown(KeyEvent.KEYCODE_BACK, new KeyEvent(0, 0));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}