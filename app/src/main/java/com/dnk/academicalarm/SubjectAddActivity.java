package com.dnk.academicalarm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SubjectAddActivity extends AppCompatActivity {
    HashMap<Integer, Date[]> result = new HashMap<>();
    CheckBox[] boxes = new CheckBox[5];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_add);

        boxes[0] = (CheckBox) findViewById(R.id.mon);
        boxes[1] = (CheckBox) findViewById(R.id.tue);
        boxes[2] = (CheckBox) findViewById(R.id.wed);
        boxes[3] = (CheckBox) findViewById(R.id.thu);
        boxes[4] = (CheckBox) findViewById(R.id.fri);
    }

    public void checkDay(View view) {
        CheckBox checkBox = (CheckBox) findViewById(view.getId());
        int id = 0;
        Date[] dates = new Date[2];
        TextView textView;
        SimpleDateFormat or_format = new SimpleDateFormat("HH:mm");
        switch (view.getId()){
            case R.id.mon:
                id = 0;
                textView = (TextView) findViewById(R.id.time0);
                break;
            case R.id.tue:
                id = 1;
                textView = (TextView) findViewById(R.id.time1);
                break;
            case R.id.wed:
                id = 2;
                textView = (TextView) findViewById(R.id.time2);
                break;
            case R.id.thu:
                id = 3;
                textView = (TextView) findViewById(R.id.time3);
                break;
            case R.id.fri:
                id = 4;
                textView = (TextView) findViewById(R.id.time4);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + view.getId());
        }
        if (checkBox.isChecked()){
            int finalId = id;

            TimePickerDialog timePickerDialog2 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    try {
                        Date date = or_format.parse(hourOfDay+":"+minute);
                        dates[1] = date;
                        if (date.before(dates[0])){
                            Toast.makeText(getApplicationContext(), "잘못된 시각 입니다. 다시 선택해주세요.", Toast.LENGTH_SHORT).show();
                            boxes[finalId].setChecked(false);
                        }else {
                            String start = or_format.format(dates[0]);
                            String end = or_format.format(dates[1]);

                            textView.setText(start+"~"+end);
                            result.put(finalId, dates);
                            checkBox.setChecked(true);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }, 12, 0, true);
            TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    try {
                        Date date = or_format.parse(hourOfDay+":"+minute);
                        dates[0] = date;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), "종료 시각을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    timePickerDialog2.show();
                }
            }, 12, 0, true);
            Toast.makeText(this, "시작 시각을 선택해주세요.", Toast.LENGTH_SHORT).show();
            timePickerDialog1.show();
            if(textView.getText().toString().equals("")){
                checkBox.setChecked(false);
            }
        }else{
            result.remove(id);
            textView.setText("");
        }

    }

    public void saveCancel(View view) {
        EditText name = (EditText) findViewById(R.id.name);
        String subject = name.getText().toString();
        SubjectDatabase subjectDatabase = SubjectDatabase.getAppDatabase(getApplicationContext());
        DayDatabase dayDatabase = DayDatabase.getAppDatabase(getApplicationContext());

        if(view.getId() == R.id.save) {
            if (subject.equals("")){
                Toast.makeText(this, "과목 이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!checked()){
                Toast.makeText(this, "강의 요일을 선택하세요.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!(result.size()==0)){
                new InsertAsyncTask(subjectDatabase.subjectDao()).execute(new Subject(subject, ConvertDate.convert(result)));
                Integer[] data = result.keySet().toArray(new Integer[0]);
                data = new ArrayList<Integer>(Arrays.asList(data)).add(su);
                new InsertAsyncTask1(dayDatabase.dayDao()).execute();
            }
        }
        finish();
    }

    public boolean checked(){
        boolean result = false;
        for (int i=0;i<5;i++){
            if (boxes[i].isChecked()){
                result = result || true;
            }
        }
        return result;
    }
    public static class InsertAsyncTask extends AsyncTask<Subject, Void, Void>{
        SubjectDao subjectDao;

        public InsertAsyncTask(SubjectDao subjectDao) {
            this.subjectDao = subjectDao;
        }

        @Override
        protected Void doInBackground(Subject... subjects) {
            subjectDao.insert(subjects[0]);
            return null;
        }
    }
    public static class InsertAsyncTask1 extends AsyncTask<Integer[], Void, Void>{
        DayDao dayDao;

        public InsertAsyncTask1(DayDao dayDao) {
            this.dayDao = dayDao;
        }

        @Override
        protected Void doInBackground(Integer[]... days) {
            Day day = dayDao.getDay(days[0][0]);
            day.setData(days[0][1].toString());
            dayDao.update(day);
            return null;
        }
    }
}