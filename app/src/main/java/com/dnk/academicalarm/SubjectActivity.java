package com.dnk.academicalarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SubjectActivity extends AppCompatActivity {
    final String TAG = "SubjectActivity";
    FloatingActionButton addSubjectButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.subjectList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SubjectDatabase db = SubjectDatabase.getAppDatabase(getApplicationContext());
        List<Subject> data = null;
        try {
            data = new InsertAsyncTask(db.subjectDao()).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Log.d("SubjectActivity", ""+data.size());
        SubjectAdapter adapter = new SubjectAdapter(data);
        recyclerView.setAdapter(adapter);

        addSubjectButton = (FloatingActionButton) findViewById(R.id.addSubjectButton);

        db.subjectDao().getAll().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(List<Subject> subjects) {
                Log.d(TAG, "Changed");
                adapter.setData(subjects);
                recyclerView.removeAllViewsInLayout();
                recyclerView.setAdapter(adapter);
            }
        });
        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SubjectAddActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });

    }
    public static class InsertAsyncTask extends AsyncTask<Void, Void, List<Subject>> {
        SubjectDao subjectDao;
        public InsertAsyncTask(SubjectDao subjectDao) {
            super();
            this.subjectDao = subjectDao;
        }

        @Override
        protected List<Subject> doInBackground(Void... voids) {
            return subjectDao.getSubjects();
        }
    }
}