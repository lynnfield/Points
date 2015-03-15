package com.lynnfield.points.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lynnfield.points.db.Helper;
import com.lynnfield.points.db.models.Event;
import com.lynnfield.points.db.models.IEvent;
import com.lynnfield.points.db.models.IRecord;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Genovich V.V. on 11.03.2015.
 */
public final class EventsAdapter
    extends BaseAdapter
    implements Filterable
{
    private Context mContext;
    private List<IEvent> mEvents = new LinkedList<>();

    public EventsAdapter(Context mContext)
    {
        this.mContext = mContext;
    }

    @Override
    public int getCount()
    {
        return mEvents.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mEvents.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return mEvents.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView view;
        if (convertView == null)
        {
            convertView =
                LayoutInflater
                    .from(mContext)
                    .inflate(android.R.layout.simple_list_item_1, null);
            view =
                (TextView) convertView
                    .findViewById(android.R.id.text1);
            convertView.setTag(view);
        }
        else
        {
            view =
                (TextView) convertView.getTag();
        }
        IEvent event =
            (IEvent) getItem(position);
        view.setText(
            event.getName());
        return convertView;
    }

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(
                CharSequence constraint)
            {
                FilterResults ret =
                    new FilterResults();
                IEvent event = new Event();
                event.setName(constraint.toString());
                List<? extends IRecord> list =
                    event.find(
                            new Helper(mContext)
                                    .getReadableDatabase());
                ret.count = list.size();
                ret.values = list;
                return ret;
            }

            @Override
            protected void publishResults(
                CharSequence constraint,
                FilterResults results)
            {
                mEvents =
                    (List<IEvent>) results.values;
                if (mEvents == null)
                    mEvents = new LinkedList<>();
                if (results.count > 0)
                    notifyDataSetChanged();
                else
                    notifyDataSetInvalidated();
            }
        };
    }
}
