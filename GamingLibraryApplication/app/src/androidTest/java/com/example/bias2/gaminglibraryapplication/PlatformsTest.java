package com.example.bias2.gaminglibraryapplication;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.android.volley.VolleyError;
import com.example.bias2.gaminglibraryapplication.callback.OnSuccessCallback;
import com.example.bias2.gaminglibraryapplication.wrapper.Endpoint;
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

public class PlatformsTest {

    private Context context;
    private IGDBWrapper wrapper;


    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getContext();
        String key = System.getProperty("API_KEY");
        wrapper = new IGDBWrapper(context, key, Version.PRO, true);

    }

    @Test
    public void testSinglePlatforms() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("11");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.platforms(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject object = result.getJSONObject(0);
                    int testId = object.getInt("id");
                    assertEquals(11, testId);
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
    public void testMultiplePeople() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("13,5,3");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.platforms(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject object1 = result.getJSONObject(0);
                    JSONObject object2 = result.getJSONObject(1);
                    JSONObject object3 = result.getJSONObject(2);

                    assertEquals(13, object1.getInt("id"));
                    assertEquals(5, object2.getInt("id"));
                    assertEquals(3, object3.getInt("id"));

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
    public void testPeopleMultiAndSingle() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addSearch("xbox one")
                .addFields("name");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.search(Endpoint.PLATFORMS, parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject object1 = result.getJSONObject(0);

                    assertEquals(49, object1.getInt("id"));
                    assertEquals("Xbox One", object1.getString("name"));

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
