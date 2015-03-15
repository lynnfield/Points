package com.lynnfield.points.db.models;

import java.util.Date;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public interface IEventPointsLog
    extends IRecord
{
    IEvent getEvent();
    void setEvent(IEvent event);

    Integer getPoints();
    void setPoints(Integer points);

    Date getDate();
    void setDate(Date date);
}
