package com.example.camilo.shoppingcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by Camilo on 4/29/2016.
 */
public class SellerStatistics extends AppCompatActivity {

    private TextView profit;
    private TextView revenue;
    private TextView costs;
    private TextView currentCosts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_activity);

        profit = (TextView) findViewById(R.id.profit);
        revenue = (TextView) findViewById(R.id.revenues);
        costs = (TextView) findViewById(R.id.costs);
        currentCosts = (TextView) findViewById(R.id.current_costs);

        refresh();

        final Toolbar myToolbar = (Toolbar) findViewById(R.id.product_view_toolbar);
        setSupportActionBar(myToolbar);
    }

    private void refresh(){
        double[] stats = Session.getInstance().getStats();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.DOWN);

        profit.setText("$" + df.format(stats[0]));
        revenue.setText("$" + df.format(stats[1]));
        costs.setText("$" + df.format(stats[2]));
        currentCosts.setText("$" + df.format(stats[3]));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart_menu, menu);
        menu.findItem(R.id.action_payment).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
