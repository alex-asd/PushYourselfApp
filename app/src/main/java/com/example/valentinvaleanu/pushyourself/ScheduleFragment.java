package com.example.valentinvaleanu.pushyourself;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.valentinvaleanu.pushyourself.Database.DataContract;
import com.example.valentinvaleanu.pushyourself.Database.PushYourselfDbHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Valentin Valeanu on 11/11/2017.
 */

public class ScheduleFragment extends Fragment
{
    private View view;
    private ArrayList<ExerciseWithImage> arrayList;

    public ScheduleFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.schedule_activity, container, false);

        ListView listViewTodayExercises = (ListView)view.findViewById(R.id.list_today_exercises);
        ListView listViewPrevExercises = (ListView) view.findViewById(R.id.list_prev_exercises);

        PushYourselfDbHelper dbHelper = new PushYourselfDbHelper(this.getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DataContract.DataEntry._ID, DataContract.DataEntry.COLUMN_NAME, DataContract.DataEntry.COLUMN_MUSCLE_GROUP,
                DataContract.DataEntry.COLUMN_DESCRIPTION, DataContract.DataEntry.COLUMN_DIFFICULTY,
                DataContract.DataEntry.COLUMN_IMAGE_ID, DataContract.DataEntry.COLUMN_STATUS, DataContract.DataEntry.COLUMN_ADDED_DATE};

        Cursor cursor = db.query("schedule_exercises", projection, null, null, null, null, null);

        int idColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.ARMS_ID);
        int nameColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME);
        int muscleGroupColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_MUSCLE_GROUP);
        int descriptionColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_DESCRIPTION);
        int difficultyColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_DIFFICULTY);
        int addedDateColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_ADDED_DATE);
        int imageIDColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_IMAGE_ID);
        int statusColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_STATUS);

        final ArrayList<ExerciseWithImageAndStatus> todayExercisesWithImageAndStatus = new ArrayList<ExerciseWithImageAndStatus>();
        final ArrayList<ExerciseWithImageAndStatus> prevExercisesWithImageAndStatus = new ArrayList<ExerciseWithImageAndStatus>();

        final ArrayList<Exercise> todayExercises = new ArrayList<Exercise>();
        final ArrayList<Exercise> prevExercises = new ArrayList<Exercise>();

        final ArrayList<Integer> theIndexForToday = new ArrayList<Integer>();
        final ArrayList<Integer> theIndexForPrev = new ArrayList<Integer>();

        while (cursor.moveToNext()) {

            int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentMuscleGroup = cursor.getString(muscleGroupColumnIndex);
            String currentDescription = cursor.getString(descriptionColumnIndex);
            String currentDifficulty = cursor.getString(difficultyColumnIndex);
            String currentImageID = cursor.getString(imageIDColumnIndex);
            String currentAddedDate = cursor.getString(addedDateColumnIndex);
            String currentStatus = cursor.getString(statusColumnIndex);

            Date currentDate = Calendar.getInstance().getTime();

            DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
            Date date;

            try {

                date = df.parse(currentAddedDate);

                if (currentDate.getTime() - date.getTime() >= 24 * 60 * 60 * 1000) {

                    prevExercisesWithImageAndStatus.add(new ExerciseWithImageAndStatus(currentName, currentImageID, currentStatus));
                    prevExercises.add(new Exercise(currentName, currentDifficulty, currentMuscleGroup, currentDescription, currentImageID));
                    theIndexForPrev.add(currentID);

                } else {

                    todayExercisesWithImageAndStatus.add(new ExerciseWithImageAndStatus(currentName, currentImageID, currentStatus));
                    todayExercises.add(new Exercise(currentName, currentDifficulty, currentMuscleGroup, currentDescription, currentImageID));
                    theIndexForToday.add(currentID);

                }
            } catch (ParseException e) {

                e.printStackTrace();
            }
        }

        ExercisesWithImageAndStatusAdapter todayExerciseAdapter = new ExercisesWithImageAndStatusAdapter(getActivity(), todayExercisesWithImageAndStatus);
        ExercisesWithImageAndStatusAdapter prevExerciseAdapter = new ExercisesWithImageAndStatusAdapter(getActivity(), prevExercisesWithImageAndStatus);

        listViewTodayExercises.setAdapter(todayExerciseAdapter);
        listViewPrevExercises.setAdapter(prevExerciseAdapter);

        listViewTodayExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent newIntent = new Intent(getContext(), ExerciseInfoLayout.class);

                newIntent.putExtra("index", theIndexForToday.get(i));
                newIntent.putExtra("muscle_group", todayExercises.get(i).getMuscleGroup());
                newIntent.putExtra("ex_name", todayExercises.get(i).getName());
                newIntent.putExtra("ex_description", todayExercises.get(i).getDescription());
                newIntent.putExtra("ex_difficulty", todayExercises.get(i).getDifficulty());
                newIntent.putExtra("ex_image_id", todayExercises.get(i).getImageID());
                newIntent.putExtra("option", "add_to_done");

                startActivity(newIntent);

            }
        });

        listViewPrevExercises.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent newIntent = new Intent(getContext(), ExerciseInfoLayout.class);

                newIntent.putExtra("index", theIndexForPrev.get(i));
                newIntent.putExtra("muscle_group", prevExercises.get(i).getMuscleGroup());
                newIntent.putExtra("ex_name", prevExercises.get(i).getName());
                newIntent.putExtra("ex_description", prevExercises.get(i).getDescription());
                newIntent.putExtra("ex_difficulty", prevExercises.get(i).getDifficulty());
                newIntent.putExtra("ex_image_id", prevExercises.get(i).getImageID());
                newIntent.putExtra("option", "not_display");

                startActivity(newIntent);
            }
        });

        return view;
    }

}
