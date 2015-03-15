package com.lynnfield.points.db.models;

import android.content.ContentValues;
import android.database.Cursor;

import com.lynnfield.points.db.migrations.PointsMigration;

import java.util.Date;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public final class Points
    extends IRecordImpl<Points>
    implements IPoints
{
    IEvent mEvent;
    Float mPoints;
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
    public Float getAmount()
    {
        return mPoints;
    }

    @Override
    public void setAmount(Float points)
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
    public void fillFromCursor(Cursor cursor)
    {
        super.fillFromCursor(cursor);
        mEvent = new Event();
        mEvent.setId(
            cursor.getLong(
                cursor.getColumnIndexOrThrow(
                    PointsMigration.EVENT_ID)));
        mPoints =
            cursor.getFloat(
                cursor.getColumnIndexOrThrow(
                    PointsMigration.POINTS_AMOUNT));
        mDate =
            new Date(
                cursor.getInt(
                    cursor.getColumnIndexOrThrow(
                        PointsMigration.CHANGE_DATE)));
    }

    @Override
    public ContentValues toContentValues()
    {
        ContentValues values =
                super.toContentValues();
        values.put(
                PointsMigration.EVENT_ID,
                mEvent.getId());
        values.put(
            PointsMigration.POINTS_AMOUNT,
            mPoints);
        values.put(
                PointsMigration.CHANGE_DATE,
                mDate.getTime());
        return values;
    }

    @Override
    public String[] getProjection() {
        return new String[]
        {
            super.getProjection()[0],
            PointsMigration.EVENT_ID,
            PointsMigration.POINTS_AMOUNT,
            PointsMigration.CHANGE_DATE
        };
    }

    @Override
    public String getTableName() {
        return PointsMigration.TABLE_NAME;
    }

    @Override
    public String getWhere() {
        StringBuilder where =
                new StringBuilder(
                    super.getWhere());
        boolean mEventExists =
                mEvent != null;
        boolean mPointsExists =
                mPoints != null;
        boolean mDateExists =
                mDate != null;
        if (isIdExists() && mEventExists)
            where.append(',');
        if (mEventExists)
        {
            where
                .append(PointsMigration.EVENT_ID)
                .append('=')
                .append(mEvent.getId());
        }
        if ((isIdExists() || mEventExists) && mPointsExists)
            where.append(',');
        if (mPointsExists)
        {
            where
                .append(PointsMigration.POINTS_AMOUNT)
                .append('=')
                .append(mPoints);
        }
        if ((isIdExists() || mEventExists || mPointsExists) && mDateExists)
            where.append(',');
        if (mDateExists)
        {
            where
                .append(PointsMigration.CHANGE_DATE)
                .append('=')
                .append(mDate.getTime());
        }
        return where.toString();
    }

    @Override
    protected Points fromCursor(Cursor cursor) {
        Points ret = new Points();
        ret.fillFromCursor(cursor);
        return ret;
    }

    @Override
    public String toString() {
        return "Points{" +
                "id=" + (id == null ? "null" : id) +
                ", mEvent=" + (mEvent == null ? "null" : (mEvent.getId() == null ? "null" : mEvent.getId())) +
                ", mPoints=" + (mPoints == null ? "null" : mPoints) +
                ", mDate=" + (mDate == null ? "null" : mDate) +
                '}';
    }
}
