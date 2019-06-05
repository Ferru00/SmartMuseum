package com.example.smart_museum;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ProgressBar pbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pbar = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void printMsg (String nome, boolean esito)
    {
        String title;
        if(esito)
            title = "Accesso effettuato. Ciao: ".concat(nome);
        else
            title = nome;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    public void Login(View view)
    {
        pbar.setVisibility(View.VISIBLE);
        final EditText Email = findViewById(R.id.User_Email);
        final EditText Password = findViewById(R.id.User_Password);

        if (Email.getText().toString().equals("") || Password.getText().toString().equals(""))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Attenzione. Per poter accedere devi compilare i due campi.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.setCancelable(false);
            builder.show();
        }
        else
        {
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = API.getUrl_login();
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            pbar.setVisibility(View.GONE);
                            // response
                            //Log.d("Response", response);
                            try{
                                JSONObject object = new JSONObject(response);
                                Log.d("success",object.getString("success"));
                                if(object.getString("success").equals("ok"))
                                {
                                    API.setNome(object.getString("nome"));
                                    API.setEmail(object.getString("email"));
                                    API.setCookie(object.getString("cookie"));
                                    SaveSharedPreference.setUserCookie(getApplicationContext(),object.getString("cookie") );
                                    Intent home = new Intent(getApplicationContext(), Home.class);
                                    startActivity(home);
                                    finish();
                                }
                                else printMsg("Credenziali non valide.",false);
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            pbar.setVisibility(View.GONE);
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams()
                {
                    Map<String, String>  params = new HashMap<>();
                    params.put("Email", Email.getText().toString());
                    params.put("Password", Password.getText().toString());

                    return params;
                }
            };
            queue.add(postRequest);
        }

    }

    public void Registrati(View v)
    {
        Intent Registrazione = new Intent(this, Registrazione.class);
        startActivity(Registrazione);
    }



}
