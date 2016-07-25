package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.Constants;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

public class FreeBusyViewOptions extends PropertyOwner implements TypeProperty
{
    public FreeBusyViewOptions(TimeWindow timeWindow, MergedFreeBusyIntervalInMinutes mergedFreeBusyIntervalInMinutes, RequestedView requestedView)
    {
        if(timeWindow == null)
            throw new NullPointerException("TimeWindow is a required field. Must not be null.");

        this.properties.add(timeWindow);
        this.properties.add(mergedFreeBusyIntervalInMinutes);
        this.properties.add(requestedView);
    }
}
