package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.models.Attribute;
import hr.span.tmartincic.roomassistant.models.AttributeOwner;
import hr.span.tmartincic.roomassistant.models.Owner;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeId;

public class TransitionsGroup extends Owner implements TypeProperty
{
    public TransitionsGroup(AttributeId attributeId, RecurringDayTransition...recurringDayTransitions)
    {
        if(attributeId == null)
            throw new NullPointerException("AttributeId is a required field. Must not be null.");

        if(recurringDayTransitions == null)
            throw new NullPointerException("RecurringDayTransition is a required field. Must not be null.");

        this.addAttribute(attributeId);

        for(RecurringDayTransition rdt : recurringDayTransitions)
        {
            this.addProperty(rdt);
        }
    }
}
