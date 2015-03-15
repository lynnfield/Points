package com.lynnfield.points.db.models;

import java.util.Date;

/**
 * Created by Genovich V.V. on 11.03.2015.
 */
public interface IUserPoints
    extends IRecord
{
    IPoints getPoints();
    void setPoints(IPoints points);

    Date getDate();
    void setDate(Date date);
}
