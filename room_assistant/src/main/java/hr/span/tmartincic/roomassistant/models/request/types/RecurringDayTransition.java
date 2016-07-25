package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

//WSDL type name: RecurringDayTransition (type: RecurringDayTransitionType)
//RecurringDayTransitionType (base: RecurringTimeTransitionType) provides DayOfWeek, Occurence elements
//RecurringTimeTransitionType (base: TransitionType) provides TimeOffset, Month elements
//TransitionType provides To element

//This class wraps this all together
public class RecurringDayTransition extends PropertyOwner implements TypeProperty
{
//    private static final String timeOffsetValue1 = "P0DT2H0M0.0S";
//    private static final String timeOffsetValue2 = "P0DT3H0M0.0S";
//
//    private static final String toValue1 = "Dlt/1";
//    private static final String toValue2 = "Std";
//
//    private static final int monthValue1 = 3;
//    private static final int monthValue2 = 10;

    public RecurringDayTransition(To to, TimeOffset timeOffset, Month month, DayOfWeek dayOfWeek, Occurrence occurrence)
    {
        this.properties.add(to);
        this.properties.add(timeOffset);
        this.properties.add(month);
        this.properties.add(dayOfWeek);
        this.properties.add(occurrence);
    }

    public String name()
    {
        return this.getClass().getSimpleName();
    }
}
