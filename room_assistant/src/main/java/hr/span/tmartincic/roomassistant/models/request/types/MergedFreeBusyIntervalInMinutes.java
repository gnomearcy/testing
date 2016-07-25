package hr.span.tmartincic.roomassistant.models.request.types;

import org.ksoap2.serialization.PropertyInfo;

import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;

public class MergedFreeBusyIntervalInMinutes implements TypeProperty, PrimitiveProperty
{
    public Integer value;

    public MergedFreeBusyIntervalInMinutes(Integer value)
    {
        this.value = value;
    }

    @Override
    public PropertyInfo getInfo()
    {
        if(value == null)
            throw new NullPointerException("Primitive " + this.getClass().getSimpleName() + " requires a non-null value.");
        PropertyInfo pi = new PropertyInfo();
        pi.setName(this.getClass().getSimpleName());
        pi.setValue(String.valueOf(this.value));
        return pi;
    }
}
