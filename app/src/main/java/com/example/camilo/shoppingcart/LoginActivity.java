package com.example.camilo.shoppingcart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Camilo on 4/18/2016.
 */
public class LoginActivity extends AppCompatActivity{

    public static final String USERS_FILE = "users";

    private TextView username;
    private TextView password;
    private Switch switchNewUser;
    private Switch switchSeller;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        username = (TextView) findViewById(R.id.login_editText1);
        password = (TextView) findViewById(R.id.login_editText2);
        switchNewUser = (Switch)findViewById(R.id.login_switch1);
        switchSeller = (Switch)findViewById(R.id.login_switch2);

        users = new ArrayList<>();

        try {
            FileInputStream fis = openFileInput(USERS_FILE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users= (ArrayList<User>)ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //clears values; they might have been populated by earlier User
        username.setText("b");
        password.setText("b");
        switchNewUser.setChecked(false);
        switchSeller.setChecked(true);
    }

    public void login(View v){
        //if username empty, not alphanumerical, or password empty, WILL NOT try to login
        if(username.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter your username.",
                    Toast.LENGTH_SHORT).show();
        else if(!username.getText().toString().matches("[a-zA-Z0-9]+"))
            Toast.makeText(getApplicationContext(), "Username is not alphanumeric." +
                    " No spaces!", Toast.LENGTH_SHORT).show();
        else if(password.getText().toString().isEmpty())
            Toast.makeText(getApplicationContext(), "Enter your password.",
                    Toast.LENGTH_SHORT).show();
        else{
            final Button button = (Button)findViewById(R.id.login_button);
            final boolean isNew = switchNewUser.isChecked();
            final boolean isSeller = switchSeller.isChecked();
            boolean matches = false;
            button.setEnabled(false);
            button.setText("LOADING...");

            //if new user, check that username is unique and create the selected account
            if(isNew){
                //check uniqueness
                for (User x :users) {
                    if (x.checkUsername(username.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Username is taken," +
                                " try another.", Toast.LENGTH_SHORT).show();
                        button.setEnabled(true);
                        button.setText("Login");
                        return;
                    }
                }

                final User newUser;
                //username unique, adding account
                if(switchSeller.isChecked()) {
                    newUser = new Seller(username.getText().toString(),
                            password.getText().toString(), isSeller);
                    users.add(newUser);
                    newUser.login();
                }
                else {
                    newUser = new Customer(username.getText().toString(),
                            password.getText().toString(),isSeller);
                    users.add(newUser);
                    newUser.login();
                }

                //save new users list
                try {
                    //saves vector to file
                    FileOutputStream fos = openFileOutput(USERS_FILE, MODE_PRIVATE);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(users);
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(isSeller)
                    startActivity(new Intent(LoginActivity.this, SellerActivity.class));
                else
                    startActivity(new Intent(LoginActivity.this, CustomerActivity.class));
                switchSeller.setChecked(false);
                switchNewUser.setChecked(false);
            }
            //login attempt; if username is found, check account type and then check password
            // if password matches, perform customer login or pass switchSeller to next activity and go to appropriate browser
            else {
                for (int x = 0; x < users.size(); x++) {
                    if (users.get(x).checkUsername(username.getText().toString())) {
                        //check if switchSeller
                        if(users.get(x).isSeller()&&isSeller){
                            //check switchSeller password
                            if (users.get(x).checkPassword(password.getText().toString())) {
                                matches = true;
                                users.get(x).login();
                                break;
                            }
                        }
                        else if(!users.get(x).isSeller()&&!isSeller){
                            //check customer password
                            if (users.get(x).checkPassword(password.getText().toString())) {
                                matches = true;
                                users.get(x).login();
                                break;
                            }
                        }
                    }
                }
                if (matches) {
                    if(isSeller)
                        startActivity(new Intent(LoginActivity.this, SellerActivity.class));
                    else
                        startActivity(new Intent(LoginActivity.this, CustomerActivity.class));
                    switchSeller.setChecked(false);
                }
                else {
                    Toast.makeText(getApplicationContext(), "Username/Password" +
                            " combo incorrect.", Toast.LENGTH_SHORT).show();
                }

            }
            button.setEnabled(true);
            button.setText("Login");
            password.setText("");
        }
    }
}
