package hr.span.tmartincic.ews.prepravljanje_ksoap2.response_models;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Vector;

import hr.span.tmartincic.ews.prepravljanje_ksoap2.response_models.FindItemResponseMessage;

public class ResponseMessages implements KvmSerializable
{
    public FindItemResponseMessage findItemResponseMessage;

    public ResponseMessages(){}
    public ResponseMessages(FindItemResponseMessage message)
    {
        this.findItemResponseMessage = message;
    }

    @Override
    public Object getProperty(int i)
    {
        switch(i)
        {
            case 0:
                return findItemResponseMessage;
        }
        return null;
    }

    @Override
    public int getPropertyCount()
    {
        return 1;
    }

    @Override
    public void setProperty(int i, Object o)
    {
        switch(i)
        {
            case 0:
                findItemResponseMessage = (FindItemResponseMessage) o;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo)
    {
        switch(i)
        {
            case 0:
                propertyInfo.type = FindItemResponseMessage.class;
                propertyInfo.name = FindItemResponseMessage.class.getSimpleName();
                break;
            default:
                break;
        }
    }

    @Override
    public String getInnerText()
    {
        return null;
    }

    @Override
    public void setInnerText(String s)
    {

    }
}
