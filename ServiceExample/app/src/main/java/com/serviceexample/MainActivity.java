package com.serviceexample;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button myButton1, myButton2, myButton3;
    BoundedService.MyBinder binder_;
    BoundedService myService;
    Boolean connected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        myButton2 = (Button) findViewById(R.id.buttonSS);
        myButton1.setOnClickListener(this);
        myButton2 = (Button) findViewById(R.id.buttonBoundedS);
        myButton2.setOnClickListener(this);
        myButton3 = (Button) findViewById(R.id.buttonBoundedS);
        myButton3.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick(View v){

        switch(v.getId()){
            case R.id.buttonSS:
                Intent  myIntent = new Intent(this, StartedIntentService.class);
                startService(myIntent);
                break;
            case R.id.buttonBoundedS:
                Intent myIntent2 = new Intent(this, BoundedService.class);
                bindService(myIntent2, mConnection, BIND_AUTO_CREATE);
                break;
            case R.id.buttonComm:
                if (connected == true) {
                    String msg__= myService.msg();
                }
                break;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            binder_ = (BoundedService.MyBinder) service;
            myService = binder_.getService();
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

}
