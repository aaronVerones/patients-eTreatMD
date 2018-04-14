package com.example.aaronverones.patients;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RequestQueue mQueue;
    private static final String TAG = "mainActivityMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        String url = "https://api.myjson.com/bins/uj26j";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.i(TAG, "Successfully retrieved data from api. Setting up application with remote json data");
                        setUpView(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "Received error from api. Setting up application with local json data");
                JSONArray jsonArray = getJSonData("patients.json");
                setUpView(jsonArray);
            }
        });
        mQueue.add(request);
    }
    private void setUpView(JSONArray jsonArray) {
        ArrayList<JSONObject> listItems = getArrayListFromJSONArray(jsonArray);
        ListView listV = (ListView)findViewById(R.id.patientsList);
        ListAdapter adapter = new PatientsListAdapter(this, R.layout.list_layout, R.id.pid, listItems);
        listV.setAdapter(adapter);
    }
    private JSONArray getJSonData(String fileName){
        JSONArray jsonArray = null;
        try {
            InputStream is = getResources().getAssets().open(fileName);
            int size = is.available();
            byte[] data = new byte[size];
            is.read(data);
            is.close();
            String json = new String(data, "UTF-8");
            jsonArray = new JSONArray(json);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return jsonArray;
    }
    private ArrayList<JSONObject> getArrayListFromJSONArray(JSONArray jsonArray){
        ArrayList<JSONObject> aList = new ArrayList<JSONObject>();
        try {
            if (jsonArray != null) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    aList.add(jsonArray.getJSONObject(i));
                }
            }
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return  aList;
    }
}
