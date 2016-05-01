package com.example.camilo.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Camilo on 4/29/2016.
 */
public class SellerStatistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.product_view_toolbar);
        setSupportActionBar(myToolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_back:
                startActivity(new Intent(SellerStatistics.this, SellerActivity.class));
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
                                final Intent back = new Intent(SellerStatistics.this, LoginActivity.class);
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
