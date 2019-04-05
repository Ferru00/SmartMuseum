package com.example.smart_museum;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Registrazione extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrazione);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void printOK ()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Registrazione effettuata!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int id) {
            Intent main = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(main);
            finish();
        }
         });
        builder.setCancelable(false);
        builder.show();
    }

    public void Registrazione (View view)
    {
        final EditText Nome = findViewById(R.id.Nome);
        final EditText Cognome = findViewById(R.id.Cognome);
        final EditText Email = findViewById(R.id.Email);
        final EditText Password = findViewById(R.id.Password);
        final EditText Conf_Password = findViewById(R.id.Conferma_password);


        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String passwordPattern =  "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";

        if (Nome.getText().toString().equals("") || Cognome.getText().toString().equals("") || Email.getText().toString().equals("") || Password.getText().toString().equals("") || Conf_Password.getText().toString().equals(""))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Attenzione. Compila tutti i campi.");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            builder.setCancelable(false);
            builder.show();

            TextView errore = findViewById(R.id.Errori);
            errore.setVisibility(View.VISIBLE);
            errore.setText("");
            errore.append("Compila tutti i campi.");

        }
        else
        {
            if (Email.getText().toString().trim().matches(emailPattern))
            {
                if (Password.getText().toString().trim().matches(passwordPattern))
                {
                    if (Password.getText().toString().equals(Conf_Password.getText().toString()))
                    {
                        RequestQueue queue = Volley.newRequestQueue(this);

                        String url = API.getUrl_signup();
                        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>()
                                {
                                    @Override
                                    public void onResponse(String response) {
                                        // response
                                        Log.d("Response", response);
                                        if(response.equals("Ok")){
                                            printOK();
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
                                params.put("Nome", Nome.getText().toString());
                                params.put("Cognome", Cognome.getText().toString());
                                params.put("Email", Email.getText().toString());
                                params.put("Password", Password.getText().toString());

                                return params;
                            }
                        };
                        queue.add(postRequest);
                    }
                    //Messaggio errore corrispondenza password
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Attenzione. Le password non corrispondono");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });
                        builder.setCancelable(false);
                        builder.show();

                        TextView errore = findViewById(R.id.Errori);
                        errore.setVisibility(View.VISIBLE);
                        errore.setText("");
                        errore.append("Le password non corrispondono.");
                    }
                }
                //Messaggio errore formato Password
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Attenzione. Il formato della password è errato.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    builder.setCancelable(false);
                    builder.show();

                    TextView errore = findViewById(R.id.Errori);
                    errore.setVisibility(View.VISIBLE);
                    errore.setText("");
                    errore.append("Password errata.");
                }
            }
            //Messaggio errore formato Email
            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Attenzione. Il formato dell'email è errato.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
                builder.setCancelable(false);
                builder.show();

                TextView errore = findViewById(R.id.Errori);
                errore.setVisibility(View.VISIBLE);
                errore.setText("");
                errore.append("Email errata.");
            }

        }

    }
}
