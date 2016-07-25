package hr.span.tmartincic.roomassistant.models.request.types;

import org.ksoap2.serialization.PropertyInfo;

import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;

//WSDL type "duration"
//explanation source: http://www.w3schools.com/schema/schema_dtypes_date.asp
public class TimeOffset implements TypeProperty, PrimitiveProperty
{
    public String value;

    public TimeOffset(String value)
    {
        this.value = value;
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
