package com.example.smart_museum;

import android.content.Intent;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TextView museo;

    private ListView list;
    private ArrayList<Musei> arraylist = new ArrayList<>();
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);

        TextView info = header.findViewById(R.id.Home_nome);
        info.setText("Ciao ".concat(API.getNome()));

        info = header.findViewById(R.id.Home_email);
        info.setText(API.getEmail());

        museo = findViewById(R.id.Titolo_museo);

        GenerazioneLista();
    }

    public void PopolateLista (final ArrayList<Musei> arraylist)
    {
        adapter = new Adapter(this, arraylist);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Object listItem = list.getItemAtPosition(position);
                Toast.makeText(Home.this,"ID: "+arraylist.get(position).getId()+"Cliccato a:"+ arraylist.get(position).getNome()
                        , Toast.LENGTH_SHORT).show();

                list.setVisibility(View.GONE);
                TextView msg = findViewById(R.id.startmsg);
                msg.setVisibility(View.GONE);

                msg = findViewById(R.id.Titolo_museo);
                msg.setText(arraylist.get(position).getNome());
                msg.setVisibility(View.VISIBLE);

                ImageView image = findViewById(R.id.image_museo);
                image.setVisibility(View.VISIBLE);

                msg = findViewById(R.id.IndirizzoMsg);
                msg.setVisibility(View.VISIBLE);

                msg = findViewById(R.id.Indirizzo);
                msg.setText(arraylist.get(position).getIndirizzo());
                msg.setVisibility(View.VISIBLE);

                msg = findViewById(R.id.DescrizioneMsg);
                msg.setVisibility(View.VISIBLE);

                msg = findViewById(R.id.Descrizione);
                msg.setText(arraylist.get(position).getDescrizione());
                msg.setVisibility(View.VISIBLE);
            }
        });
    }

    public void GenerazioneLista()
    {
        list = (ListView) findViewById(R.id.Listview);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = API.getUrl_lista_musei();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RISPOSTA",response);
                        try{
                            JSONObject object = new JSONObject(response);
                            Log.d("successo",object.getString("success"));
                            if(object.getString("success").equals("ok"))
                            {
                                Musei temp;
                                for (int i = 0; i < 9; i++)
                                {
                                    temp = new Musei(object.getString("museo"+i),object.getString("indirizzo"+i),object.getString("id"+i), object.getString("descrizione"+i));
                                    arraylist.add(temp);
                                }
                                PopolateLista(arraylist);
                            }
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
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
        };
        queue.add(postRequest);

        /*arraylist.add(new Musei("aaaaaaaaaaaaaaaaaaa","email-1"));
        arraylist.add(new Musei("bbbbbbbbbbbbbbbbbbb","email-2"));
        arraylist.add(new Musei("ccccccccccccccccc","email-3"));
        adapter = new Adapter(this, arraylist);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Object listItem = list.getItemAtPosition(position);
                Toast.makeText(Home.this,"Cliccato a:"+ arraylist.get(position).nome
                        , Toast.LENGTH_SHORT).show();
            }
        });*/
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
        getMenuInflater().inflate(R.menu.home, menu);
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
