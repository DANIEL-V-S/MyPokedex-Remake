package com.danrio.mypokedex;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JsonMethods {

    private String readJsonFromAssets(Context context, int rawFile) {
        String json = null;
        try {
            InputStream inputStream = context.getResources().openRawResource(rawFile);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public List<String> importTypesEn(Context context) {
        List<String> typesList = new ArrayList<>();
        try {
            JSONArray array = new JSONObject(Objects.requireNonNull(readJsonFromAssets(context, R.raw.types))).getJSONArray("typesEn");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                typesList.add(obj.getString("NAME"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
        return typesList;
    }

    public List<String> importTypesPt(Context context) {
        List<String> typesList = new ArrayList<>();
        try {
            JSONArray array = new JSONObject(Objects.requireNonNull(readJsonFromAssets(context, R.raw.types))).getJSONArray("typesBr");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                typesList.add(obj.getString("NAME"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>();
        }
        return typesList;
    }

    public void importAbilitiesEn(Context context) {
        readJsonFromAssets(context, R.raw.types);
    }

    public void importAbilitiesPt(Context context) {
        readJsonFromAssets(context, R.raw.types);
    }
}
