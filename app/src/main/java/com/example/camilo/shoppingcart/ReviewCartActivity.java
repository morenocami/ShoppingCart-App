package com.example.camilo.shoppingcart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

/**
 * Created by Camilo on 4/26/2016.
 */
public class ReviewCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_cart_activity);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.review_toolbar);
        setSupportActionBar(myToolbar);

    }
}
