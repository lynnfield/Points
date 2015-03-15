package com.lynnfield.points.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lynnfield.points.db.models.IRecord;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public interface IMigration
{
    public static final String PK = "INTEGER PRIMARY KEY";
    public static final String PKAI = PK + " AUTOINCREMENT";
    public static final String TEXT = "TEXT";
    public static final String INTEGER = "INTEGER";
    public static final String REAL = "REAL";


    void apply(SQLiteDatabase db);
    void revert(SQLiteDatabase db);
}
