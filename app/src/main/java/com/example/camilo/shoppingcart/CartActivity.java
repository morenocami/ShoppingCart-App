package com.example.camilo.shoppingcart;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Iterator;

/**
 * Created by Camilo on 4/26/2016.
 */
public class CartActivity extends BrowserActivity {

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
        return Session.getInstance().getCartIterator();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_home:
                startActivity(new Intent(CartActivity.this, CustomerActivity.class));
                return true;
            case R.id.action_settings:

                return true;
            case R.id.action_logout:
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.icon_caution)
                        .setTitle("Confirm logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Session.getInstance().userLogout();
                                final Intent back = new Intent(CartActivity.this, LoginActivity.class);
                                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(back);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
