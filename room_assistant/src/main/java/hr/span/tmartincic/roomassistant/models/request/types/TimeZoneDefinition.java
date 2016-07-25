package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.models.Attribute;
import hr.span.tmartincic.roomassistant.models.AttributeOwner;
import hr.span.tmartincic.roomassistant.models.Owner;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TimeZoneAbbreviation;
import hr.span.tmartincic.roomassistant.models.TypeProperty;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeId;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeName;

/**TODO
 * wsdl definition:
 *
 <xs:complexType name="TimeZoneDefinitionType">
     <xs:sequence minOccurs="0">
         <xs:element name="Periods" type="t:NonEmptyArrayOfPeriodsType"/>
         <xs:element name="TransitionsGroups" type="t:ArrayOfTransitionsGroupsType" minOccurs="0"/>
         <xs:element name="Transitions" type="t:ArrayOfTransitionsType" minOccurs="0"/>
     </xs:sequence>

 Is this type a property owner or not?
 * */


//WSDL type name: TimeZoneDefinitionType
public class TimeZoneDefinition extends Owner implements TypeProperty
{
    //TODO sequence of these 3 children - assuming one triplet
    public TimeZoneDefinition(AttributeName attributeName, AttributeId attributeId, Periods periods, TransitionsGroups transitionsGroups, Transitions transitions)
    {
        if(attributeName == null)
            throw new NullPointerException("AttributeName is a required field. Must not be null.");
        if(attributeId == null)
            throw new NullPointerException("AttributeId is a required field. Must not be null.");
        if(periods == null)
            throw new NullPointerException("Periods is a required field. Must not be null.");

        this.addAttribute(attributeName);
        this.addAttribute(attributeId);
        this.addProperty(periods);
        this.addProperty(transitionsGroups);
        this.addProperty(transitions);
    }
}
