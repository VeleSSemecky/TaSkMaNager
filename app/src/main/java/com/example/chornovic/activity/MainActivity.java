package com.example.chornovic.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.android.colorpicker.ColorStateDrawable;
import com.example.chornovic.R;
import com.example.chornovic.adapter.TaskAdapter;
import com.example.chornovic.task.Task;
import com.example.chornovic.util.StorageShipment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.TimeZone;
import java.util.UUID;




/**
 * Created by Юрий on 26.05.2016.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener, AdapterView.OnItemClickListener, AppCompatCallback, FloatingActionButton.OnClickListener{

    private static int save = -1;
    private ListView listView;
    private static final String LIST_STATE = "listState";
    private static final String JSON_NAME = "name";
    private static final String JSON_COMMENT = "comment";
    private static final String JSON_TIME = "time";
    private static final String MY_SETTINGS = "my_settings";
    private TaskAdapter mAdapter;
    private int mPositionInList;
    private ArrayList<Task> mTaskList = new ArrayList<>();
    private String mNameTask, mCommentTask, mTimeTask;
    private StorageShipment storageShipment = new StorageShipment();
    Calendar firstTime,spentTime,endTime;
    private Gson mSerializerAndDeserializer;
    private String mGJsonSting;
    private Task mTask;
    private int mColor1, mColor2, mColor3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listView = (ListView) findViewById(R.id.listView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        // проверяем, первый ли раз открывается программа
            try {
                downloadTask();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);
        assert fab != null;
        fab.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.inflateMenu(R.menu.menu_main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_autocomplete:
                for (int i = 0; i < 15; i++) {
                    mNameTask = JSON_NAME + (int) (Math.random() * 9);
                    mCommentTask = JSON_COMMENT + "\n" + (int) (Math.random() * 15) + "\n" + UUID.randomUUID().toString();
                    mTimeTask = "";
                    mTaskList.add(new Task(mNameTask, mCommentTask, mTimeTask));
                    mStoreCommonData();
                }
                return true;
            case R.id.action_remove:
                final SharedPreferences sharedPreferences = getSharedPreferences("Task", Context.MODE_PRIVATE);
                sharedPreferences.edit().clear().apply();
                mTaskList.clear();
                mAdapter.clearData();
                mAdapter.notifyDataSetChanged();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_sort_a_z:
                Collections.sort(mTaskList,
                        new Comparator<Task>() {
                            @Override
                            public int compare(Task lhs, Task rhs) {
                                return lhs.getName().compareTo(rhs.getName());
                            }
                        }
                );
                mStoreCommonData();
                item.setChecked(true);
                item.isChecked();
                return true;
            case R.id.action_sort_z_a:
                Collections.sort(mTaskList,
                        new Comparator<Task>() {
                            @Override
                            public int compare(Task lhs, Task rhs) {
                                return rhs.getName().compareTo(lhs.getName());
                            }
                        }
                );
                mStoreCommonData();
                item.setChecked(true);
                return true;
            case R.id.action_sort_start_date:
                Collections.sort(mTaskList,
                        new Comparator<Task>() {
                            @Override
                            public int compare(Task lhs, Task rhs) {
                                return lhs.getTime().compareTo(rhs.getTime());
                            }
                        }
                );
                mStoreCommonData();
                item.setChecked(true);
                return true;
            case R.id.action_sort_end_date:
                Collections.sort(mTaskList,
                        new Comparator<Task>() {
                            @Override
                            public int compare(Task lhs, Task rhs) {
                                return rhs.getTime().compareTo(lhs.getTime());
                            }
                        }
                );
                mStoreCommonData();
                item.setChecked(true);
                return true;
            case R.id.action_add:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivityForResult(intent, 3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //get data from SecondActivity and adding in ListView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    mNameTask = data.getStringExtra(JSON_NAME);
                    mCommentTask = data.getStringExtra(JSON_COMMENT);
                    mTimeTask = "";
                    Log.d("name", "2" + mNameTask);
                    Log.d("comment", "2" + mCommentTask);
                    Log.d("time", "2" + mTimeTask);

                    mTaskList.add(new Task(mNameTask, mCommentTask, mTimeTask));
                    mStoreCommonData();
                    break;
                case 2:
                    mNameTask = data.getStringExtra(JSON_NAME);
                    mCommentTask = data.getStringExtra(JSON_COMMENT);
                    mTimeTask = data.getStringExtra(JSON_TIME);
                    mTaskList.set(mPositionInList, new Task(mNameTask, mCommentTask, mTimeTask));
                    mStoreCommonData();
                case 3:
                    mColor1 = data.getIntExtra("firstTime",mColor1);
                    mColor2 = data.getIntExtra("spentTime",mColor2);
                    mColor3 = data.getIntExtra("endTime",mColor3);

                    Log.d("color1", String.valueOf(mColor1));
                    Log.d("color2", String.valueOf(mColor2));
                    Log.d("color3", String.valueOf(mColor2));
                    storageShipment.saveColor(getApplicationContext(),mColor1,mColor2,mColor3);
                        mAdapter.setColor1(mColor1);
                        mAdapter.setColor2(mColor2);
                        mAdapter.setColor3(mColor3);
                        mAdapter.notifyDataSetChanged();
                    break;
            }

        }
    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList(LIST_STATE,musers);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        susers = savedInstanceState.getParcelableArrayList(LIST_STATE);
//        mmadapter = new TaskAdapter(susers, this);
//        listView.setAdapter(mmadapter);
//    }


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, EditActivity.class);
        mTask = mTaskList.get(position);
        intent.putExtra("names", mTask.getName());
        intent.putExtra("comments", mTask.getComment());
        intent.putExtra("times", mTask.getTime());
        mPositionInList = position;
        startActivityForResult(intent, 2);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mTask = mTaskList.get(position);
        String loadTime = mTask.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        SimpleDateFormat a = new SimpleDateFormat("HH:mm");
        firstTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
        endTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
        spentTime = Calendar.getInstance(TimeZone.getTimeZone("GMT+01:00"));
        try {
            firstTime.setTime(simpleDateFormat.parse(loadTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String sTIme = mTask.getTime();
        if (sTIme.isEmpty()) {
            sTIme = simpleDateFormat.format(endTime.getTime());
        } else if (sTIme.equals(simpleDateFormat.format(firstTime.getTime()))) {
            long diff = endTime.getTimeInMillis() - firstTime.getTimeInMillis()-10800000;
            spentTime.setTimeInMillis(diff);
            sTIme = simpleDateFormat.format(firstTime.getTime()) + "-" + simpleDateFormat.format(endTime.getTime()) + " " + a.format(spentTime.getTime());
        }
        mTaskList.set(position, new Task(mTask.getName(), mTask.getComment(), sTIme));
        mStoreCommonData();

    }


    private void downloadTask() throws JSONException{
        Thread t = new Thread(new Runnable() {
            public void run() {
                mGJsonSting = storageShipment.loadTasks(getApplicationContext());
                mSerializerAndDeserializer = new GsonBuilder().setPrettyPrinting().create();
                if(!mGJsonSting.isEmpty()) {
                    mTaskList = mSerializerAndDeserializer.fromJson(mGJsonSting, new TypeToken<ArrayList<Task>>() {}.getType());
                    mAdapter = new TaskAdapter(mTaskList, MainActivity.this);
                    mColor1 =storageShipment.loadColor1(getApplicationContext());
                    mColor2 =storageShipment.loadColor2(getApplicationContext());
                    mColor3 =storageShipment.loadColor3(getApplicationContext());

                    if(mColor1==0) {
                        mAdapter.setColor1(Color.parseColor("#696969"));
                    }
                    else {
                        mAdapter.setColor1(mColor1);
                    }
                    if(mColor2==0) {
                        mAdapter.setColor2(Color.parseColor("#696969"));
                    }
                    else {
                        mAdapter.setColor2(mColor2);
                    }
                    if(mColor3==0) {
                        mAdapter.setColor3(Color.parseColor("#696969"));
                    }
                    else {
                        mAdapter.setColor3(mColor3);
                    }
                    listView.setAdapter(mAdapter);
                }
            }
        });
        t.start();
    }
    private void mStoreCommonData(){
        mSerializerAndDeserializer = new GsonBuilder().setPrettyPrinting().create();
        mGJsonSting = mSerializerAndDeserializer.toJson(mTaskList,new TypeToken<ArrayList<Task>>() {}.getType());
//        mAdapter.notifyDataSetChanged();
        listView.setAdapter(mAdapter);
        storageShipment.saveTasks(getApplicationContext(), mGJsonSting);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab:
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivityForResult(intent, 1);
        }
    }
}

