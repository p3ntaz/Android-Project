package com.example.bias2.gaminglibraryapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bias2.gaminglibraryapplication.wrapper.IGDBWrapper;

public class MenuPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
    }

    APIWrapper wrapper = new APIWrapper(context, "YOUR_API_KEY");
    Parameters params = new Parameters()
            .addFields("*")
            .addorder("published_at:desc");

wrapper.games(params, new onSuccessCallback(){
        @Override
        public void onSuccess(JSONArray result) {
            // Do something with resulting JSONArray
        }

        @Override
        public void onError(VolleyError error) {
            // Do something on error
        }
    });

}