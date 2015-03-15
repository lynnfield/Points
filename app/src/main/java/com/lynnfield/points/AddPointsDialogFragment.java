package com.lynnfield.points;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.lynnfield.points.db.Helper;
import com.lynnfield.points.db.models.Event;
import com.lynnfield.points.db.models.IEvent;
import com.lynnfield.points.db.models.IPoints;
import com.lynnfield.points.db.models.Points;
import com.lynnfield.points.util.EventsAdapter;

import java.lang.reflect.Member;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPointsDialogFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPointsDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPointsDialogFragment
    extends DialogFragment implements AdapterView.OnItemClickListener {
    private OnFragmentInteractionListener mListener;
    private AutoCompleteTextView mEventView;
    private TextView mPointsView;
    private EventsAdapter mAdapter;
    private IEvent mEvent = new Event();
    private IPoints mPoints = new Points();
    private Helper mHelper;

    public static AddPointsDialogFragment newInstance() {
        return new AddPointsDialogFragment();
    }

    public AddPointsDialogFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mAdapter = new EventsAdapter(getActivity());
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder =
            new AlertDialog.Builder(getActivity());
        View view =
            LayoutInflater
                .from(getActivity())
                .inflate(R.layout.fragment_add_points_dialog, null);
        builder
            .setView(view)
            .setPositiveButton(
                R.string.add,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        String event =
                            mEventView.getText().toString();
                        String points =
                            mPointsView.getText().toString();
                        if (event.isEmpty())
                        {
                            Toast
                                .makeText(
                                    getActivity(),
                                    getActivity()
                                        .getString(R.string.event_is_empty),
                                    Toast.LENGTH_SHORT)
                                .show();
                            return;
                        }
                        if (points.isEmpty())
                        {
                            Toast
                                .makeText(
                                    getActivity(),
                                    getActivity()
                                        .getString(R.string.points_is_empty),
                                    Toast.LENGTH_SHORT)
                                .show();
                            return;
                        }
                        boolean eventExists =
                            mEvent.getId() != null;
                        boolean eventIsDirty =
                            !event.equals(
                                mEvent.getName());
                        boolean pointsExists =
                            mPoints.getId() != null;
                        boolean pointsIsDirty =
                            pointsExists
                            && !points.equals(
                                mPoints.getAmount().toString());
                        if(!eventExists || eventIsDirty)
                            addEvent();
                        else if (!pointsExists || pointsIsDirty)
                            addPoints();
                        mListener.onEventAdded(mEvent);
                    }
                })
            .setNegativeButton(
                R.string.cancel,
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        AddPointsDialogFragment.this
                            .getDialog()
                            .cancel();
                    }
                });
        mEventView =
            (AutoCompleteTextView)view.findViewById(R.id.event);
        mPointsView =
            (TextView)view.findViewById(R.id.points);
        mEventView.setAdapter(mAdapter);
        mEventView.setOnItemClickListener(this);
        return builder.create();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try
        {
            mListener = (OnFragmentInteractionListener) activity;
        }
        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach()
    {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(
        AdapterView<?> parent,
        View view,
        int position,
        long id)
    {
        mEvent =
            (IEvent) mAdapter
                .getItem(position);
        mEvent
            .getPoints()
            .fetch(
                getHelper()
                    .getReadableDatabase());
        mPoints =
            mEvent.getPoints();
        mEventView
            .setText(
                    mEvent
                            .getName());
        mPointsView
            .setText(
                mPoints
                    .getAmount()
                    .toString());
    }

    private Helper getHelper()
    {
        if (mHelper == null)
            mHelper = new Helper(getActivity());
        return mHelper;
    }

    private void addEvent()
    {
        SQLiteDatabase db =
            getHelper().getWritableDatabase();
        String event =
            mEventView
                .getText()
                .toString();
        Float points =
            Float.valueOf(
                mPointsView
                    .getText()
                    .toString());
        mEvent
            .setName(event);
        mEvent
            .setPoints(mPoints);
        mPoints
            .setAmount(points);
        mPoints
            .setDate(new Date());
        mPoints
            .setEvent(mEvent);
        mEvent
            .save(db);
        mPoints
            .save(db);
        mEvent
            .save(db);
    }

    private void addPoints()
    {
        SQLiteDatabase db =
                getHelper().getWritableDatabase();
        Float points =
            Float.valueOf(
                mPointsView
                    .getText()
                    .toString());
        mPoints
            .setId(null);
        mPoints
            .setAmount(points);
        mPoints
            .setDate(new Date());
        mPoints
            .setEvent(mEvent);
        mPoints
            .save(db);
        mEvent
            .setPoints(mPoints);
        mEvent
            .save(db);
    }

    public interface OnFragmentInteractionListener
    {
        void onEventAdded(IEvent event);
    }
}
