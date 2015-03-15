package com.lynnfield.points.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynnfield.points.db.migrations.EventMigration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public final class Event
    extends IRecordImpl<Event>
    implements IEvent
{
    String mName;
    IPoints mPoints;

    @Override
    public String getName()
    {
        return mName;
    }

    @Override
    public void setName(String name)
    {
        mName = name;
    }

    @Override
    public IPoints getPoints()
    {
        return mPoints;
    }

    @Override
    public void setPoints(IPoints points)
    {
        mPoints = points;
    }

    @Override
    public void fillFromCursor(Cursor cursor)
    {
        super.fillFromCursor(cursor);
        mName =
            cursor.getString(
                cursor.getColumnIndexOrThrow(
                    EventMigration.NAME));
        mPoints = new Points();
        mPoints.setId(
                cursor.getLong(
                        cursor.getColumnIndexOrThrow(
                                EventMigration.POINTS_ID)));
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues values =
            super.toContentValues();
        values.put(
            EventMigration.NAME, mName);
        values.put(
            EventMigration.POINTS_ID, mPoints.getId());
        return values;
    }

    @Override
    public String[] getProjection() {
        return new String[]
        {
            super.getProjection()[0],
            EventMigration.NAME,
            EventMigration.POINTS_ID
        };
    }

    @Override
    public String getTableName() {
        return EventMigration.TABLE_NAME;
    }

    @Override
    public String getWhere()
    {
        StringBuilder where =
                new StringBuilder(
                    super.getWhere());
        boolean mNameExists =
                mName != null && !mName.isEmpty();
        boolean mPointsExists =
                mPoints != null;
        if (isIdExists() && mNameExists)
            where.append(',');
        if (mNameExists)
            where
                .append(EventMigration.NAME)
                .append(" LIKE '")
                .append(mName)
                .append("%'");
        if ((isIdExists() || mNameExists) && mPointsExists)
            where.append(',');
        if (mPointsExists)
            where
                .append(EventMigration.POINTS_ID)
                .append('=')
                .append(mPoints);
        return where.toString();
    }

    @Override
    protected Event fromCursor(Cursor cursor)
    {
        Event ret = new Event();
        ret.fillFromCursor(cursor);
        return ret;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + (id == null ? "null" : id.toString()) +
                ", mName='" + (mName == null ? "null" : mName) + '\'' +
                ", mPoints=" + (mPoints == null ? "null" : (mPoints.getId() == null ? "null" : mPoints.getId())) +
                '}';
    }
}
