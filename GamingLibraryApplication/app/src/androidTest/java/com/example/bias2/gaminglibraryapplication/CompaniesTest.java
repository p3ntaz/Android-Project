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
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * Created by captainawesome on 2017-09-15.
 */

public class CompaniesTest {

    private Context context;
    private IGDBWrapper wrapper;


    public void setUp() throws InterruptedException {
        context = InstrumentationRegistry.getContext();
        String key = System.getProperty("API_KEY");
        wrapper = new IGDBWrapper(context, key, Version.PRO, true);

    }

    @Test
    public void testSingleCompany() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("2238");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.companies(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject companies = result.getJSONObject(0);
                    int testId = companies.getInt("id");
                    assertEquals(2238, testId);
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
    public void testMultipleCompany() throws InterruptedException {
        setUp();
        Parameters parameters = new Parameters()
                .addIds("2239,2240,2242");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.characters(parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();
                    JSONObject company1 = result.getJSONObject(0);
                    JSONObject company2 = result.getJSONObject(1);
                    JSONObject company3 = result.getJSONObject(2);

                    assertEquals(2239, company1.getInt("id"));
                    assertEquals(2240, company2.getInt("id"));
                    assertEquals(2242, company3.getInt("id"));

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
                .addSearch("ea")
                .addFields("name");

        final CountDownLatch lock = new CountDownLatch(1);
        wrapper.search(Endpoint.COMPANIES, parameters, new OnSuccessCallback() {
            @Override
            public void onSuccess(JSONArray result) {
                try {
                    lock.countDown();

                    JSONObject company = result.getJSONObject(0);

                    assertEquals(3673, company.getInt("id"));
                    assertTrue(company.getString("name").contains("EA"));

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
