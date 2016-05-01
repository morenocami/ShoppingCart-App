package com.example.camilo.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Camilo on 4/30/2016.
 */
public class ProductCreation extends AppCompatActivity {

    private ImageView imageView;
    private EditText nameView;
    private EditText priceView;
    private EditText qtyView;
    private EditText descriptionView;
    private int imageResource=0;

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
                final Spinner spinner = new Spinner(ProductCreation.this);
                final ArrayList<Integer> spinnerArray = new ArrayList<>();
                spinnerArray.add(R.drawable.icon_add_to_cart);
                spinnerArray.add(R.drawable.icon_back);
                spinnerArray.add(R.drawable.icon_cart);
                spinnerArray.add(R.drawable.icon_stats);
                spinnerArray.add(R.drawable.icon_edit);
                spinnerArray.add(R.drawable.icon_green_check);
                spinnerArray.add(R.drawable.icon_refresh);
                ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<>(ProductCreation.this, android.R.layout.simple_spinner_item, spinnerArray);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinnerArrayAdapter);

                new AlertDialog.Builder(ProductCreation.this)
                        .setIcon(R.drawable.icon_add_to_cart)
                        .setTitle("Select product image")
                        .setMessage("Select a choice from the dropdown menu.")
                        .setView(spinner)
                        .setPositiveButton("Place Image", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                imageView.setImageResource((Integer)spinner.getSelectedItem());
                                imageResource=(Integer)spinner.getSelectedItem();
                            }

                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.product_view_toolbar);
        setSupportActionBar(myToolbar);
    }

    private int createNewProduct(){
        if(imageResource == 0)
            return 0;

        if(nameView.toString().isEmpty() ||
                priceView.toString().isEmpty() ||
                qtyView.toString().isEmpty() ||
                descriptionView.toString().isEmpty())
            return 1;

        if(Session.getInstance().createProduct(
                imageResource,
                nameView.getText().toString(),
                Double.parseDouble(priceView.getText().toString()),
                Double.parseDouble(priceView.getText().toString()),
                Integer.parseInt(qtyView.getText().toString()),
                descriptionView.getText().toString()))
            return 3;
        return 2;
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
                                switch(createNewProduct()) {
                                    case 0:
                                        Toast.makeText(ProductCreation.this,"Press the image above to select an image.",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 1:
                                        Toast.makeText(ProductCreation.this,"Fill In all fields.",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 2:
                                        Toast.makeText(ProductCreation.this,"Item exists!",Toast.LENGTH_SHORT).show();
                                        break;
                                    case 3:
                                        final Intent back = new Intent(ProductCreation.this, SellerActivity.class);
                                        back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(back);
                                        break;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
