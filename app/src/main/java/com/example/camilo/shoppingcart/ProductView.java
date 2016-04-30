package com.example.camilo.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by Camilo on 4/30/2016.
 */
public class ProductView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_activity);

        ImageView imageView = (ImageView) findViewById(R.id.product_image_full);
        EditText nameView = (EditText) findViewById(R.id.product_name_full);
        EditText priceView = (EditText) findViewById(R.id.product_price_full);
        EditText qtyView = (EditText) findViewById(R.id.product_qty_full);
        EditText descriptionView = (EditText) findViewById(R.id.product_description_full);

        ListItemModel item = (ListItemModel) getIntent().getExtras().get("content");

        imageView.setImageResource(item.getImageResource());
        nameView.setText(item.getProductName());
        priceView.setText("" + item.getPrice());
        qtyView.setText("" + item.getQty());
        descriptionView.setText(item.getDescription());

        nameView.setKeyListener(null);
        priceView.setKeyListener(null);
        qtyView.setKeyListener(null);
        descriptionView.setKeyListener(null);

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
                startActivity(new Intent(ProductView.this, CustomerActivity.class));
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
                                final Intent back = new Intent(ProductView.this, LoginActivity.class);
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
