package com.lynnfield.points.db.migrations;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.lynnfield.points.db.IMigration;

/**
 * Created by Genovich V.V. on 10.03.2015.
 */
public final class PointsMigration
    implements IMigration, BaseColumns
{
    public static final String TABLE_NAME = "event_points_log";
    public static final String EVENT_ID = "event_id";
    public static final String POINTS_AMOUNT = "points_amount";
    public static final String CHANGE_DATE = "change_date";

    @Override
    public void apply(SQLiteDatabase db)
    {
        db.execSQL(
            String.format(
                "CREATE TABLE %s (%s, %s, %s, %s)",
                    TABLE_NAME,
                    String.format("%s %s", _ID, PKAI),
                    String.format("%s %s", EVENT_ID, INTEGER),
                    String.format("%s %s", POINTS_AMOUNT, REAL),
                    String.format("%s %s", CHANGE_DATE, INTEGER)));
    }

    @Override
    public void revert(SQLiteDatabase db) {
        db.execSQL("DROP TABLE " + TABLE_NAME);
    }
}
