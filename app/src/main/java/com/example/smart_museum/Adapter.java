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
    ArrayList <Musei> museo;
    private static LayoutInflater inflater = null;

    public Adapter(Activity context, ArrayList<Musei> musei)
    {
        this.context = context;
        this.museo = musei;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int  getCount() {
        return museo.size();
    }

    @Override
    public Musei getItem(int position)
    {
        return museo.get(position);
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
        itemView = (itemView == null) ? inflater.inflate(R.layout.lista,null):itemView;
        TextView nome = (TextView) itemView.findViewById(R.id.prova_nome);
        TextView email = (TextView) itemView.findViewById(R.id.prova_email);
        Musei selezionato = museo.get(position);
        nome.setText(selezionato.getNome());
        email.setText(selezionato.getIndirizzo());
        return itemView;
    }
}
