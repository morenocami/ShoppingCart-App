package com.example.camilo.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Camilo on 4/30/2016.
 */
public class ProductViewFull extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_full);

        ListItemModel item = (ListItemModel)getIntent().getSerializableExtra("content");

        ((ImageView) findViewById(R.id.product_image_full)).setImageResource(item.getImageResource());
        ((TextView) findViewById(R.id.product_name_full)).setText(item.getProductName());
        ((TextView) findViewById(R.id.product_price_full)).setText("$" + item.getPrice());
        ((TextView) findViewById(R.id.product_qty_full)).setText("Qty: " + item.getQty());
        ((TextView) findViewById(R.id.product_description_full)).setText(item.getDescription());

        final Toolbar toolbar = (Toolbar) findViewById(R.id.product_view_toolbar);
        setSupportActionBar(toolbar);
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
                startActivity(new Intent(ProductViewFull.this, CustomerActivity.class));
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
                                final Intent back = new Intent(ProductViewFull.this, LoginActivity.class);
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
