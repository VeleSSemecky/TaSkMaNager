package com.example.chornovic.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chornovic.R;
import com.example.chornovic.task.Task;

import java.util.ArrayList;

/**
 * Created by Юрий on 26.05.2016.
 */
public class TaskAdapter extends BaseAdapter {

    ArrayList<Task> tasks = new ArrayList<>();
    LayoutInflater lInflater;
    int color1 ;
    int color2 ;
    int color3 ;

    public int getColor1() {
        return color1;
    }

    public int getColor2() {
        return color2;
    }

    public int getColor3() {
        return color3;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public void setColor3(int color3) {
        this.color3 = color3;
    }

    public TaskAdapter(ArrayList<Task> users, Activity activity) {
        this.tasks = users;
        lInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    //avoids the multiple search items in the list
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = lInflater.inflate(R.layout.item_adapter, null, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.nameTextView);
            viewHolder.comment = (TextView) convertView.findViewById(R.id.commentTextView);
            viewHolder.time = (TextView) convertView.findViewById(R.id.dateTAsk);
            convertView.setTag(viewHolder);
            } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //adding name and comment in ListView
        Task task = (Task) getItem(position);
        if (task != null) {
            viewHolder.name.setText(task.getName());
            viewHolder.comment.setText(task.getComment());
            viewHolder.time.setText(task.getTime());
            try {
                if (task.getTime().length() == 0) {
                    convertView.setBackgroundColor(color1);
                } else if (task.getTime().length() == 16) {
                    convertView.setBackgroundColor(color2);
                } else if (task.getTime().length() > 16) {
                    convertView.setBackgroundColor(color3);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }
        return convertView;
    }


    private class ViewHolder {
        TextView name;
        TextView comment;
        TextView time;
    }
    public void clearData() {
        // clear the data
        tasks.clear();
    }


}
