package com.example.smart_museum;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

public class SavedOpera implements Serializable {


    private String nome, descrizione;
    private byte[] image;

    public  SavedOpera (String nome, String descrizione, Bitmap image)
    {
        this.nome = nome;
        this.descrizione = descrizione;
        this.image = android.util.Base64.decode(getStringImage(image), android.util.Base64.DEFAULT);;
    }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getDescrizione() { return descrizione; }

    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public Bitmap getImage() {
        Bitmap bmp = BitmapFactory.decodeByteArray(this.image, 0, this.image.length);
        return bmp;
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = android.util.Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
}
