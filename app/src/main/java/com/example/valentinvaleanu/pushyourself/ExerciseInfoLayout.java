package com.example.valentinvaleanu.pushyourself;

/**
 * Created by Aleksandar on 13.11.2017 г..
 */

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentinvaleanu.pushyourself.Database.DataContract;
import com.example.valentinvaleanu.pushyourself.Database.PushYourselfDbHelper;
import com.spark.submitbutton.SubmitButton;

import java.util.Calendar;

public class ExerciseInfoLayout extends AppCompatActivity {

    private SubmitButton but;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_layout);

        Bundle bundle = getIntent().getExtras();

        final int theIndex = (int)bundle.get("index");
        final String theName = (String)bundle.get("ex_name");
        final String theMuscleGroup = (String)bundle.get("muscle_group");
        final String theDifficulty = (String)bundle.get("ex_difficulty");
        final String theDescription = (String)bundle.get("ex_description");
        final String theImageID = (String)bundle.get("ex_image_id");
        final String theOption = (String)bundle.get("option");

        TextView exName = (TextView) findViewById(R.id.ex_name);
        TextView exDescription = (TextView) findViewById(R.id.desc_prg1);
        TextView exDifficulty = (TextView) findViewById(R.id.ex_difficulty);

        ImageView exImage = (ImageView) findViewById(R.id.exercise_image);

        int imageID = getResourceId(this, theImageID, "drawable", getPackageName());

        exName.setText(theName);
        exDescription.setText(theDescription);
        exDifficulty.setText(theDifficulty);
        exImage.setImageResource(imageID);

        but = (SubmitButton) findViewById(R.id.spButton);

        if (theOption.equals("not_display")) but.setVisibility(View.INVISIBLE); else
            if (theOption.equals("add_to_schedule")) {
                but.setText("Add");
                but.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {



                        PushYourselfDbHelper dbHelper = new PushYourselfDbHelper(ExerciseInfoLayout.this);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();

                        values.put(DataContract.DataEntry.COLUMN_NAME, theName);
                        values.put(DataContract.DataEntry.COLUMN_DIFFICULTY, theDifficulty);
                        values.put(DataContract.DataEntry.COLUMN_DESCRIPTION, theDescription);
                        values.put(DataContract.DataEntry.COLUMN_MUSCLE_GROUP, theMuscleGroup);
                        values.put(DataContract.DataEntry.COLUMN_IMAGE_ID, theImageID);
                        values.put(DataContract.DataEntry.COLUMN_STATUS, "undone");
                        values.put(DataContract.DataEntry.COLUMN_ADDED_DATE, String.valueOf(Calendar.getInstance().getTime()));
                        db.insert("schedule_exercises", null, values);

                        db.close();
                    }
                });

        } else {
                but.setText("Done");
                but.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                        PushYourselfDbHelper dbHelper = new PushYourselfDbHelper(ExerciseInfoLayout.this);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(DataContract.DataEntry.COLUMN_STATUS, "done");

                        String selection = DataContract.DataEntry._ID + "= ?";
                        String[] selectionArgs = {theIndex + ""};

                        db.update("schedule_exercises", values, selection, selectionArgs);

                    }
                });

            }
    }

    private static int getResourceId(Context context, String pVariableName, String pResourcename, String pPackageName) throws RuntimeException {
        try {
            return context.getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
        } catch (Exception e) {
            throw new RuntimeException("Error getting Resource ID.", e);
        }
    }

    @Override
    protected void onStart() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onStart();
    }
}
