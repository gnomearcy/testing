package hr.span.tmartincic.roomassistant.models.request.types;


import org.ksoap2.serialization.PropertyInfo;

import hr.span.tmartincic.roomassistant.models.AttributeOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeBias;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeId;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeName;

//WSDL type name: PeriodType
public class Period extends AttributeOwner implements TypeProperty, PrimitiveProperty
{
    //TODO refactor
//    public static final String attributeNameValue1 = "Standard";
//    public static final String attributeNameValue2 = "Daylight";
//    public static final String attributeIdValue1 = "Std";
//    public static final String attributeIdValue2 = "Dlt/1";
//    public static final String attributeBiasValue1 = "-P0DT1H0M0.0S";
//    public static final String attributeBiasValue2 = "-P0DT2H0M0.0S";

    public Period(AttributeBias attributeBias, AttributeName attributeName, AttributeId attributeId)
    {
        if(attributeBias == null)
            throw new NullPointerException("AttributeBias is a required field. Must not be null.");
        if(attributeName == null)
            throw new NullPointerException("AttributeName is a required field. Must not be null.");
        if(attributeId == null)
            throw new NullPointerException("attributeId is a required field. Must not be null.");

        this.attributes.add(attributeBias);
        this.attributes.add(attributeName);
        this.attributes.add(attributeId);
    }

    @Override
    public PropertyInfo getInfo()
    {
        return null;
    }
}
