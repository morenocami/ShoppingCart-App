package com.example.camilo.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Camilo on 4/30/2016.
 */
public class ProductCreation extends AppCompatActivity {

    private ImageView imageView;
    private EditText nameView;
    private EditText priceView;
    private EditText qtyView;
    private EditText descriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_activity);

        imageView = (ImageView) findViewById(R.id.product_image_full);
        nameView = (EditText) findViewById(R.id.product_name_full);
        priceView = (EditText) findViewById(R.id.product_price_full);
        qtyView = (EditText) findViewById(R.id.product_qty_full);
        descriptionView = (EditText) findViewById(R.id.product_description_full);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.product_view_toolbar);
        setSupportActionBar(myToolbar);
    }

    private boolean createNewProduct(){
        return Session.getInstance().createProduct(
//                imageView.getId(),
                R.drawable.icon_caution,
                nameView.getText().toString(),
                Double.parseDouble(priceView.getText().toString()),
                Double.parseDouble(priceView.getText().toString()),
                Integer.parseInt(qtyView.getText().toString()),
                descriptionView.getText().toString());
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
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.icon_caution)
                        .setTitle("Confirm product creation")
                        .setMessage("Submit new product to my inventory?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(createNewProduct()) {
                                    final Intent back = new Intent(ProductCreation.this, SellerActivity.class);
                                    back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(back);
                                }
                                else{
                                    Toast.makeText(ProductCreation.this,"Item exists!",Toast.LENGTH_SHORT).show();
                                }
                            }

                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent back = new Intent(ProductCreation.this, SellerActivity.class);
                                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(back);
                            }

                        })
                        .show();
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
                                final Intent back = new Intent(ProductCreation.this, LoginActivity.class);
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
