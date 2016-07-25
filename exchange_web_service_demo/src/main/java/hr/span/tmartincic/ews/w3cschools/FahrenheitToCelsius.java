package hr.span.tmartincic.ews.w3cschools;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;
import java.util.Vector;

public class FahrenheitToCelsius extends Vector<Fahrenheit> implements KvmSerializable
{
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
        this.add((Fahrenheit)o);
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo)
    {
        propertyInfo.name = Fahrenheit.class.getSimpleName();
        propertyInfo.type = Fahrenheit.class;
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
