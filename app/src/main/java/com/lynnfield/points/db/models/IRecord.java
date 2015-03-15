package com.lynnfield.points.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public interface IRecord
{
    Long getId();
    void setId(Long id);

    List<? extends IRecord> find(SQLiteDatabase db);
    void fetch(SQLiteDatabase db);
    void save(SQLiteDatabase db);
    void remove(SQLiteDatabase db);
    void fillFromCursor(Cursor cursor);
    ContentValues toContentValues();
    String[] getProjection();
    String getTableName();
    String getWhere();
}
