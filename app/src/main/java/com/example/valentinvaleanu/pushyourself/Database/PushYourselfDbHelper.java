package com.example.valentinvaleanu.pushyourself.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.valentinvaleanu.pushyourself.Database.DataContract;

/**
 * Created by Admin on 15.11.2017 г..
 */

public class PushYourselfDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "pushyourself.db";
    private static final int DATABASE_VERSION = 1;

    public PushYourselfDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ARMS_EXERCISE = "CREATE TABLE " + DataContract.DataEntry.TABLE_ARMS_NAME + "("
                + DataContract.DataEntry.ARMS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.DataEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_MUSCLE_GROUP + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT,"
                + DataContract.DataEntry.COLUMN_IMAGE_ID + " TEXT,"
                + DataContract.DataEntry.COLUMN_DIFFICULTY + " TEXT NOT NULL) ;";
        db.execSQL(SQL_CREATE_ARMS_EXERCISE);

        String SQL_CREATE_LEGS_EXERCISE = "CREATE TABLE " + DataContract.DataEntry.TABLE_LEGS_NAME + "("
                + DataContract.DataEntry.LEGS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.DataEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_MUSCLE_GROUP + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT,"
                + DataContract.DataEntry.COLUMN_IMAGE_ID + " TEXT,"
                + DataContract.DataEntry.COLUMN_DIFFICULTY + " TEXT NOT NULL) ;";
        db.execSQL(SQL_CREATE_LEGS_EXERCISE);

        String SQL_CREATE_CHEST_EXERCISE = "CREATE TABLE " + DataContract.DataEntry.TABLE_CHEST_NAME + "("
                + DataContract.DataEntry.CHEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.DataEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_MUSCLE_GROUP + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT,"
                + DataContract.DataEntry.COLUMN_IMAGE_ID + " TEXT,"
                + DataContract.DataEntry.COLUMN_DIFFICULTY + " TEXT NOT NULL) ;";
        db.execSQL(SQL_CREATE_CHEST_EXERCISE);

        String SQL_CREATE_ABS_EXERCISE = "CREATE TABLE " + DataContract.DataEntry.TABLE_ABS_NAME + "("
                + DataContract.DataEntry.ABS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.DataEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_MUSCLE_GROUP + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT,"
                + DataContract.DataEntry.COLUMN_IMAGE_ID + " TEXT,"
                + DataContract.DataEntry.COLUMN_DIFFICULTY + " TEXT NOT NULL) ;";
        db.execSQL(SQL_CREATE_ABS_EXERCISE);

        String SQL_CREATE_CARDIO_EXERCISE = "CREATE TABLE " + DataContract.DataEntry.TABLE_CARDIO_NAME + "("
                + DataContract.DataEntry.CARDIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.DataEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_MUSCLE_GROUP + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT,"
                + DataContract.DataEntry.COLUMN_IMAGE_ID + " TEXT,"
                + DataContract.DataEntry.COLUMN_DIFFICULTY + " TEXT NOT NULL) ;";
        db.execSQL(SQL_CREATE_CARDIO_EXERCISE);

        String SQL_CREATE_BACK_EXERCISE = "CREATE TABLE " + DataContract.DataEntry.TABLE_BACK_NAME + "("
                + DataContract.DataEntry.BACK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.DataEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_MUSCLE_GROUP + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT,"
                + DataContract.DataEntry.COLUMN_IMAGE_ID + " TEXT,"
                + DataContract.DataEntry.COLUMN_DIFFICULTY + " TEXT NOT NULL) ;";
        db.execSQL(SQL_CREATE_BACK_EXERCISE);

        String SQL_CREATE_SCHEDULE_EXERCISE = "CREATE TABLE " + DataContract.DataEntry.TABLE_SCHEDULE_NAME + "("
                + DataContract.DataEntry.SCHEDULE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DataContract.DataEntry.COLUMN_NAME + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_MUSCLE_GROUP + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_DESCRIPTION + " TEXT,"
                + DataContract.DataEntry.COLUMN_DIFFICULTY + " TEXT NOT NULL,"
                + DataContract.DataEntry.COLUMN_IMAGE_ID + " TEXT,"
                + DataContract.DataEntry.COLUMN_STATUS + " TEXT,"
                + DataContract.DataEntry.COLUMN_ADDED_DATE + " TEXT NOT NULL) ;";
        db.execSQL(SQL_CREATE_SCHEDULE_EXERCISE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
