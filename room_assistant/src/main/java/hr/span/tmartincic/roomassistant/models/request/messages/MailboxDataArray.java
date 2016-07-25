package hr.span.tmartincic.roomassistant.models.request.messages;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.Constants;
import hr.span.tmartincic.roomassistant.models.MessageProperty;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.request.types.MailboxData;

public class MailboxDataArray extends PropertyOwner implements MessageProperty
{
    public MailboxDataArray(MailboxData...mailboxDatas)
    {
        for(MailboxData mailboxData : mailboxDatas)
        {
            this.properties.add(mailboxData);
        }
    }
}
