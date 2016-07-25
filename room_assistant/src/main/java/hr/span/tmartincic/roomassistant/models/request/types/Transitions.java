package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

public class Transitions extends PropertyOwner implements TypeProperty
{
    //TODO Transitions <xs:attribute name="Id" type="xs:string"/> - add support for this, currently not needed
//    public List<Property> properties;

    public Transitions(Transition transition)
    {
        if(transition == null)
            throw new NullPointerException("Transition is a required field. Must not be null.");

//        this.properties = new ArrayList<>();
        properties.add(transition);
    }

    public String name()
    {
        return this.getClass().getSimpleName();
    }

//    @Override
//    public int getPropertyCount()
//    {
//        return this.properties.size();
//    }
//
//    @Override
//    public Property getProperty(int position)
//    {
//        return this.properties.get(position);
//    }
//
//    @Override
//    public void addProperty(Property property)
//    {
//        this.properties.add(property);
//    }
}
