package com.example.camilo.shoppingcart;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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


    private void showCustomDialog() {
        LayoutInflater mInflater = this.getLayoutInflater();
        View mView = mInflater.inflate(R.layout.payment_layout, null);
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.icon_payment)
                .setTitle("Confirm transaction")
                .setMessage("If you've reviewed your cart, enter your payment information to complete your purchase.")
                .setView(mView)
                .setPositiveButton("Purchase", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Session.getInstance().checkout();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        menu.findItem(R.id.action_payment).setIcon(R.drawable.icon_payment);
        menu.findItem(R.id.action_payment).setTitle("Checkout");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_payment:
                showCustomDialog();
                return true;
            case R.id.action_back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
