package com.example.android.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.example.android.habittracker.Data.HabitContract;
import com.example.android.habittracker.Data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {

    private EditText mHabitEditText;
    private EditText mDurationEditText;
    private EditText mDayEditText;

    private HabitDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Temporary helper method to display informaton in the onscreen textview about
    // the state of the pets database.

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    public void insertHabit() {
        String habitString = mHabitEditText.getText().toString().trim();
        String durationString = mDurationEditText.getText().toString().trim();
        String dayInteger = mDayEditText.getText().toString().trim();

        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Makes database writable
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ContentValues object where the column names are the keys,
        // and habit attributes from the editor are the values.,
        ContentValues values= new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, habitString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DURATION, durationString);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DAY, durationString);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
    }

    private void displayDatabaseInfo() {
        HabitDbHelper mDbhelper = new HabitDbHelper(this);

        //Create and open a database to read from it
        SQLiteDatabase db = mDbhelper.getReadableDatabase();

        String[] project = {
            HabitContract.HabitEntry._ID,
                HabitContract.HabitEntry.COLUMN_HABIT_NAME,
                HabitContract.HabitEntry.COLUMN_HABIT_DURATION,
                HabitContract.HabitEntry.COLUMN_HABIT_DAY
        };

        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                project,
                null,
                null,
                null,
                null,
                null);
        try {
            //Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry._ID);
            int habitColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_NAME);
            int durationColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_DURATION);
            int dayColumnIndex = cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_HABIT_DAY);

            //Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                //Use the index to extract the string or int value of the word
                // at the current row the cursor is on
                int currentId = cursor.getInt(idColumnIndex);
                String currentHabit = cursor.getString(habitColumnIndex);
                int currentDuration = cursor.getInt(durationColumnIndex);
                int currentDay = cursor.getInt(dayColumnIndex);
            }
        }
        finally {
            cursor.close();
        }
    }

}
