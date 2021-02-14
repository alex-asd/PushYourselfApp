package com.example.valentinvaleanu.pushyourself;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.valentinvaleanu.pushyourself.Database.DataContract;
import com.example.valentinvaleanu.pushyourself.Database.PushYourselfDbHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;

    private MenuItem prevMenuItem;
    private BottomNavigationView bottomNavigationView;
    private ExercisesFragment exercisesFragment;
    private ScheduleFragment scheduleFragment;
    private AchievementFragment achievementFragment;

    private StorageReference mStorageRef;

    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);

        if (!sharedPreferences.contains("downloaded")) {

            SharedPreferences.Editor editor = sharedPreferences.edit();

            loadFromFirebase();

            editor.putBoolean("downloaded", true);

            editor.commit();
        }

        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_dehaze_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView n = (NavigationView) findViewById(R.id.navigation);

        n.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.drawer_menu_settings:
                        Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();

                }
                mDrawerLayout.closeDrawers();  // CLOSE DRAWER
                return true;
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_exercises:

                                viewPager.setCurrentItem(0);

                                break;
                            case R.id.action_schedules:

                                viewPager.setCurrentItem(1);

                                break;
                            case R.id.action_achievements:

                                viewPager.setCurrentItem(2);

                                break;
                        }
                        return false;
                    }
                });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        setupViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        scheduleFragment = new ScheduleFragment();

        viewPagerAdapter.removeFragment(1);

        viewPagerAdapter.addFragment(1, scheduleFragment);

        viewPager.setAdapter(viewPagerAdapter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        View header = navigationView.getHeaderView(0);
        TextView pointsView = (TextView) header.findViewById(R.id.the_points);

        PushYourselfDbHelper dbHelper = new PushYourselfDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {DataContract.DataEntry._ID, DataContract.DataEntry.COLUMN_DIFFICULTY, DataContract.DataEntry.COLUMN_STATUS};

        Cursor cursor = db.query("schedule_exercises", projection, null, null, null, null, null);

        int points = 0;
        int idColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.ARMS_ID);
        int difficultyColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_DIFFICULTY);
        int statusColumnIndex = cursor.getColumnIndex(DataContract.DataEntry.COLUMN_STATUS);

        while (cursor.moveToNext()) {

            int currentID = cursor.getInt(idColumnIndex);
            String currentDifficulty = cursor.getString(difficultyColumnIndex);
            String currentStatus = cursor.getString(statusColumnIndex);

            if (currentStatus.equals("done")) {

                switch (currentDifficulty){

                    case "Easy": { points += 100; break; }
                    case "Medium": { points += 150; break; }
                    case "Hard": { points += 250; break; }

                }

            }
        }

        pointsView.setText("Points : " + points);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // THIS IS YOUR DRAWER/HAMBURGER BUTTON
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public void openExercises(View view)
    {
        Intent newIntent;

        switch(view.getId()) {

            case R.id.arms_exercises:

                newIntent = new Intent(MainActivity.this, ExerciesesListByMuscleGroup.class);

                newIntent.putExtra("muscle_group", "arms");

                startActivity(newIntent);

                break;

            case R.id.chest_exercises:

                newIntent = new Intent(MainActivity.this, ExerciesesListByMuscleGroup.class);

                newIntent.putExtra("muscle_group", "chest");

                startActivity(newIntent);

                break;

            case R.id.abs_exercises:

                newIntent = new Intent(MainActivity.this, ExerciesesListByMuscleGroup.class);

                newIntent.putExtra("muscle_group", "abs");

                startActivity(newIntent);

                break;

            case R.id.back_exercises:

                newIntent = new Intent(MainActivity.this, ExerciesesListByMuscleGroup.class);

                newIntent.putExtra("muscle_group", "back");

                startActivity(newIntent);

                break;

            case R.id.legs_exercises:

                newIntent = new Intent(MainActivity.this, ExerciesesListByMuscleGroup.class);

                newIntent.putExtra("muscle_group", "legs");

                startActivity(newIntent);

                break;

            case R.id.cardio_exercises:

                newIntent = new Intent(MainActivity.this, ExerciesesListByMuscleGroup.class);

                newIntent.putExtra("muscle_group", "cardio");

                startActivity(newIntent);

                break;

        }
    }

    private void setupViewPager(ViewPager viewPager)
    {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        exercisesFragment = new ExercisesFragment();
        scheduleFragment = new ScheduleFragment();
        achievementFragment = new AchievementFragment();

        viewPagerAdapter.addFragment(exercisesFragment);
        viewPagerAdapter.addFragment(scheduleFragment);
        viewPagerAdapter.addFragment(achievementFragment);

        viewPager.setAdapter(viewPagerAdapter);
    }

    public void loadFromFirebase()
    {
        database = FirebaseDatabase.getInstance();

        readChildrenFromDataSnapshot("abs");
        readChildrenFromDataSnapshot("arms");
        readChildrenFromDataSnapshot( "back");
        readChildrenFromDataSnapshot("legs");
        readChildrenFromDataSnapshot("chest");
        readChildrenFromDataSnapshot("cardio");

    }

    public void readChildrenFromDataSnapshot(final String muscleGroup)
    {
        final ArrayList<Exercise> data = new ArrayList<Exercise>();

        databaseReference = database.getReference("Exercises").child(muscleGroup);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {

                    data.add(childDataSnapshot.getValue(Exercise.class)); // cast to Exercise class

                }

                addToSQL(muscleGroup, data);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addToSQL(String muscleGroup, ArrayList<Exercise> data)
    {
        PushYourselfDbHelper dbHelper = new PushYourselfDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < data.size(); i++) {

            ContentValues values = new ContentValues();

            values.put(DataContract.DataEntry.COLUMN_NAME, data.get(i).getName());
            values.put(DataContract.DataEntry.COLUMN_DIFFICULTY, data.get(i).getDifficulty());
            values.put(DataContract.DataEntry.COLUMN_DESCRIPTION, data.get(i).getDescription());
            values.put(DataContract.DataEntry.COLUMN_MUSCLE_GROUP, data.get(i).getMuscleGroup());
            values.put(DataContract.DataEntry.COLUMN_IMAGE_ID, data.get(i).getImageID());
            db.insert(muscleGroup + "_exercises", null, values);

        }

        db.close();

    }
}
