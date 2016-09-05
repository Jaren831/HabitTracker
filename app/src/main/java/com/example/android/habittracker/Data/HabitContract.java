package com.example.android.habittracker.Data;

import android.provider.BaseColumns;

/**
 * Created by Jaren Lynch on 8/21/2016.
 */
public final class HabitContract {
    private HabitContract() {}

    public static final class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "habits";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_HABIT_NAME = "name";
        public final static String COLUMN_HABIT_DURATION = "duration";
        public final static String COLUMN_HABIT_DAY = "day";

        public static final int DAY_MONDAY = 0;
        public static final int DAY_TUESDAY = 1;
        public static final int DAY_WENESDAY = 2;
        public static final int DAY_THURSDAY = 3;
        public static final int DAY_FRIDAY = 4;
        public static final int DAY_SATURDAY = 5;
        public static final int DAY_SUNDAY = 6;
    }
}
