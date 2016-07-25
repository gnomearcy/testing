package hr.span.tmartincic.ews.w3cschools;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Vector;

public class FahrenheitToCelsiusResponse extends Vector<FahrenheitToCelsiusResult> implements KvmSerializable
{
    private static final long serialVersionUID = -1166006770093411055L;

    @Override
    public Object getProperty(int i)
    {
        return this.get(i);
    }

    @Override
    public int getPropertyCount()
    {
        return 1;
    }

    @Override
    public void setProperty(int i, Object o)
    {
        this.add((FahrenheitToCelsiusResult)o);
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo)
    {
        propertyInfo.name = FahrenheitToCelsiusResult.class.getSimpleName();
        propertyInfo.type = PropertyInfo.OBJECT_CLASS;
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
