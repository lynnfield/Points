package com.lynnfield.points.db.models;

import java.util.Date;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public interface IPoints
    extends IRecord
{
    IEvent getEvent();
    void setEvent(IEvent event);

    Float getAmount();
    void setAmount(Float points);

    Date getDate();
    void setDate(Date date);
}
