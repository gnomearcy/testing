package hr.span.tmartincic.roomassistant.models.request.attributes;

import hr.span.tmartincic.roomassistant.StringUtils;
import hr.span.tmartincic.roomassistant.models.Attribute;

public class AttributeKind extends Attribute
{
    public AttributeKind(String value)
    {
        super.value = value;
    }

    @Override
    public String label()
    {
        try{
            return StringUtils.strip(this.getClass().getSimpleName());
        }catch (InvalidAttributeNameFormatException e){
            e.printStackTrace();
        }
        return null;
    }

    //WSDL type name: TransitionTargetKindType
    public enum Values
    {
        Period,
        Group
    }
}
