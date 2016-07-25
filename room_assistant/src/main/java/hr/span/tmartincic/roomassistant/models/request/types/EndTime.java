package hr.span.tmartincic.roomassistant.models.request.types;

import org.joda.time.DateTime;
import org.ksoap2.serialization.PropertyInfo;

import hr.span.tmartincic.roomassistant.DateUtils;
import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;

public class EndTime implements TypeProperty, PrimitiveProperty
{
    public DateTime value;

    public EndTime(DateTime value)
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
        pi.setValue(DateUtils.jodaToString(this.value));
        return pi;
    }
}
