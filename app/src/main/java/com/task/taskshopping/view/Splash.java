package com.task.taskshopping.view;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;

import com.task.taskshopping.R;
import com.task.taskshopping.item.ProductRoomDatabase;
import com.task.taskshopping.item.Productdb;
import com.task.taskshopping.viewmodel.ProductViewModel;

/**
 * Created by KATHIR on 07-06-2020
 */
public class Splash extends AppCompatActivity {
    String app_ver;
    TextView v;
    private long ms = 0;
    private long splashDuration = 3000;
    private boolean splashActive = true;
    private boolean paused = false;

    private ProductViewModel productViewModel;

    private static ProductRoomDatabase mItemDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);


        Thread mythread = new Thread() {

            public void run() {

                try {

                    while (splashActive && ms < splashDuration) {

                        if (!paused)

                            ms = ms + 100;

                        sleep(100);
                        productViewModel.deleteAll();
                        Productdb note = new Productdb("Biriyani", "Checken", "₹ 100.98", 0);
                        Productdb note1 = new Productdb("Biriyani", "Checken", "₹ 100.98", 0);
                        Productdb note2 = new Productdb("Biriyani", "Checken", "₹ 100.98", 0);
                        Productdb note3 = new Productdb("Biriyani", "Checken", "₹ 100.98", 0);
                        Productdb note4 = new Productdb("Biriyani", "Checken", "₹ 100.98", 0);
                        Productdb note5 = new Productdb("Biriyani", "Checken", "106.87", 0);
                        productViewModel.insert(note);
                        productViewModel.insert(note1);
                        productViewModel.insert(note2);
                        productViewModel.insert(note3);
                        productViewModel.insert(note4);
                        productViewModel.insert(note5);
                        mItemDB = Room.databaseBuilder(getApplicationContext(), ProductRoomDatabase.class, "sample").build();
                    }

                } catch (Exception e) {

                } finally {

                    Intent intent = new Intent(Splash.this, MainActivity.class);

                    startActivity(intent);
                    finish();


                }

            }

        };

        mythread.start();

    }

}