package com.lynnfield.points;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lynnfield.points.db.Helper;
import com.lynnfield.points.db.models.IEvent;
import com.lynnfield.points.db.models.IUserPoints;
import com.lynnfield.points.db.models.UserPoints;
import com.lynnfield.points.util.UserPointsLoader;

import java.util.Date;
import java.util.List;

/**
 * Created by Genovich V.V. on 10.03.2015.
 */
public class PointsActivityExt
    extends Activity
    implements View.OnClickListener,
        AddPointsDialogFragment.OnFragmentInteractionListener,
        UserPointsLoader.OnCallbackListener
{
    private AddPointsDialogFragment mAddPoints;
    private TextView mPointsView;
    private ProgressBar mProgressBar;
    private float mPoints = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        mAddPoints =
            AddPointsDialogFragment.newInstance();
        mPointsView =
            (TextView) findViewById(R.id.total_points);
        mProgressBar =
                (ProgressBar) findViewById(R.id.points_loading);
        ((Button)findViewById(R.id.add_points))
            .setOnClickListener(this);
        UserPointsLoader loader =
            new UserPointsLoader(this);
        loader
            .setListener(this);
        loader
            .execute();
        refreshPoints();
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.add_points)
        {
            getFragmentManager()
                .beginTransaction()
                .add(mAddPoints, mAddPoints.getClass().getName())
                .commit();
        }
    }

    @Override
    public void onEventAdded(IEvent event)
    {
        IUserPoints userPoints =
            new UserPoints();
        userPoints
            .setPoints(event.getPoints());
        userPoints
            .setDate(new Date());
        userPoints
            .save(new Helper(this)
                    .getWritableDatabase());
        mPoints +=
            event.getPoints().getAmount();
        refreshPoints();
    }

    private void refreshPoints()
    {
        mPointsView.setText(
            String.format(
                getString(R.string.points_template),
                mPoints));
    }

    @Override
    public void onPreExecute()
    {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onProgressUpdate(float progress) {}

    @Override
    public void onPostExecute(float result)
    {
        mPoints = result;
        refreshPoints();
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
