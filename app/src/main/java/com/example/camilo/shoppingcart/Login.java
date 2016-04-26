package com.example.camilo.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Camilo on 4/18/2016.
 */
public class Login extends AppCompatActivity{

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);


    }

    public void login(View v){

        startActivity(new Intent(Login.this, Browser.class));
    }
}
