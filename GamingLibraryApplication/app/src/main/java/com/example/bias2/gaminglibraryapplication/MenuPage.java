package com.example.bias2.gaminglibraryapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;
import com.igdb.api_android_java.callback.onSuccessCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import static junit.framework.Assert.fail;

public class MenuPage extends AppCompatActivity  {

    private APIWrapper wrapper;
    private List<String> arrayList =new ArrayList<>();
    private ListView lv;
    private String GamesIDs;
    private String NewGamesIDs[];
    private String NewGamesIDs2 = "";
    private String Gamelist;
    private  ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        lv = (ListView) findViewById(R.id.Listview);
        SetUpButton();

        try {
            Genres();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setUp() {
        wrapper = new APIWrapper(getApplicationContext(), "c957fd42f03e55db16b0d7ad5c74a2d5");
    }

    public void Genres() throws InterruptedException {
        setUp();
        Parameters params = new Parameters()
                .addIds("7");

        wrapper.search(APIWrapper.Endpoint.GENRES, params, new onSuccessCallback(){
            @Override
            public void onSuccess(JSONArray result) {

                try {
                    JSONObject Genre = result.getJSONObject(0);
                    GamesIDs = Genre.getString("games");
                    String regex = "\\[|\\]";
                    GamesIDs = GamesIDs.replaceAll(regex, "");
                    NewGamesIDs = GamesIDs.split(",");


                    for (int i=0; i<50; i++)
                    {
                        NewGamesIDs2 += NewGamesIDs[i] + ",";
                    }

                    NewGamesIDs2 = NewGamesIDs2.substring(0, NewGamesIDs2.length() - 1);

                    Log.d("teest", "result:" + GamesIDs);
                    Log.d("teest2", "result:" + GamesIDs.length());
                    Log.d("teest3", "result:" + NewGamesIDs[0]);
                    Log.d("teest4", "result:" + NewGamesIDs.length);
                    Log.d("teest5", "result:" +  NewGamesIDs2);

                } catch (JSONException e) {
                    e.printStackTrace();
                    fail("JSONExeption!!");
                }
            }
            @Override
            public void onError(VolleyError error) {
                Log.e("tesst", "error: " + error);
            }
        });
    }

    public void Games() throws InterruptedException {
        setUp();
        Parameters params = new Parameters()
                .addIds( NewGamesIDs2)
                .addFields("name,popularity");

        wrapper.games(params, new onSuccessCallback(){
            @Override
            public void onSuccess(JSONArray result) {

                try {
                    arrayList = new ArrayList(result.length());

                    for (int e=0; e<result.length(); e++)
                    {
                        JSONObject game = result.getJSONObject(e);
                        Gamelist = "Navn: " + game.getString("name") + " Release date: " + game.getString("popularity");

                        arrayList.add(Gamelist);

                    }

                    Log.d("test", "result: " + arrayList);
                    Log.d("test2", "result: " + Gamelist);


                } catch (JSONException e) {
                    e.printStackTrace();
                    fail("JSONExeption!!");
                }
            }
            @Override
            public void onError(VolleyError error) {
                Log.e("tesst", "error: " + error);
            }
        });
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);
    }

    void ButtonClicked() throws InterruptedException {

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);
        Games();
        Log.e("test", "hej");
    }

    void SetUpButton() {
        Button testButton = findViewById(R.id.button);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    ButtonClicked();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
