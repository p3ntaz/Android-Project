package com.example.bias2.gaminglibraryapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class StartPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    void TestPicasso() {
        //ImageView imageToChange = (ImageView) findViewById(R.id.imageView);
        String url = "https://i.ytimg.com/vi/UEYp1RTv";
        //Picasso.get().load(url).into(imageToChange);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
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