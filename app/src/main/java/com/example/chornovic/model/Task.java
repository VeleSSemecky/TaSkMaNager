package com.example.chornovic.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Юрий on 26.05.2016.
 */
public class Task implements Parcelable {
    String name;
    String comment;
    String time;

    public Task(String name, String comment,String time) {
        this.name = name;
        this.comment = comment;
        this.time = time;
    }

    protected Task(Parcel in) {
        name = in.readString();
        comment = in.readString();
        time = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };


    public void setTime(String time) {
        this.time = time;
    }



    public String getTime() {
        return time;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {
        return name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(comment);
        dest.writeString(time);
    }

}

