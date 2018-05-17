package com.example.gaminglibraryapplication;

import android.support.test.InstrumentationRegistry;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.igdb.api_android_java.model.APIWrapper;
import com.igdb.api_android_java.model.Parameters;
import com.igdb.api_android_java.callback.onSuccessCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import static junit.framework.Assert.fail;

public class MenuPage extends AppCompatActivity {

    private APIWrapper wrapper;
    private TextView newTextView;
    private String Gameinfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        SetUpButton();
        newTextView = (TextView) findViewById(R.id.textView2);
        newTextView.setText(R.string.my_love_text);
    }

    public void setUp() {
        wrapper = new APIWrapper(getApplicationContext(), "c957fd42f03e55db16b0d7ad5c74a2d5");
    }

    public void testSingleGames() throws InterruptedException {
        setUp();
        Parameters params = new Parameters()
                .addIds("1942")
                .addFields("screenshots");

        wrapper.games(params, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                // Do something with resulting JSONArray
                Log.d("tesst", "result: " + result.toString());
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tesst", "error: " + error);
            }
        });
    }

    public void testSingleGames2() throws InterruptedException {
        setUp();
        Parameters params = new Parameters()
                .addSearch("Atari")
                .addFields("name")
                .addLimit("2");

        wrapper.games(params, new onSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                // Do something with resulting JSONArray
                Log.d("tesst", "result: " + result.toString());
                Gameinfo = result.toString();
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tesst", "error: " + error);
            }
        });
    }

    void ButtonClicked() throws InterruptedException {
        testSingleGames();
        testSingleGames2();
        newTextView.setText(Gameinfo);
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
