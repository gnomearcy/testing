package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.Constants;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.RootProperty;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

public class TimeZoneContext extends PropertyOwner implements TypeProperty, RootProperty
{
    public TimeZoneContext(TimeZoneDefinition timeZoneDefinition)
    {
        if(timeZoneDefinition == null)
            throw new NullPointerException("TimeZoneDefinition is a required field. Must not be null.");

        this.properties.add(timeZoneDefinition);
    }

    public String name()
    {
        return this.getClass().getSimpleName();
    }
}
