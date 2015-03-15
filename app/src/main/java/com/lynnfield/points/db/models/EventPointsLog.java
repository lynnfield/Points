package com.lynnfield.points.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynnfield.points.db.migrations.EventPointsLogMigration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public final class EventPointsLog
    extends IRecordImpl
    implements IEventPointsLog
{
    Long mId;
    IEvent mEvent;
    Integer mPoints;
    Date mDate;

    @Override
    public IEvent getEvent()
    {
        return mEvent;
    }

    @Override
    public void setEvent(IEvent event)
    {
        mEvent = event;
    }

    @Override
    public Integer getPoints()
    {
        return mPoints;
    }

    @Override
    public void setPoints(Integer points)
    {
        mPoints = points;
    }

    @Override
    public Date getDate()
    {
        return mDate;
    }

    @Override
    public void setDate(Date date)
    {
        mDate = date;
    }

    @Override
    public Long getId()
    {
        return mId;
    }

    @Override
    public void setId(Long id)
    {
        mId = id;
    }

    @Override
    public List<? extends IRecord> find(SQLiteDatabase db)
    {
        StringBuilder where =
            new StringBuilder();
        boolean mIdExists =
            mId != null;
        boolean mEventExists =
            mEvent != null;
        boolean mPointsExists =
            mPoints != null;
        boolean mDateExists =
            mDate != null;
        if (mIdExists)
        {
            where
                .append(EventPointsLogMigration._ID)
                .append(',')
                .append(mId.toString());
        }
        if (mIdExists && mEventExists)
            where.append(',');
        if (mEventExists)
        {
            where
                .append(EventPointsLogMigration.EVENT_ID)
                .append('=')
                .append(mEvent.getId());
        }
        if ((mIdExists || mEventExists) && mPointsExists)
            where.append(',');
        if (mPointsExists)
        {
            where
                .append(EventPointsLogMigration.POINTS_AMOUNT)
                .append('=')
                .append(mPoints);
        }
        if ((mIdExists || mEventExists || mPointsExists) && mDateExists)
            where.append(',');
        if (mDateExists)
        {
            where
                .append(EventPointsLogMigration.CHANGE_DATE)
                .append('=')
                .append(mDate.getTime());
        }
        Cursor cursor = db.query(
            getTableName(),
            getProjection(),
            where.toString(),
            null,
            null,
            null,
            null);
        List<IEventPointsLog> ret =
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
    public void fillFromCursor(Cursor cursor)
    {
        mId =
            cursor.getLong(
                cursor.getColumnIndexOrThrow(
                    EventPointsLogMigration._ID));
        mEvent = new Event();
        mEvent.setId(
            cursor.getLong(
                cursor.getColumnIndexOrThrow(
                    EventPointsLogMigration.EVENT_ID)));
        mPoints =
            cursor.getInt(
                cursor.getColumnIndexOrThrow(
                    EventPointsLogMigration.POINTS_AMOUNT));
        mDate =
            new Date(
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(
                        EventPointsLogMigration.CHANGE_DATE)));
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues values =
                new ContentValues();
        values.put(
            EventPointsLogMigration._ID,
            mId);
        values.put(
            EventPointsLogMigration.EVENT_ID,
            mEvent.getId());
        values.put(
            EventPointsLogMigration.POINTS_AMOUNT,
            mPoints);
        values.put(
            EventPointsLogMigration.CHANGE_DATE,
            mDate.getTime());
        return values;
    }

    @Override
    public String[] getProjection() {
        return new String[]
        {
            EventPointsLogMigration._ID,
            EventPointsLogMigration.EVENT_ID,
            EventPointsLogMigration.POINTS_AMOUNT,
            EventPointsLogMigration.CHANGE_DATE
        };
    }

    @Override
    public String getTableName() {
        return EventPointsLogMigration.TABLE_NAME;
    }

    public static EventPointsLog fromCursor(Cursor cursor)
    {
        EventPointsLog ret =
            new EventPointsLog();
        ret.fillFromCursor(cursor);
        return ret;
    }
}
