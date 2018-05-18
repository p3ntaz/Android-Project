package com.example.bias2.gaminglibraryapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;

public class Genres extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private boolean fragmentShown = false;

    void SetUp() {
        final FrameLayout[] genreButtons = new FrameLayout[4];
        genreButtons[0] = (FrameLayout) findViewById(R.id.genreButton1);
        genreButtons[1] = (FrameLayout) findViewById(R.id.genreButton2);
        genreButtons[2] = (FrameLayout) findViewById(R.id.genreButton3);
        genreButtons[3] = (FrameLayout) findViewById(R.id.genreButton4);

        for (int i = 0; i < genreButtons.length; i++) {
            final int n = i;
            genreButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoadActivity(n);
                }
            });
        }
    }

    void LoadActivity(int number) {
        switch (number) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                Intent fpsList = new Intent(this, FPSList.class);
                startActivity(fpsList);
                break;
            case 3:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);
        SetUp();
        Layout();
    }

    public void Layout() {
        Spinner dropDownMenu = findViewById(R.id.menuSpinner);
        String[] items = new String[]{"Category: Genres", "Home Page", "Genres", "About"};
        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        dropDownMenu.setAdapter(adapter);
        dropDownMenu.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                Log.d("myTag", "Switch to Home Page.");
                Intent homePage = new Intent(this, StartPage.class);
                startActivity(homePage);
                break;
            case 2:
                Log.d("myTag", "Switch to Genres.");
                Intent genres = new Intent(this, Genres.class);
                startActivity(genres);
                break;
            case 3:
                Log.d("myTag", "Switch to About.");
                Intent about = new Intent(this, About.class);
                startActivity(about);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
