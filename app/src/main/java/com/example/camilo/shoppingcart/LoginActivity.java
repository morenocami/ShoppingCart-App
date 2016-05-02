package com.example.camilo.shoppingcart;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

/**
 * Created by Camilo on 4/18/2016.
 */
public class LoginActivity extends AppCompatActivity{

    private static String USERS = "users";

    public static File filePath;
    public static File file;

    private TextView username;
    private TextView password;
    private Switch switchNewUser;
    private Switch switchSeller;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        filePath = getFilesDir();
        file = new File(getFilesDir(), USERS);

        username = (TextView) findViewById(R.id.login_editText1);
        password = (TextView) findViewById(R.id.login_editText2);
        switchNewUser = (Switch)findViewById(R.id.login_switch1);
        switchSeller = (Switch)findViewById(R.id.login_switch2);
        switchSeller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    username.setText("b");
                    password.setText("b");
                } else {
                    username.setText("a");
                    password.setText("a");
                }
            }
        });

        users = new ArrayList<>();

        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            users = ((ArrayList<User>) ois.readObject());
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (OptionalDataException e) {
            e.printStackTrace();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //clears values; they might have been populated by earlier User
        username.setText("");
        password.setText("");
        switchNewUser.setChecked(false);
        switchSeller.setChecked(false);
    }

    public void login(View v){
        final String name = username.getText().toString().toLowerCase();
        //if username empty, not alphanumerical, or password empty, WILL NOT try to login
        if(name.isEmpty())
            Toast.makeText(getApplicationContext(), "Enter your username.",
                    Toast.LENGTH_SHORT).show();
        else if(!name.matches("[a-zA-Z0-9]+"))
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
                    if (x.checkUsername(name)) {
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
                    newUser = new Seller(name,
                            password.getText().toString(), isSeller);
                    users.add(newUser);
                    newUser.login();
                }
                else {
                    newUser = new Customer(name,
                            password.getText().toString(),isSeller);
                    users.add(newUser);
                    newUser.login();
                }

                //save new users list
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    ObjectOutputStream oos = new ObjectOutputStream(fos);
                    oos.writeObject(users);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
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
            //returning user, login attempt; if username is found, check account type and then check password
            // if password matches, perform customer login or pass switchSeller to next activity and go to appropriate browser
            else {
                for (int x = 0; x < users.size(); x++) {
                    if (users.get(x).checkUsername(name)) {
                        //check if seller, and login check
                        if(users.get(x).isSeller()&&isSeller){
                            if (users.get(x).checkPassword(password.getText().toString())) {
                                matches = true;
                                users.get(x).login();
                                break;
                            }
                        }
                        //login as customer
                        else if(!users.get(x).isSeller()&&!isSeller){
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
