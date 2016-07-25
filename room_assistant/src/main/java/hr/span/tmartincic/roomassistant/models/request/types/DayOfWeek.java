package hr.span.tmartincic.roomassistant.models.request.types;

import org.ksoap2.serialization.PropertyInfo;

import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;

public class DayOfWeek implements TypeProperty, PrimitiveProperty
{
    public Values value;

    public DayOfWeek(Values value)
    {
        this.value = value;
    }

    @Override
    public PropertyInfo getInfo()
    {
        if(value == null)
            throw new NullPointerException("Primitive " + this.getClass().getSimpleName() + " requires a non-null value.");
        PropertyInfo pi = new PropertyInfo();
        pi.setValue(this.value.name());
        pi.setName(this.getClass().getSimpleName());

        return pi;
    }

    //WSDL type name: DayOfWeekType
    public enum Values
    {
        Monday,
        Tuesday,
        Wednesday,
        Thursday,
        Friday,
        Saturday,
        Sunday,
        Day,
        Weekday,
        WeekendDay
    }
}
