package com.lynnfield.points.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.lynnfield.points.db.Helper;
import com.lynnfield.points.db.models.UserPoints;

import java.util.List;

/**
 * Created by Genovich V.V. on 12.03.2015.
 */
public final class UserPointsLoader
    extends AsyncTask<Void, Float, Float>
{
    private OnCallbackListener mListener;
    private Context mContext;
    private float mPoints = 0.0f;

    public UserPointsLoader(Context context)
    {
        mContext = context;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        if (mListener != null)
            mListener.onPreExecute();
    }

    @Override
    protected Float doInBackground(Void... params)
    {
        SQLiteDatabase db =
            new Helper(mContext).getReadableDatabase();
        List<UserPoints> userPoints =
            new UserPoints().find(db);
        for (UserPoints item : userPoints)
        {
            item.getPoints().fetch(db);
            Float points =
                item.getPoints().getAmount();
            mPoints += points;
            publishProgress(points);
        }

        return mPoints;
    }

    @Override
    protected void onProgressUpdate(Float... values)
    {
        super.onProgressUpdate(values);
        if (mListener != null)
            mListener.onProgressUpdate(values[0]);
    }

    @Override
    protected void onPostExecute(Float result)
    {
        super.onPostExecute(result);
        if (mListener != null)
            mListener.onPostExecute(result);
    }

    public void setListener(OnCallbackListener listener)
    {
        mListener = listener;
    }

    public interface OnCallbackListener
    {
        void onPreExecute();
        void onProgressUpdate(float progress);
        void onPostExecute(float result);
    }
}
