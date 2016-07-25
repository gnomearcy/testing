package hr.span.tmartincic.roomassistant.models.request.types;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.Constants;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

public class TimeWindow extends PropertyOwner implements TypeProperty
{
    public TimeWindow(StartTime startTime, EndTime endTime)
    {
        if(startTime == null)
            throw new NullPointerException("StartTime is a required field. Must not be null.");
        if(endTime == null)
            throw new NullPointerException("EndTime is a required field. Must not be null.");

        this.properties.add(startTime);
        this.properties.add(endTime);
    }
}
