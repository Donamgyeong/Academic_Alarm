package com.dnk.academicalarm;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConvertDate {
    static final String TAG = "ConvertDate";
    static SimpleDateFormat or_format = new SimpleDateFormat("HH:mm");
    public static String convert(HashMap<Integer, Date[]> data){
        String result = "";
        for (Map.Entry<Integer, Date[]> entry:data.entrySet()){
            result = result +entry.getKey()+"/"+or_format.format(entry.getValue()[0])+"/"+or_format.format(entry.getValue()[1])+"=";
        }
        return result;
    }
    public static HashMap<Integer, Date[]> convert(String data){
        String[] arrayData = data.split("=");
        HashMap<Integer, Date[]> result = new HashMap<>();
        for (int i=0;i<arrayData.length;i++){
            String[] time = arrayData[i].split("/");
            Date[] dates = new Date[2];
            try {
                dates[0] = or_format.parse(time[1]);
                dates[1] = or_format.parse(time[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Log.d(TAG, time[0]);
            result.put(Integer.parseInt(time[0]), dates);
        }
        Log.d(TAG, result.keySet().toString());
        return result;
    }
}
