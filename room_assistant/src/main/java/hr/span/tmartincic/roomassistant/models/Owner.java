package hr.span.tmartincic.roomassistant.models;

import java.util.List;

public class Owner
{
    private AttributeOwner attributeOwner;
    private PropertyOwner propertyOwner;

    public Owner()
    {
        this.attributeOwner = new AttributeOwner();
        this.propertyOwner = new PropertyOwner();
    }

    public List<Attribute> getAttributes()
    {
        return this.attributeOwner.attributes;
    }

    public List<Property> getProperties()
    {
        return this.propertyOwner.properties;
    }

    public int getAttributeCount()
    {
        return this.attributeOwner.getAttributeCount();
    }

    public int getPropertyCount()
    {
        return this.propertyOwner.getPropertyCount();
    }

    public void addAttribute(Attribute attribute)
    {
        this.attributeOwner.addAttribute(attribute);
    }

    public void addProperty(Property property)
    {
        this.propertyOwner.addProperty(property);
    }

    public Attribute getAttribute(int position)
    {
        return this.attributeOwner.getAttribute(position);
    }

    public Property getProperty(int position)
    {
        return this.propertyOwner.getProperty(position);
    }
}
