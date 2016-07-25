package hr.span.tmartincic.roomassistant.models.request.messages;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.models.MessageProperty;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.RootProperty;
import hr.span.tmartincic.roomassistant.models.request.types.FreeBusyViewOptions;

public class GetUserAvailabilityRequest extends PropertyOwner implements MessageProperty, RootProperty
{
    public GetUserAvailabilityRequest(MailboxDataArray mailboxDataArray, FreeBusyViewOptions freeBusyViewOptions)
    {
        if(mailboxDataArray == null)
            throw new NullPointerException("MailboxDataArray is a required field. Must not be null.");

        if(freeBusyViewOptions == null)
            throw new NullPointerException("FreeBusyViewOptions is a required field. Must not be null.");

        this.properties.add(mailboxDataArray);
        this.properties.add(freeBusyViewOptions);
    }

    public String name()
    {
        return this.getClass().getSimpleName();
    }

}
