package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.Constants;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

//WSDL type name: MailboxData
public class MailboxData extends PropertyOwner implements TypeProperty
{
    public MailboxData(Email email, AttendeeType attendeeType, ExcludeConflicts excludeConflicts)
    {
        //TODO add null check for email and attendeeType - DONE
        if(email == null)
            throw new NullPointerException("Email is a required field. Must not be null.");
        if(attendeeType == null)
            throw new NullPointerException("AttendeeType is a required field. Must not be null.");

        this.properties.add(email);
        this.properties.add(attendeeType);
        this.properties.add(excludeConflicts);
    }
}
