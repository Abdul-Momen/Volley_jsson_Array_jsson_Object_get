package com.example.jsonarrayrequestvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    private Button btnMakeObjectRequest, btnMakeArrayRequest;
    private TextView txtResponse;
    private TextView txtResponse2;
    private TextView txtResponse3;
    private TextView txtResponse4;
    private TextView txtResponse5;


    String storeData;
    String storeData2;
    // Progress dialog
    private ProgressDialog pDialog;


    // json object response url
    private String urlJsonObj = "https://api.androidhive.info/volley/person_object.json";

    // json array response url
    private String urlJsonArry = "https://api.androidhive.info/volley/person_array.json";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMakeObjectRequest =  findViewById(R.id.btnObjRequest);
        btnMakeArrayRequest =  findViewById(R.id.btnArrayRequest);
        txtResponse =  findViewById(R.id.txtResponse5);
        txtResponse2 =  findViewById(R.id.txtResponse2);
        txtResponse3 =  findViewById(R.id.txtResponse3);
        txtResponse4 =  findViewById(R.id.txtResponse4);
        txtResponse5 =  findViewById(R.id.txtResponse5);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json object request
                makeJsonObjectRequest();
            }
        });

        btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeJsonArrayRequest();
            }
        });
    }



/////////////json array///////////
    private void makeJsonArrayRequest() {
        showpDialog();

        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            // Parsing json array response
                            // loop through each json object
                            storeData2 = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject person = (JSONObject) response
                                        .get(i);

                                String name = person.getString("name");
                                String email = person.getString("email");
                                JSONObject phone = person.getJSONObject("phone");
                                String home = phone.getString("home");
                                String mobile = phone.getString("mobile");

                                storeData2 += "Name: " + name + "\n\n";
                                storeData2 += "Email: " + email + "\n\n";
                                storeData2 += "Home: " + home + "\n\n";
                                storeData2 += "Mobile: " + mobile + "\n\n\n";

                            }

                            txtResponse2.setText(storeData2);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(MainActivity.this).add(req);

    }







    ///////json object/////////
    private void makeJsonObjectRequest() {

        showpDialog();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    // Parsing json object response
                    // response will be a json object
                    String name = response.getString("name");
                    String email = response.getString("email");
                    JSONObject phone = response.getJSONObject("phone");
                    String home = phone.getString("home");
                    String mobile = phone.getString("mobile");

                    storeData = "";
                    storeData += "Name: " + name + "\n\n";
                    storeData += "Email: " + email + "\n\n";
                    storeData += "Home: " + home + "\n\n";
                    storeData += "Mobile: " + mobile + "\n\n";

                    txtResponse.setText(storeData);



                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        Volley.newRequestQueue(MainActivity.this).add(jsonObjReq);
    }
        




//////progress bar
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
