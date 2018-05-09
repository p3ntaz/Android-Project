package com.example.bias2.gaminglibraryapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.bias2.gaminglibraryapplication.wrapper.IGDBWrapper;
/* import com.example.bias2.gaminglibraryapplication.callback.OnSuccessCallback;
import com.example.bias2.gaminglibraryapplication.wrapper.Endpoint;
import com.example.bias2.gaminglibraryapplication.wrapper.Parameters;
import com.example.bias2.gaminglibraryapplication.wrapper.Version;*/

public class MenuPage extends AppCompatActivity {

    private Context context;
    private IGDBWrapper wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
    }




}