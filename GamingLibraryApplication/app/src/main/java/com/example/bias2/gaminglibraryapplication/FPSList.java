package com.example.bias2.gaminglibraryapplication;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class FPSList extends Fragment {

    private APIWrapper wrapper;
    private ArrayList<String> arrayList =new ArrayList<>();
    private ListView lv;
    private String GamesIDs;
    private String NewGamesIDs[];
    private String NewGamesIDs2 = "";
    MyTask NewMyTask = new MyTask();
    private String Gamelist;
    private  ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_rtslist, container, false);
        lv = (ListView) v.findViewById(R.id.ListviewRTSList);
        SetUpButton(v);
        NewMyTask.execute();

        return v;
    }

    public void setUp() {
        wrapper = new APIWrapper(getActivity().getApplicationContext(), "c957fd42f03e55db16b0d7ad5c74a2d5");
    }

    public void Genres() throws InterruptedException

    {
        setUp();
        Parameters params = new Parameters()
                .addIds("5");

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
                        Gamelist = "Navn: " + game.getString("name") + " popularity: " + game.getString("popularity");

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
    }

    void ButtonClicked() throws InterruptedException {

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);
        Games();
        Log.e("test", "hej");
    }

    void SetUpButton(View v) {
        Button testButton = getActivity().findViewById(R.id.fragmentButton);
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
    private class MyTask extends AsyncTask<Void, Void, Void> {
        ArrayList<String> current = new ArrayList<String>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Void doInBackground(Void... params) {
            try {
                Genres();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Games();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            current = arrayList;
            Log.d("teesst1","loading");
            Log.d("teesst2",current.toString());
            Log.d("teesst3",arrayList.toString());
            return null;
        }

        //@Override
        protected void onPostExecute(Void result) {
            //super.onPostExecute(result);
            ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, current);
            lv.setAdapter(adapter);
            Log.d("teesst4", "done");
        }
    }
}
