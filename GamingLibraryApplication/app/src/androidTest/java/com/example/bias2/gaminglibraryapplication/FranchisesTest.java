package com.example.bias2.gaminglibraryapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.android.volley.VolleyError;
import com.example.bias2.gaminglibraryapplication.callback.OnSuccessCallback;
import com.example.bias2.gaminglibraryapplication.wrapper.IGDBWrapper;
import com.example.bias2.gaminglibraryapplication.wrapper.Parameters;
import com.example.bias2.gaminglibraryapplication.wrapper.Version;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * Created by captainawesome on 2017-09-15.
 */

public class FranchisesTest {

    private Context context;
    private IGDBWrapper wrapper;


    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getContext();
        String key = System.getProperty("API_KEY");
        wrapper = new IGDBWrapper(context, key, Version.PRO, true);

    }

    @Test
    public void testSingleFranchises() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("1040");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.franchises(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject credits = result.getJSONObject(0);
                    int testId = credits.getInt("id");
                    assertEquals(1040, testId);
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
        lock.await(20000, TimeUnit.MILLISECONDS);

    }

    @Test
    public void testMultipleFranchises() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("1043,1,2");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.franchises(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject credit1 = result.getJSONObject(0);
                    JSONObject credit2 = result.getJSONObject(1);
                    JSONObject credit3 = result.getJSONObject(2);

                    assertEquals(1043, credit1.getInt("id"));
                    assertEquals(1, credit2.getInt("id"));
                    assertEquals(2, credit3.getInt("id"));

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
        lock.await(20000, TimeUnit.MILLISECONDS);

    }
}
