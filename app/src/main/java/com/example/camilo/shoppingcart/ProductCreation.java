package com.example.camilo.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Camilo on 4/30/2016.
 */
public class ProductCreation extends AppCompatActivity {

    private ImageView imageView;
    private EditText nameView;
    private EditText priceView;
    private EditText costView;
    private EditText qtyView;
    private EditText descriptionView;
    private int imageResource=0;
    private boolean isKeyboardHidden = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view_activity);

        imageView = (ImageView) findViewById(R.id.product_image_full);
        nameView = (EditText) findViewById(R.id.product_name_full);
        priceView = (EditText) findViewById(R.id.product_price_full);
        costView = (EditText) findViewById(R.id.product_cost_full);
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

        final View activityRootView = findViewById(R.id.product_view_root);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    isKeyboardHidden=false;
                }
            }
        });

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.product_view_toolbar);
        setSupportActionBar(myToolbar);
    }

    private int createNewProduct(){
        if(imageResource == 0)
            return 0;

        if(nameView.getText().toString().isEmpty() ||
                priceView.getText().toString().isEmpty() ||
                costView.getText().toString().isEmpty() ||
                qtyView.getText().toString().isEmpty() ||
                descriptionView.getText().toString().isEmpty())
            return 1;

        if(Session.getInstance().createProduct(
                imageResource,
                nameView.getText().toString(),
                Double.parseDouble(priceView.getText().toString()),
                Double.parseDouble(costView.getText().toString()),
                Integer.parseInt(qtyView.getText().toString()),
                descriptionView.getText().toString()))
            return 3;
        return 2;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK && this==ProductCreation.this) {
            if(!isKeyboardHidden)
                isKeyboardHidden=true;
            else{
                //Ask the user if they want to quit
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.icon_caution)
                        .setTitle("Confirm")
                        .setMessage("Cancel product creation?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Intent back = new Intent(ProductCreation.this, SellerActivity.class);
                                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(back);
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
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
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        menu.findItem(R.id.action_payment).setIcon(R.drawable.icon_green_check);
        menu.findItem(R.id.action_payment).setTitle("Create Product");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_back:
                final Intent back = new Intent(ProductCreation.this, SellerActivity.class);
                back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(back);
                return true;
            case R.id.action_payment:
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
                        .setNegativeButton("No", null)
                        .show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
