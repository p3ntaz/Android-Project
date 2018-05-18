package com.example.bias2.gaminglibraryapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FPSList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fpslist);
        Setup();
    }

    void Setup() {
        Fragment frag = new TextFragment();
        FragmentManager fragMng = getSupportFragmentManager();
        fragMng.beginTransaction().replace(R.id.textFragment, frag).addToBackStack(null).commit();
    }
}
