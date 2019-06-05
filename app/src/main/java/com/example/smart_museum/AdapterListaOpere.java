package com.example.smart_museum;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterListaOpere extends BaseAdapter {
    Activity context;
    ArrayList<SavedOpera> a;
    private static LayoutInflater inflater = null;

    public AdapterListaOpere(Activity context, ArrayList<SavedOpera> tmp)
    {
        this.context = context;
        this.a = tmp;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int  getCount() { return a.size(); }

    @Override
    public SavedOpera getItem(int position)
    {
        return a.get(position);
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
        itemView = (itemView == null) ? inflater.inflate(R.layout.lista_opere,null):itemView;
        TextView nome = (TextView) itemView.findViewById(R.id.ListaOpere_Nome);
        TextView desc= (TextView) itemView.findViewById(R.id.ListaOpere_Desc);
        ImageView image = (ImageView) itemView.findViewById(R.id.ListaOpere_Image);
        SavedOpera selezionato = this.a.get(position);
        nome.setText(selezionato.getNome());
        desc.setText(selezionato.getDescrizione().substring(0,50)+"...");
        image.setImageBitmap(selezionato.getImage());

        return itemView;
    }
}
