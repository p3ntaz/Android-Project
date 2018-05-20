package com.example.bias2.gaminglibraryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.igdb.api_android_java.model.APIWrapper;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.fail;

public class Genres extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemSelectedListener {

    private boolean fragmentShown = false;
    private ViewPager mViewPager;
    private APIWrapper wrapper;
    private List<String> arrayList =new ArrayList<>();
    private ListView lv;
    private String GamesIDs;
    private String NewGamesIDs[];
    private String NewGamesIDs2 = "";
    private String Gamelist;
    private ArrayAdapter<String> adapter;
    private DrawerLayout mDrawer;
    private SectionsStatePagerAdapter mSectionsStatePagerAdapter;

    public void Layout() {
        Spinner dropDownMenu = findViewById(R.id.menuSpinner);
        String[] items = new String[]{"Category: Genres", "Home Page", "Genres"};
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genres);
        CreateGenreMenu();
        Setup();
    }

    void Setup() {
        mSectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        //SetupViewPager();
    }

    void SetupViewPager() {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new RTSList(), "RTS Fragment");
        adapter.AddFragment(new FPSList(), "FPS Fragment");
        adapter.AddFragment(new RPGList(), "RPG Fragment");
        mViewPager.setAdapter(adapter);
    }

    void SetViewPager(int fragmentNumber) {
        mViewPager.setCurrentItem(fragmentNumber);
    }

    void CreateGenreMenu() {
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.appbar_scrolling_view_behavior, R.string.appbar_scrolling_view_behavior);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        switch (item.getItemId()) {
            case R.id.nav_RTS:
                Toast.makeText(this, "Going to RTS Fragment", Toast.LENGTH_SHORT).show();
                adapter.AddFragment(new RTSList(), "RTS Fragment");
                mViewPager.setAdapter(adapter);
                this.SetViewPager(0);
                break;
            case R.id.nav_FPS:
                Toast.makeText(this, "Going to FPS Fragment", Toast.LENGTH_SHORT).show();
                adapter.AddFragment(new FPSList(), "FPS Fragment");
                mViewPager.setAdapter(adapter);
                this.SetViewPager(1);
                break;
            case R.id.nav_RPG:
                Toast.makeText(this, "Going to RPG Fragment", Toast.LENGTH_SHORT).show();
                adapter.AddFragment(new RPGList(), "RPG Fragment");
                mViewPager.setAdapter(adapter);
                this.SetViewPager(2);
                break;
            case R.id.nav_Survival:
                Toast.makeText(this, "Going to Survival Fragment", Toast.LENGTH_SHORT).show();
                adapter.AddFragment(new SurvivalList(), "Survival Fragment");
                mViewPager.setAdapter(adapter);
                this.SetViewPager(3);
                break;
        }
        return true;
    }
}
