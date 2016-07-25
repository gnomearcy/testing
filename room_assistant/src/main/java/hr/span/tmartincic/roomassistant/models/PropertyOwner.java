package hr.span.tmartincic.roomassistant.models;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.models.Attribute;
import hr.span.tmartincic.roomassistant.models.Property;

public class PropertyOwner
{
    public List<Property> properties;

    public PropertyOwner()
    {
        this.properties = new ArrayList<>();
    }
    public int getPropertyCount()
    {
        return this.properties.size();
    }

    public Property getProperty(int position)
    {
        if(position > getPropertyCount())
            throw new IndexOutOfBoundsException("Requested index out of bounds.");
        return this.properties.get(position);
    }
    public void addProperty(Property attribute)
    {
        if(attribute == null)
            throw new NullPointerException("Attempt to add null property to collection.");
        this.properties.add(attribute);
    }
}
