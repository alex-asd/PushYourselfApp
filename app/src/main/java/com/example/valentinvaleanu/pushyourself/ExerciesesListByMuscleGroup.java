package com.example.valentinvaleanu.pushyourself;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.valentinvaleanu.pushyourself.Database.DataContract;
import com.example.valentinvaleanu.pushyourself.Database.PushYourselfDbHelper;

import java.util.ArrayList;

/**
 * Created by Valentin Valeanu on 11/11/2017.
 */

public class ExerciesesListByMuscleGroup extends AppCompatActivity
{
    private ArrayList<Exercise> exercises;
    private ArrayList<ExerciseWithImage> exerciseWithImageWithImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercises_list_layout);

        final ListView listView = (ListView) findViewById(R.id.exercises_list);

        Bundle bundle = getIntent().getExtras();

        final String muscleGroup;

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        if (bundle != null) {

            muscleGroup = (String) bundle.get("muscle_group");

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("muscle_group", muscleGroup);

            editor.commit();

        } else {

            muscleGroup = sharedPreferences.getString("muscle_group", "empty");

        }

        setAdapterForListView(listView, muscleGroup);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent newIntent = new Intent(ExerciesesListByMuscleGroup.this, ExerciseInfoLayout.class);

                newIntent.putExtra("index", i);
                newIntent.putExtra("muscle_group", muscleGroup);
                newIntent.putExtra("ex_name", exercises.get(i).getName());
                newIntent.putExtra("ex_description", exercises.get(i).getDescription());
                newIntent.putExtra("ex_difficulty", exercises.get(i).getDifficulty());
                newIntent.putExtra("ex_image_id", exercises.get(i).getImageID());
                newIntent.putExtra("option", "add_to_schedule");

                startActivity(newIntent);

            }
        });
    }

    private void setAdapterForListView(ListView listView, String muscleGroup)
    {
        PushYourselfDbHelper dbHelper = new PushYourselfDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DataContract.DataEntry._ID, DataContract.DataEntry.COLUMN_NAME, DataContract.DataEntry.COLUMN_MUSCLE_GROUP,
                DataContract.DataEntry.COLUMN_DESCRIPTION, DataContract.DataEntry.COLUMN_DIFFICULTY, DataContract.DataEntry.COLUMN_IMAGE_ID};

        Cursor cursor = db.query(muscleGroup + "_exercises", projection, null, null, null, null, null);

        exercises = new ArrayList<Exercise>();
        exerciseWithImageWithImage = new ArrayList<ExerciseWithImage>();

        int idColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.ARMS_ID);
        int nameColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_NAME);
        int muscleGroupColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_MUSCLE_GROUP);
        int descriptionColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_DESCRIPTION);
        int difficultyColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_DIFFICULTY);
        int imageIDColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_IMAGE_ID);

        while (cursor.moveToNext()) {

            int currentID = cursor.getInt(idColumnIndex);
            String currentName = cursor.getString(nameColumnIndex);
            String currentMuscleGroup = cursor.getString(muscleGroupColumnIndex);
            String currentDescription = cursor.getString(descriptionColumnIndex);
            String currentDifficulty = cursor.getString(difficultyColumnIndex);
            String currentImageID = cursor.getString(imageIDColumnIndex);

            exercises.add(new Exercise(currentName, currentDifficulty, currentMuscleGroup, currentDescription, currentImageID));
            exerciseWithImageWithImage.add(new ExerciseWithImage(currentName, currentImageID));
        }

        ExercisesWithImageAdapter adapter = new ExercisesWithImageAdapter(this, exerciseWithImageWithImage);

        cursor.close();

        listView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onStart();
    }
}
