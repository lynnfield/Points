package com.lynnfield.points.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lynnfield.points.db.migrations.EventMigration;
import com.lynnfield.points.db.migrations.PointsMigration;
import com.lynnfield.points.db.migrations.UserPointsMigration;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public class Helper extends SQLiteOpenHelper {
    private static final int FIRST_VERSION = 1;
    private static final int CURRENT_VERSION = FIRST_VERSION;

    private static final String DB_NAME = "points_db";

    public Helper(Context context)
    {
        super(context, DB_NAME, null, CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        new EventMigration().apply(db);
        new PointsMigration().apply(db);
        new UserPointsMigration().apply(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
