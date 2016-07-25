package hr.span.tmartincic.roomassistant;

import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;

public class ExtendedEnvelope extends SoapSerializationEnvelope
{
    public ExtendedEnvelope(int version)
    {
        super(version);
    }

    @Override
    protected void writeProperty(XmlSerializer writer, Object obj, PropertyInfo type) throws IOException
    {
        if(obj != null && obj != SoapPrimitive.NullNilElement)
        {
            Object[] qName = this.getInfo((Object)null, obj);
            if(!type.multiRef && qName[2] == null) {
//                if(!this.implicitTypes || obj.getClass() != type.type) {
                if(!this.implicitTypes)
                {
                    String prefix1 = writer.getPrefix((String)qName[0], true);
                    writer.attribute(this.xsi, "type", prefix1 + ":" + qName[1]);
                }

                this.writeElement(writer, obj, type, qName[3]);
            }
        }
        else
        {
            writer.attribute(this.xsi, this.version >= 120?"nil":"null", "true");
        }
    }
}
