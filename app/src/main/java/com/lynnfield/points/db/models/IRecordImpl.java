package com.lynnfield.points.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genovich V.V. on 10.03.2015.
 */
public abstract class IRecordImpl<T extends IRecordImpl>
    implements IRecord
{
    protected Long id;

    @Override
    public void setId(Long id)
    {
        this.id = id;
    }

    @Override
    public Long getId()
    {
        return id;
    }

    @Override
    public List<T> find(SQLiteDatabase db)
    {
        Cursor cursor = db.query(
                getTableName(),
                getProjection(),
                getWhere(),
                null,
                null,
                null,
                null);
        List<T> ret =
                new ArrayList<>();
        for (
            cursor.moveToFirst();
            !cursor.isAfterLast();
            cursor.moveToNext())
        {
            ret.add(fromCursor(cursor));
        }
        return ret;
    }

    @Override
    public void fetch(SQLiteDatabase db)
    {
        Cursor cursor =
            db.query(
                getTableName(),
                getProjection(),
                BaseColumns._ID + "=" + getId().toString(),
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        fillFromCursor(cursor);
    }

    @Override
    public void save(SQLiteDatabase db)
    {
        if (getId() == null)
        {
            setId(
                db.insert(
                    getTableName(),
                    null,
                    toContentValues()));
        }
        else
        {
            db.update(
                getTableName(),
                toContentValues(),
                BaseColumns._ID + "=" + getId().toString(),
                null);
        }
    }

    @Override
    public void remove(SQLiteDatabase db)
    {
        db.delete(
            getTableName(),
            BaseColumns._ID + "=" + getId().toString(),
            null);
    }

    @Override
    public void fillFromCursor(Cursor cursor)
    {
        id =
            cursor.getLong(
                cursor.getColumnIndexOrThrow(
                    BaseColumns._ID));
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues ret =
                new ContentValues();
        ret.put(BaseColumns._ID, id);
        return ret;
    }

    @Override
    public String[] getProjection() {
        return new String[]
        {
            BaseColumns._ID
        };
    }

    @Override
    public String getWhere() {
        StringBuilder ret =
                new StringBuilder();
        if (isIdExists())
        {
            ret
                .append(BaseColumns._ID)
                .append('=')
                .append(id);
        }
        return ret.toString();
    }

    protected boolean isIdExists()
    {
        return id != null;
    }

    protected abstract T fromCursor(Cursor cursor);
}
