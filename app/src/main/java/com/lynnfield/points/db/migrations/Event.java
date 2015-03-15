package com.lynnfield.points.db.migrations;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.lynnfield.points.db.IMigration;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public final class Event
    implements IMigration, BaseColumns
{
    public static final String TABLE_NAME = "event";
    public static final String NAME = "name";
    public static final String POINTS = "points";

    @Override
    public void apply(SQLiteDatabase db)
    {
        db.execSQL(
            "CREATE TABLE %s (%s, %s, %s)",
            new Object[]
            {
                TABLE_NAME,
                String.format("%s %s", _ID, PKAI),
                String.format("%s %s", NAME, TEXT),
                String.format("%s %s", POINTS, REAL)
            });
    }

    @Override
    public void revert(SQLiteDatabase db)
    {
        db.execSQL("DROP TABLE " + TABLE_NAME);
    }

    @Override
    public Cursor find(SQLiteDatabase db, String where)
    {
        return db.query(
            TABLE_NAME,
            new String[]
            {
                _ID,
                NAME,
                POINTS
            },
            where,
            null,
            null,
            null,
            null);
    }

    @Override
    public Cursor fetch(SQLiteDatabase db, Long id)
    {
        return db.query(
            TABLE_NAME,
            new String[]
            {
                _ID,
                NAME,
                POINTS
            },
            _ID + " = %s",
            new String[]
            {
                id.toString()
            },
            null,
            null,
            null);
    }

    @Override
    public void save(SQLiteDatabase db, ContentValues values)
    {

    }

    @Override
    public void delete(SQLiteDatabase db, Long id)
    {
        db.delete(
            TABLE_NAME,
            _ID + " = %s",
            new String[]
            {
                id.toString()
            });
    }
}
