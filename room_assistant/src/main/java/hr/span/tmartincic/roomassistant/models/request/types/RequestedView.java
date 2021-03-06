package hr.span.tmartincic.roomassistant.models.request.types;

import org.ksoap2.serialization.PropertyInfo;

import hr.span.tmartincic.roomassistant.DateUtils;
import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;

public class RequestedView implements TypeProperty, PrimitiveProperty
{
    public Values value;

    public RequestedView(Values value)
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
        pi.setValue(this.value.name());
        return pi;
    }

    public enum Values
    {
        None,
        MergedOnly,
        FreeBusy,
        FreeBusyMerged,
        Detailed,
        DetailedMerged
    }

    public String name()
    {
        return this.getClass().getSimpleName();
    }
}
