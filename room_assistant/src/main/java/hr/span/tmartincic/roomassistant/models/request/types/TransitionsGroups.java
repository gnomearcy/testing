package hr.span.tmartincic.roomassistant.models.request.types;

import java.util.ArrayList;
import java.util.List;

import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

public class TransitionsGroups extends PropertyOwner implements TypeProperty
{
//    private static final String transitionGroupId0 = "0";

    //WSDL type name: ArrayOfTransitionsGroupsType
//    public List<Property> properties;

    public TransitionsGroups(TransitionsGroup...groups)
    {
//        this.properties = new ArrayList<>();
        for(TransitionsGroup group : groups)
        {
            this.properties.add(group);
        }

//        TransitionsGroup transitionsGroup = new TransitionsGroup(transitionGroupId0);
//        properties.add(transitionsGroup);
    }

//    @Override
//    public int getPropertyCount()
//    {
//        return properties.size();
//    }
//
//    @Override
//    public Property getProperty(int position)
//    {
//        return properties.get(position);
//    }
//
//    @Override
//    public void addProperty(Property property)
//    {
//        this.properties.add(property);
//    }

    public String name()
    {
        return this.getClass().getSimpleName();
    }
}
