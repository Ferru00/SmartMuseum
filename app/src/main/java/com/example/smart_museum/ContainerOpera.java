package com.example.smart_museum;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ContainerOpera {
    ArrayList<SavedOpera> a= new ArrayList<>();

    public void SaveOpera (SavedOpera tmp, Context ctx)
    {
        try
        {
            this.a.add(tmp);

            FileOutputStream fos = ctx.openFileOutput("data.bin", Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(this.a);
            os.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            Log.i("ERRORE", ioe.toString());
        }

    }

    public void LoadOpera (Context ctx)
    {
        try
        {
            FileInputStream fis = ctx.openFileInput("data.bin");
            ObjectInputStream is = new ObjectInputStream(fis);
            this.a = (ArrayList<SavedOpera>) is.readObject();
            is.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            Log.i("ERRORE", ioe.toString());
        }
        catch (ClassNotFoundException r)
        {
            Log.i("ERRORE", "3");
        }
    }

    public int cippi(){
        return a.size();
    }


    public void deleteFile(String dir){
        File file = new File(dir, "data.bin");
        boolean deleted = file.delete();
    }

    public ArrayList<SavedOpera> getArrayList()
    {
        return this.a;
    }

    public SavedOpera getItemPosition (int pos)
    {
        return this.a.get(pos);
    }
}
