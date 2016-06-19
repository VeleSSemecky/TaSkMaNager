package com.example.chornovic.activity;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.example.chornovic.R;

import android.content.Intent;
import android.graphics.Color;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.util.Log;

public class SettingsActivity extends PreferenceActivity  {


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
    }


    public static class MyPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener {
        Intent intent = new Intent();
        Preference mFirstTime;
        Preference mSpentTime;
        Preference mEndTime;
        Preference mRestoreDefault;


        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
            mFirstTime = findPreference("firstTime");
            mSpentTime = findPreference("spentTime");
            mEndTime = findPreference("endTime");
            mRestoreDefault = findPreference("restoreDefault");
            mFirstTime.setOnPreferenceClickListener(this);
            mSpentTime.setOnPreferenceClickListener(this);
            mEndTime.setOnPreferenceClickListener(this);
            mRestoreDefault.setOnPreferenceClickListener(this);
        }

        @Override
        public boolean onPreferenceClick(Preference preference) {
            switch (preference.getKey()){
                case "firstTime":{
                    Log.d("addres", "1234");
                    mColorPickerDialog("firstTime");
                    return false;
                }
                case "spentTime":{
                    Log.d("addres", "123");
                    mColorPickerDialog("spentTime");
                    return false;
                }
                case "endTime":{
                    Log.d("addres", "123");
                    mColorPickerDialog("endTime");
                    return false;
                }
                case "restoreDefault":{
                    intent.putExtra("firstTime", Color.parseColor("#026c07"));
                    intent.putExtra("spentTime", Color.parseColor("#FF9B00"));
                    intent.putExtra("endTime", Color.parseColor("#a61a1a"));
//                    Log.d("addreswww", String.valueOf(Color.parseColor("#026c07")));
                    getActivity().setResult(RESULT_OK, intent);
//                    mColorPickerDialog("endTime");
                    return false;
                }
                default:
                    return false;
            }

        }
        public void mColorPickerDialog(String s){
            final String s1 = s;
            int[] rainbow = getResources().getIntArray(R.array.rainbow);
            ColorPickerDialog colorPickerDialog2 = new ColorPickerDialog();
            colorPickerDialog2.initialize(
                    R.string.sort_by_a_z, rainbow, R.color.grey, 4, rainbow.length);
            colorPickerDialog2.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {
                @Override
                public void onColorSelected(int color) {
                    intent.putExtra(s1, color);
                    getActivity().setResult(RESULT_OK, intent);
                }
            });
            colorPickerDialog2.show(getFragmentManager(), "123");
        }
    }
}




