package hr.span.tmartincic.roomassistant.models.request.types;

import org.ksoap2.serialization.PropertyInfo;

import hr.span.tmartincic.roomassistant.models.AttributeOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeKind;


public class To extends AttributeOwner implements TypeProperty, PrimitiveProperty
{
    public String value;

    public To(AttributeKind attributeKind, String value)
    {
        //TODO is attributeKind a required attribute for To property?
        if(attributeKind == null)
            throw new NullPointerException("AttributeId is a required field. Must not be null.");
        this.value = value;
        attributes.add(attributeKind);
    }

    @Override
    public PropertyInfo getInfo()
    {
        if(value == null)
            throw new NullPointerException("Primitive " + this.getClass().getSimpleName() + " requires a non-null value.");
        PropertyInfo pi = new PropertyInfo();
        pi.setValue(this.value);
        pi.setName(this.getClass().getSimpleName());

        return pi;
    }
}
