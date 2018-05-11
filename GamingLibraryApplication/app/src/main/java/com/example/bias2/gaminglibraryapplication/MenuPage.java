package com.example.bias2.gaminglibraryapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.bias2.gaminglibraryapplication.wrapper.IGDBWrapper;
import com.example.bias2.gaminglibraryapplication.callback.OnSuccessCallback;
import com.example.bias2.gaminglibraryapplication.wrapper.Endpoint;
import com.example.bias2.gaminglibraryapplication.wrapper.Parameters;
import com.example.bias2.gaminglibraryapplication.wrapper.Version;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import android.text.TextUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class MenuPage extends AppCompatActivity {

    private Context context;
    private IGDBWrapper wrapper;
    String name;
    TextView hej;
    int i = 0;
    ArrayList list = new ArrayList();
    private ListView lv;
    String joined;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        hej = (TextView) findViewById(R.id.textView);
        lv = (ListView) findViewById(R.id.ListView2);
    }

    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getContext();
        wrapper = new IGDBWrapper(context, "c957fd42f03e55db16b0d7ad5c74a2d5", Version.PRO, true);

    }

    public void testSingleGames() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addSearch("Atari")
                .addFields("name")
                .addLimit("2");

        wrapper.search(Endpoint.PLATFORMS, parameters, new OnSuccessCallback(){
            @Override

            public void onSuccess(JSONArray result) {
                try {
                    JSONArray NewResult = new JSONArray(result);
                    for (int i = 0, count = NewResult.length(); i< count; i++)
                    {
                        list.add(NewResult.get(i).toString());
                    }
                    joined = TextUtils.join(", ", list);
                   // ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this ,android.R.layout.simple_list_item_1, list);

                   // lv.setAdapter(arrayAdapter);
                   // int testId = object.getInt("id");
                   // name = object.getString("id");
                    //hej.setText("name: "+list);
                } catch (JSONException e) {
                    e.printStackTrace();
                    fail("JSONException!!");
                }
            }

            @Override
            public void onError(VolleyError error) {
                fail("Volley Error!!");
            }
        });

    }

    public void goButtonClicked(View v) throws InterruptedException {
        tToast("Go button clicked!");
        //testSingleGames();
        Log.d("myTag", joined);
    }

    private void tToast(String s) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, s, duration);
        toast.show();
    }

}