package com.example.android.cecunosti.data;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Archangel on 4/28/2017.
 * Trebuie sa facem si parsarea fisierului astfel incat sa rezulte
 * obiectele java asociate.
 *clasa LoaderHelper citeste continutul unui fisier din Assets si sa il returneaza
 * intr-un String .
 */

public class LoaderHelper {

    public static String getJson(Context context, String json) {
        String jsonString = parseFileToString(context, json);
        return jsonString;
    }

    public static String parseFileToString(Context context, String filename) {

        try {
            InputStream stream = context.getAssets().open(filename);
            int size = stream.available();
            byte[] bytes = new byte[size];
            stream.read(bytes);
            stream.close();
            return new String(bytes);
        } catch (IOException e) {
            Log.i("GuiFormData", "IOException: " + e.getMessage());
        }
        return null;
    }
}
