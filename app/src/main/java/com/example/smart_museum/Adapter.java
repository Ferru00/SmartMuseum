package com.example.smart_museum;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

class Adapter extends BaseAdapter {
    Activity context;
    ArrayList <Studente> studenti;
    private static LayoutInflater inflater = null;

    public Adapter(Activity context, ArrayList<Studente> studenti)
    {
        this.context = context;
        this.studenti = studenti;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int  getCount() {
        return studenti.size();
    }

    @Override
    public Studente getItem(int position)
    {
        return studenti.get(position);
    }

    @Override
    public long getItemId (int position)
    {
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent)
    {
        View itemView = convertView;
        itemView = (itemView == null) ? inflater.inflate(R.layout.prova,null):itemView;
        TextView nome = (TextView) itemView.findViewById(R.id.prova_nome);
        TextView email = (TextView) itemView.findViewById(R.id.prova_email);
        Studente selezionato = studenti.get(position);
        nome.setText(selezionato.nome);
        email.setText(selezionato.email);
        return itemView;
    }
}
