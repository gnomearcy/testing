package hr.span.tmartincic.roomassistant.requests;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.kxml2.kdom.Element;

import hr.span.tmartincic.roomassistant.Constants;
import hr.span.tmartincic.roomassistant.models.Attribute;
import hr.span.tmartincic.roomassistant.models.AttributeOwner;
import hr.span.tmartincic.roomassistant.models.MessageProperty;
import hr.span.tmartincic.roomassistant.models.Owner;
import hr.span.tmartincic.roomassistant.models.Property;
import hr.span.tmartincic.roomassistant.models.PropertyOwner;
import hr.span.tmartincic.roomassistant.models.RootProperty;
import hr.span.tmartincic.roomassistant.models.PrimitiveProperty;
import hr.span.tmartincic.roomassistant.models.TypeProperty;

public class RequestUtils
{
    public static final String EMPTY_NAMESPACE = "";
    public static final String EMPTY_TEXT = "";

    public static Element[] parseHeader(Property...properties)
    {
        Element[] headers = new Element[properties.length];
        int index = 0;

        //traverse the given properties hierarchy and construct Element object recursively
        for(Property property : properties)
        {
            Element e = new Element();
            //if we are the root, add namespace declaration
            if(property instanceof RootProperty)
            {
                e.setNamespace(Constants.NAMESPACE_VALUE_TYPE);
                e.setName(property.getClass().getSimpleName());
                e.setPrefix(Constants.PREFIX_TYPE_1, Constants.NAMESPACE_VALUE_TYPE);
            }
            else
            {
                e.setNamespace(EMPTY_NAMESPACE);
                e.setName(Constants.PREFIX_TYPE_1 + ":" + property.getClass().getSimpleName());
            }

            //assumption - header elements are all types
//            if(property instanceof TypeProperty)
//            {
//                //t:RequestServerVersion
//                e.setName(Constants.PREFIX_TYPE_1 + property.getClass().getSimpleName());
//            }

            //handle attributes and properties
            if(property instanceof Owner)
            {
                //add all attributes to element
                for(Attribute attr : ((Owner)property).getAttributes())
                {
                    e.setAttribute(EMPTY_NAMESPACE, attr.label(), attr.value());
                }
                for(Property prop : ((Owner)property).getProperties())
                {
                    //batch properties
//                    Element[] tempHeaders = new Element[((Owner) property).getPropertyCount()];
                    Element[] tempHeaders = parseHeader(prop);

                    //single property at time
//                    Element[] tempHeaders = new Element[1];
//                    tempHeaders = parseHeader(prop);

                    for(Element elem : tempHeaders)
                    {
                        e.addChild(2, elem);
                    }
                }
            }
            else
            {
                if(property instanceof AttributeOwner)
                {
                    for(Attribute attr : ((AttributeOwner)property).attributes)
                    {
                        e.setAttribute(EMPTY_NAMESPACE, attr.label(), attr.value());
                    }
                }
                if(property instanceof PropertyOwner) //soap object
                {
//                    ((PropertyOwner)property)
                    for(Property prop : ((PropertyOwner)property).properties)
                    {
                        //idemo 1 po 1, ali moze i batch
//                        Element[] tempHeaders = new Element[((PropertyOwner)property).getPropertyCount()];
                        Element[] tempHeaders = parseHeader(prop);
//                        Element[] tempHeaders = new Element[1];
//                        tempHeaders = parseHeader(prop);

                        for(Element elem : tempHeaders)
                        {
                            e.addChild(2, elem);
                        }
                    }
                }
                else //soap primitive
                {
                    PropertyInfo primitiveInfo = ((PrimitiveProperty)property).getInfo();
                    if(primitiveInfo !=  null)
                    {
                        e.addChild(4, primitiveInfo.getValue());
                    }
                }
            }

            //add e to return array
            headers[index] = e;
            index++;
        }
        return headers;
    }

    public static Object parseBody(Property...properties)
    {
        SoapObject object = null;

        for(Property property : properties)
        {
            if(property instanceof RootProperty)
            {
                //Request root object is always a message - and is always a PropertyOwner
                object = new SoapObject(EMPTY_NAMESPACE, Constants.PREFIX_MESSAGE_2 + property.getClass().getSimpleName());
                object.addAttribute(Constants.NAMESPACE_PREFIX_MESSAGE, Constants.NAMESPACE_VALUE_MESSAGE);
                object.addAttribute(Constants.NAMESPACE_PREFIX_TYPE, Constants.NAMESPACE_VALUE_TYPE);
            }

            if(property instanceof PropertyOwner)
            {
                if(property instanceof MessageProperty && !(property instanceof RootProperty))
                {
                    object = new SoapObject(EMPTY_NAMESPACE, Constants.PREFIX_MESSAGE_2 + property.getClass().getSimpleName());
                }
                if(property instanceof TypeProperty && !(property instanceof RootProperty))
                {
                    object = new SoapObject(EMPTY_NAMESPACE, Constants.PREFIX_TYPE_2 + property.getClass().getSimpleName());
                }

                for(Property prop : ((PropertyOwner)property).properties)
                {
                    Object response = parseBody(prop);

                    if(response instanceof PropertyInfo)
                    {
                        object.addProperty((PropertyInfo) response);
                    }
                    if(response instanceof SoapObject)
                    {
                        object.addSoapObject((SoapObject) response);
                    }
                }
            }
            else //primitive
            {
                PropertyInfo primitiveInfo = ((PrimitiveProperty)property).getInfo();

                //should never happen, all request body primitives hold some information.
                //null checks are handled in request body primitive constructor bodies.
                if(primitiveInfo != null)
                {
                    PropertyInfo propertyToAdd = new PropertyInfo();

                    if(property instanceof TypeProperty)
                    {
                        propertyToAdd.name = Constants.PREFIX_TYPE_2 + primitiveInfo.getName();
                    }
                    //sanity check - should never happen - primitives cannot be messages
                    //if a primitive is accidentally declared as a MessageProperty - handle it there
                    if(property instanceof MessageProperty)
                    {
                        propertyToAdd.name = Constants.PREFIX_MESSAGE_2 + primitiveInfo.getName();
                    }

                    propertyToAdd.setValue(primitiveInfo.getValue());
                    return propertyToAdd;
                }
            }
        }

        return object;
    }
}
