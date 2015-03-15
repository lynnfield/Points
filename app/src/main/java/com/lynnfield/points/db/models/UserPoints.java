package com.lynnfield.points.db.models;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.lynnfield.points.db.migrations.UserPointsMigration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Genovich V.V. on 11.03.2015.
 */
public final class UserPoints
    extends IRecordImpl<UserPoints>
    implements IUserPoints
{
    private IPoints mPoints;
    private Date mDate;

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
        mPoints = new Points();
        mPoints.setId(
            cursor.getLong(
                cursor.getColumnIndex(
                    UserPointsMigration.POINTS_ID)));
        mDate = new Date(
            cursor.getLong(
                cursor.getColumnIndex(
                    UserPointsMigration.CREATED)));
    }

    @Override
    public ContentValues toContentValues() {
        ContentValues values =
            super.toContentValues();
        values.put(
            UserPointsMigration.POINTS_ID,
            mPoints.getId());
        values.put(
            UserPointsMigration.CREATED,
            mDate.getTime());
        return values;
    }

    @Override
    public String[] getProjection() {
        return new String[]
        {
            super.getProjection()[0],
            UserPointsMigration.POINTS_ID,
            UserPointsMigration.CREATED
        };
    }

    @Override
    public String getTableName() {
        return UserPointsMigration.TABLE_NAME;
    }

    @Override
    public String getWhere() {
        StringBuilder where =
            new StringBuilder(super.getWhere());
        boolean mPointsExists =
            mPoints != null;
        boolean mDateExists =
            mDate != null;
        if (isIdExists() && mPointsExists)
            where.append(',');
        if (mPointsExists)
            where
                .append(UserPointsMigration.POINTS_ID)
                .append('=')
                .append(mPoints.getId());
        if ((isIdExists() || mPointsExists) && mDateExists)
            where.append(',');
        if (mDateExists)
            where
                .append(UserPointsMigration.CREATED)
                .append('=')
                .append(mDate.getTime());
        String ret =
            where.toString();
        return ret.isEmpty() ? null : ret;
    }

    @Override
    protected UserPoints fromCursor(Cursor cursor)
    {
        UserPoints ret = new UserPoints();
        ret.fillFromCursor(cursor);
        return ret;
    }

    @Override
    public String toString() {
        return "UserPoints{" +
                "id=" + (id == null ? "null" : id) +
                ", mPoints=" + (mPoints == null ? "null" : (mPoints.getId() == null ? "null" : mPoints.getId())) +
                ", mDate=" + (mDate == null ? "null" : mDate) +
                '}';
    }
}
