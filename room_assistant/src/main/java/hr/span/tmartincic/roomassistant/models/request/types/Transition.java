package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

public class Transition extends PropertyOwner implements TypeProperty
{
    public Transition(To to)
    {
        if(to == null)
            throw new NullPointerException("To is a required field. Must not be null.");

        this.properties.add(to);
    }
}
