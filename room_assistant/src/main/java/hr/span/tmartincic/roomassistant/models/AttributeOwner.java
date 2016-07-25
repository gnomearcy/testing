package hr.span.tmartincic.roomassistant.models;

import java.util.ArrayList;
import java.util.List;

public class AttributeOwner
{
    public List<Attribute> attributes;

    public AttributeOwner()
    {
        this.attributes = new ArrayList<>();
    }

    public int getAttributeCount()
    {
        return this.attributes.size();
    }
    public Attribute getAttribute(int position)
    {
        if(position > getAttributeCount())
            throw new IndexOutOfBoundsException("Requested index out of bounds.");
        return this.attributes.get(position);
    }
    public void addAttribute(Attribute attribute)
    {
        if(attribute == null)
            throw new NullPointerException("Attempt to add null attribute to collection.");
        this.attributes.add(attribute);
    }
}
