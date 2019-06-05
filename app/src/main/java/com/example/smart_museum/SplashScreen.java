package com.example.smart_museum;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                if(!SaveSharedPreference.getUserCookie(getApplicationContext()).equals("NaV")){
                    checkLogin();
                }else {
                    Intent main = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(main);
                    finish();
                }
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


    public void checkLogin ()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = API.getUrl_checkLogin();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Log.d("RES/",response);
                        Log.d("api/", SaveSharedPreference.getUserCookie(getApplicationContext()));
                        try{
                            JSONObject object = new JSONObject(response);
                            Log.d("success",object.getString("success"));
                            if(object.getString("success").equals("ok"))
                            {
                                API.setNome(object.getString("nome"));
                                API.setEmail(object.getString("email"));
                                Intent home = new Intent(getApplicationContext(), Home.class);
                                startActivity(home);
                                finish();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(main);
                            finish();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("sid", SaveSharedPreference.getUserCookie(getApplicationContext()));

                return params;
            }
        };
        queue.add(postRequest);
    }
}
