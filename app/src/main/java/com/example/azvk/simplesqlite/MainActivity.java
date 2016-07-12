package com.example.azvk.simplesqlite;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputFragment inputFragment = new InputFragment();
        OutputFragment outputFragment = new OutputFragment();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.inputFrame, inputFragment);
        fragmentTransaction.replace(R.id.outputFrame, outputFragment);
        fragmentTransaction.commit();
    }
}
