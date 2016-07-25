package hr.span.tmartincic.roomassistant.models.request.types;

import org.ksoap2.serialization.PropertyInfo;

import hr.span.tmartincic.roomassistant.models.AttributeOwner;
import hr.span.tmartincic.roomassistant.models.RootProperty;
import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeVersion;

public class RequestServerVersion extends AttributeOwner
        implements TypeProperty, RootProperty, PrimitiveProperty
{
    public RequestServerVersion(AttributeVersion attributeVersion)
    {
        if(attributeVersion == null)
            throw new NullPointerException("AttributeVersion is a required field. Must not be null.");

        attributes.add(attributeVersion);
    }

    @Override
    public PropertyInfo getInfo()
    {
        return null;
    }
}
