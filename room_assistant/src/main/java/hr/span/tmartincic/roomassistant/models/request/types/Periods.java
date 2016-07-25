package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.Constants;
import hr.span.tmartincic.roomassistant.models.EmptyArrayException;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

public class Periods extends PropertyOwner implements TypeProperty
{
    public Periods(Period...periods)
    {
        //TODO it can be empty it seems
//        if(periods.length == 0)
//            throw new EmptyArrayException("Passed in array of Period objects is empty.");

        for(Period period : periods)
        {
            this.properties.add(period);
        }
    }
}
