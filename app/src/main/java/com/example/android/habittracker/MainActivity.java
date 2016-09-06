package com.example.android.habittracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.habittracker.Data.HabitContract;
import com.example.android.habittracker.Data.HabitDbHelper;

public class MainActivity extends AppCompatActivity {


    private HabitDbHelper mDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDbHelper = new HabitDbHelper(this);
        displayDatabaseInfo();
    }

    // Temporary helper method to display informaton in the onscreen textview about
    // the state of the pets database.

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    public void insertHabit() {
        // Create database helper
        HabitDbHelper mDbHelper = new HabitDbHelper(this);

        // Makes database writable
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        //Create a ContentValues object where the column names are the keys,
        // and habit attributes from the editor are the values.,
        ContentValues values= new ContentValues();
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_NAME, "TESt");
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DURATION, 100);
        values.put(HabitContract.HabitEntry.COLUMN_HABIT_DAY, 100);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, values);
        Log.v("MainActivity", "New row ID " + newRowId);
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

        TextView displayView = (TextView) findViewById(R.id.displayView);

        try {
            displayView.setText("The database contains " + cursor.getCount() + " habits.\n\n");
            displayView.append(HabitContract.HabitEntry._ID + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_DAY + " - " +
                    HabitContract.HabitEntry.COLUMN_HABIT_DURATION + "\n\n");

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
                displayView.append(currentId + " - " +
                        currentHabit + " - " +
                        currentDay + " - " +
                        currentDuration);
            }
        }
        finally {
            cursor.close();
        }
    }

    public Cursor getHabit(int id) {

        //Search habit using ID in database.
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + HabitContract.HabitEntry.TABLE_NAME + " where " + HabitContract.HabitEntry._ID + "=" + id  , null);
        return cursor;

    }

    public void update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.update(table, values, whereClause, whereArgs);

    }

    // This will delete all rows when called
    public boolean deleteDatabase(Context context) {
        return context.deleteDatabase(HabitContract.HabitEntry.TABLE_NAME);

    }

    //Will delete database when called.
    public void deleteDb(SQLiteDatabase db) {
        db = mDbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM " + HabitContract.HabitEntry.TABLE_NAME);
        db.close();
    }
}
