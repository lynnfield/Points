package com.lynnfield.points.db.models;

/**
 * Created by Genovich V.V. on 06.03.2015.
 */
public interface IEvent
        extends IRecord
{
    String getName();
    void setName(String name);

    IPoints getPoints();
    void setPoints(IPoints points);
}
