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

public class CollectionsTest {

    private Context context;
    private IGDBWrapper wrapper;


    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getContext();
        wrapper = new IGDBWrapper(context, "c957fd42f03e55db16b0d7ad5c74a2d5", Version.PRO, true);

    }

    @Test
    public void testSingleCollection() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("1194");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.collections(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject collection = result.getJSONObject(0);
                    int testId = collection.getInt("id");
                    assertEquals(1194, testId);
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
    public void textMultipleCollection() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("1198,1199,1200");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.characters(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject collection1 = result.getJSONObject(0);
                    JSONObject collection2 = result.getJSONObject(1);
                    JSONObject collection3 = result.getJSONObject(2);
                    int testId1 = collection1.getInt("id");
                    int testId2 = collection2.getInt("id");
                    int testId3 = collection3.getInt("id");

                    assertEquals(1198, testId1);
                    assertEquals(1199, testId2);
                    assertEquals(1200, testId3);

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
