package com.example.bias2.gaminglibraryapplication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.Image;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.AsyncTask;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;


public class StartPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    void Picasso() {
        android.widget.ImageView[] imageToChange = new android.widget.ImageView[8];
        imageToChange[0] = findViewById(R.id.image1);
        imageToChange[1] = findViewById(R.id.image2);
        imageToChange[2] = findViewById(R.id.image3);
        imageToChange[3] = findViewById(R.id.image4);
        imageToChange[4] = findViewById(R.id.image5);
        imageToChange[5] = findViewById(R.id.image6);
        imageToChange[6] = findViewById(R.id.image7);
        imageToChange[7] = findViewById(R.id.image8);
        String url = "https://i.ytimg.com/vi/UEYp1RTv";
        CreateListOfImages(imageToChange);
    }

    void CreateListOfImages(ImageView[] imagesToChange) {
        try {
            Resources res = getResources();
            AssetManager am = res.getAssets();
            String[] imageList = am.list("images");
            List<Integer> usedImages = null;
            for (int i = 0; i < imagesToChange.length; i++) {
                Random rnd = new Random();
                int n = rnd.nextInt(imageList.length);
                if (usedImages != null) {
                    for (int j = 0; j < usedImages.size(); j++) {
                        if (usedImages.get(j) == n) {
                            while (usedImages.get(j) == n) {
                                n = rnd.nextInt(imageList.length);
                            }
                            Picasso.with(this).load(imageList[n]).placeholder(R.drawable.android_died).resize(80,80).into(imagesToChange[i]);
                        }
                    }
                    usedImages.add(n);
                } else {
                    String s = "res/images/" + imageList[n];
                    File f = new File(s);
                    Picasso.with(this).load(imageList[n]).placeholder(R.drawable.android_died).resize(80,80).into(imagesToChange[i]);
                }
            }
            ImageView mainImage = (ImageView) findViewById(R.id.mainImage);
            Picasso.with(this).load(imageList[0]).placeholder(R.drawable.android_died).resize(250,150).into(mainImage);
        } catch (IOException e) {
            Log.d("IOExcep", e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        TextView text = (TextView) findViewById(R.id.welcomeText);
        text.setText("This is the prototype on a gaming library for Android users, which was created during a course on Android Programming on the University of Southern Denmark, spring 2018.");
        Layout();
        Picasso();
    }

    public void Layout() {
        Spinner dropDownMenu = findViewById(R.id.menuSpinner);
        String[] items = new String[]{"Category: Home Page", "Home Page", "Genres"};
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}