package com.dnk.academicalarm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {
    final String TAG = "SubjectAdapter";
    ArrayList<String> names = new ArrayList<String>();
    HashMap<String, HashMap<Integer, Date[]>> data = new HashMap<>();
    public SubjectAdapter(List<Subject> lists) {
        setData(lists);
    }
    public void setData(List<Subject> lists){
        if(lists.isEmpty()){
            Log.d(TAG, "empty");
            return;
        }
        for(int i=0;i<lists.size();i++){
            String name = lists.get(i).getName();
            Log.d(TAG, name);
            HashMap<Integer, Date[]> time = ConvertDate.convert(lists.get(i).getData());
            this.data.put(name, time);
            names.add(name);
        }
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, day_week;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.subject_name);
            day_week = (TextView) itemView.findViewById(R.id.day_of_week);
        }
    }

    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_subject_recycler, parent, false);

        SubjectAdapter.ViewHolder viewHolder = new SubjectAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder holder, int position) {
        holder.name.setText(names.get(position));

        HashMap<Integer, Date[]> times = data.get(names.get(position));
        String days = "";
        if(times.isEmpty()){
            return;
        }
        for(Integer key:times.keySet()){
            switch (key){
                case 0:
                    days += "월";
                    break;
                case 1:
                    days += "화";
                    break;
                case 2:
                    days += "수";
                    break;
                case 3:
                    days += "목";
                    break;
                case 4:
                    days += "금";
                    break;
            }
        }
        holder.day_week.setText(days);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
