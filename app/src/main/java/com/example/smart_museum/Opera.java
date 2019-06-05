package com.example.smart_museum;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Opera extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opera);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*String result= getIntent().getStringExtra("result");
        TextView text = findViewById(R.id.result);
        text.setText(result);*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);

        TextView info = header.findViewById(R.id.Opera_nome);
        info.setText("Ciao ".concat(API.getNome()));

        info = header.findViewById(R.id.Opera_email);
        info.setText(API.getEmail());

        Button btn = (Button) findViewById(R.id.saveImage);
        btn.setBackgroundResource(R.drawable.heart);


        if(Integer.parseInt(getIntent().getStringExtra("result")) >= 0)
        {
            fetchImage();
            getInfoOpera();
        }
        else if (Integer.parseInt(getIntent().getStringExtra("posizioneLista")) >= 0)
        {
            SavedOpera tmp = Home.serialize.getItemPosition(Integer.parseInt(getIntent().getStringExtra("posizioneLista")));

            TextView txt = (TextView) findViewById(R.id.Titolo);
            txt.setText(tmp.getNome());

            txt = (TextView) findViewById(R.id.Immagine_descrzione);
            txt.setText(tmp.getDescrizione());

            ImageView imageView = (ImageView) findViewById(R.id.Immagine_opera);
            imageView.setImageBitmap(tmp.getImage());
        }
    }

    Bitmap Imageresponse;
    public void SaveOpera (View view)
    {
        TextView txt = (TextView) findViewById (R.id.Titolo);
        String titolo = txt.getText().toString();

        txt = (TextView) findViewById (R.id.Immagine_descrzione);
        String descr = txt.getText().toString();

        SavedOpera tmp = new SavedOpera(titolo, descr, Imageresponse);


        Home.serialize.SaveOpera(tmp, getApplicationContext());
        Toast.makeText(Opera.this,"Opera Salvata ", Toast.LENGTH_SHORT).show();
    }

    public void getInfoOpera ()
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = API.getUrl_InfoOpera();

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        try{
                            JSONObject object = new JSONObject(response);
                            Log.d("successo",object.getString("success"));
                            if(object.getString("success").equals("ok"))
                            {
                                TextView txt = (TextView) findViewById(R.id.Titolo);
                                txt.setText(object.getString("nome"));

                                txt = (TextView) findViewById(R.id.Immagine_descrzione);
                                txt.setText(object.getString("descrizione"));
                            }
                            else
                            {
                                Toast.makeText(Opera.this,"Opera inesistente", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Opera.this,"Opera inesistente", Toast.LENGTH_SHORT).show();
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
        )

        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("idopera", getIntent().getStringExtra("result"));
                params.put("idmuseo", API.getMuseoSelez());
                return params;
            }
        };
        queue.add(postRequest);
    }
    public void fetchImage ()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final ImageView imageView = (ImageView) findViewById(R.id.Immagine_opera);

        String result= getIntent().getStringExtra("result");
        String url_image = API.getUrl_immagini()+API.getMuseoSelez()+"/"+result + ".jpg";
        Log.d("resultimage", url_image);
        Log.d("Resetra", result);

        ImageRequest ir = new ImageRequest(url_image,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        imageView.setImageBitmap(response);
                        Imageresponse = response;
                    }
                }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("ImageError", "Image Load Error: ");
            }
        });

        requestQueue.add(ir);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.opera, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
