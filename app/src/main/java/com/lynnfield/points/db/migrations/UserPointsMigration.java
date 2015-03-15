package com.lynnfield.points.db.migrations;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.lynnfield.points.db.IMigration;

/**
 * Created by Genovich V.V. on 10.03.2015.
 */
public final class UserPointsMigration
    implements IMigration, BaseColumns
{
    public static final String TABLE_NAME = "user_points";
    public static final String POINTS_ID = "points_id";
    public static final String CREATED = "created";

    @Override
    public void apply(SQLiteDatabase db)
    {
        db.execSQL(
            String.format(
                "CREATE TABLE %s (%s, %s, %s)",
                TABLE_NAME,
                String.format("%s %s", _ID, PKAI),
                String.format("%s %s", POINTS_ID, INTEGER),
                String.format("%s %s", CREATED, INTEGER)));
    }

    @Override
    public void revert(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE " + TABLE_NAME);
    }
}
