package hr.span.tmartincic.roomassistant.models.request.attributes;

import hr.span.tmartincic.roomassistant.StringUtils;
import hr.span.tmartincic.roomassistant.models.Attribute;
import hr.span.tmartincic.roomassistant.models.request.enums.ExchangeVersion;

public class AttributeVersion extends Attribute
{
    public AttributeVersion(String value)
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
}
