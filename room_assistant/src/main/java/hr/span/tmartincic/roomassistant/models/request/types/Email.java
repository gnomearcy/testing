package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.Constants;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

//WSDL type name: EmailAddress
public class Email extends PropertyOwner implements TypeProperty
{
    public Email(Address address, Name name, RoutingType routingType)
    {
        //TODO add null check for address
        if(address == null)
        {
            throw new NullPointerException();
        }

        this.properties.add(address);

        if(name != null)
            this.properties.add(name);
        if(routingType != null)
            this.properties.add(routingType);
    }
}
