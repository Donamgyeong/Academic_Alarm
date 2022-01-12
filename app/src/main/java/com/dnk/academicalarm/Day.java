package com.dnk.academicalarm;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Day {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String data;

    public Day(String data){
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data += data + "/";
    }

    @Override
    public String toString() {
        return "Day{" +
                "data='" + data + '\'' +
                '}';
    }
}
