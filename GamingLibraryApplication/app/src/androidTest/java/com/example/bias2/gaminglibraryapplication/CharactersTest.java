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
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by captainawesome on 2017-09-15.
 */

public class CharactersTest {

    private Context context;
    private IGDBWrapper wrapper;


    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getContext();
        wrapper = new IGDBWrapper(context, "c957fd42f03e55db16b0d7ad5c74a2d5", Version.PRO, true);

    }

    @Test
    public void testSingleCharacter() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("8529");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.characters(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject character = result.getJSONObject(0);
                    int charID = character.getInt("id");
                    assertEquals(8529, charID);
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
    public void testMultipleCharacters() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("8530,8531,8533");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.characters(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject character1 = result.getJSONObject(0);
                    JSONObject character2 = result.getJSONObject(1);
                    JSONObject character3 = result.getJSONObject(2);
                    int charID1 = character1.getInt("id");
                    int charID2 = character2.getInt("id");
                    int charID3 = character3.getInt("id");

                    assertEquals(8530, charID1);
                    assertEquals(8531, charID2);
                    assertEquals(8533, charID3);

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
    public void testGameSearchMultiAndSingle() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addSearch("geralt of rivia")
                .addFields("name");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.search(Endpoint.CHARACTERS, parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();

                    JSONObject character = result.getJSONObject(0);

                    assertEquals(1453, character.getInt("id"));
                    assertEquals("Geralt of Rivia", character.getString("name"));

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
