package com.example.chornovic.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;


/**
 * Created by Юрий on 02.06.2016.
 */
public class StorageShipment {
    private static final String TAG = "save";

    private SharedPreferences sharedPreferences;
    final String SAVED_TEXT = "saved_text";
    final String  SAVED_COLOR1 =  "saved_color1";
    final String  SAVED_COLOR2 =  "saved_color2";
    final String  SAVED_COLOR3 =  "saved_color3";





    public void saveTasks(Context context, String jsonString) {

        sharedPreferences = context.getSharedPreferences("Task", Context.MODE_PRIVATE);

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString(SAVED_TEXT, jsonString);

        ed.apply();
//        Toast.makeText(context.getApplicationContext(), jsonString, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "2" + jsonString);
//        sharedPreferences.edit().clear().apply();
    }

    public String loadTasks(Context context) {
            sharedPreferences = context.getSharedPreferences("Task", Context.MODE_PRIVATE);
            String savedText = sharedPreferences.getString(SAVED_TEXT, "");
//        etText.setText(savedText);
//        Toast.makeText(context.getApplicationContext(), "load" + savedText, Toast.LENGTH_SHORT).show();
            return savedText;
    }
    public void saveColor(Context context,int mColor1,int mColor2,int mColor3){
        sharedPreferences = context.getSharedPreferences("Color", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putInt(SAVED_COLOR1, mColor1);
        ed.putInt(SAVED_COLOR2, mColor2);
        ed.putInt(SAVED_COLOR3, mColor3);
        ed.apply();
    }
    public int loadColor1(Context context){
        sharedPreferences = context.getSharedPreferences("Color", Context.MODE_PRIVATE);
        int color1 = sharedPreferences.getInt(SAVED_COLOR1,0);
        return color1;
    }
    public int loadColor2(Context context){
        sharedPreferences = context.getSharedPreferences("Color", Context.MODE_PRIVATE);
        int color2 = sharedPreferences.getInt(SAVED_COLOR2,0);
        return color2;
    }
    public int loadColor3(Context context){
        sharedPreferences = context.getSharedPreferences("Color", Context.MODE_PRIVATE);
        int color3 = sharedPreferences.getInt(SAVED_COLOR3,0);
        return color3;
    }
}
