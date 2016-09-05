package com.example.android.habittracker.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jaren Lynch on 8/21/2016.
 */
public class HabitDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = HabitDbHelper.class.getSimpleName();

    //Name of database file
    private static final String DATABASE_NAME = "habitTracker.db";

    //Database version. If database schema is changed, must increment database version.
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of HabitDbHelper
     * @param context of the app
     */
    public HabitDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     * @param db is habit database
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create a string that contains the SQL statement to create the pets table.
        String SQL_CREATE_HABITS_TABLE = "CREATE TABLE " + HabitContract.HabitEntry.TABLE_NAME + " ("
                + HabitContract.HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + HabitContract.HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL, "
                + HabitContract.HabitEntry.COLUMN_HABIT_DAY + " TEXT NOT NULL "
                + HabitContract.HabitEntry.COLUMN_HABIT_DURATION + " INTEGER NOT NULL DEFAULT 0);";

        //Execute the SQL statement.
        db.execSQL(SQL_CREATE_HABITS_TABLE);
    }

    /**
     * This is called when the database needs to be updated
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    // This will delete whole database when called
    public void deleteDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + HabitContract.HabitEntry.TABLE_NAME);
        onCreate(db);
    }
}
