package hr.span.tmartincic.ews;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
//import org.ksoap2.transport.NtlmTransport;
import hr.span.tmartincic.ews.ntlmauth.CalendarItem;
import hr.span.tmartincic.ews.ntlmauth.NtlmTransport;
import hr.span.tmartincic.ews.ntlmauth.ResponseMessages;

import org.kxml2.kdom.Element;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class TestingksoapNTLMtransport extends ActionBarActivity
{
    private static final String tag = "ksoap2";
    private static final String targetNamespace = "http://schemas.microsoft.com/exchange/services/2006/messages";
    private static final String forwardSlash = "/";
    private static final String operationName = "FindItem";
    private static final String soapAction = targetNamespace + forwardSlash + operationName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testingksoap_ntlmtransport);

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
                NtlmTransport transport = new NtlmTransport();
                transport.debug = true;
//                transport.setCredentials(ServiceInfo.serviceUrl, ServiceInfo.username, ServiceInfo.password, "", "");

                FindItemSoapRequest findItem = new FindItemSoapRequest();
                SoapSerializationEnvelope envelope = findItem.getFindItemRequestObject();
                envelope.dotNet = true;

//                envelope.addMapping(targetNamespace, "ResponseMessages", new ResponseMessages().getClass());

                try
                {
                    //Template is a operation xml element wrapper of the body message
//                    envelope.addTemplate();

                    transport.call(soapAction, envelope, null);
                    Log.d(tag, "Request dump - " + transport.requestDump);
                    Log.d(tag, "Response dump - " + transport.responseDump);
                    SoapObject bodyIN = (SoapObject) envelope.bodyIn;
                    Log.d(tag, "Body In - " + bodyIN);
                    SoapObject result = parse(bodyIN);
                    Log.d(tag, "Count - " + result.getPropertyCount());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                catch (XmlPullParserException e)
                {
                    e.printStackTrace();
                }

                return null;
            }
        }.execute();

    }

    private SoapObject parse(SoapObject in)
    {
        String targetName = "CalendarItem";
        try{
            //TODO check if the recursively passed in object has different hash code.

            int hash = in.hashCode();
            return (SoapObject) in.getProperty(targetName);
        }
        catch(Exception e)
        {
//            String error = e.getMessage();
            //not found
            int count = in.getPropertyCount();
            for(int i = 0; i < count; i++)
            {
                Object temp = in.getProperty(i);
                if(!temp.getClass().equals(SoapPrimitive.class))
                {
                    //deep copy the soap object and call the function with new object
                    SoapObject toRecurse = new SoapObject();
//                    for(int j = 0; j < ((SoapObject)temp).getPropertyCount(); j++)
//                    {
//                        Object property = ((SoapObject)temp).getProperty(j);
//
//                        if(property.getClass().equals(SoapPrimitive.class))
//                        {
//                            //if we are soap primitive, copy the name and value and add to toRecurse object
//                            toRecurse.addProperty(((SoapPrimitive)property).getName(), ((SoapPrimitive)property).getValue());
//                        }
//                        else
//                        {
//                            toRecurse.addProperty(((SoapObject)property).getName(), property);
//                        }
//                    }

                    parse(toRecurse);
                }
            }
        }

        return null;
    }

    private SoapObject manualParse(SoapObject in)
    {
        SoapObject responsemsgs = (SoapObject) in.getProperty("ResponseMessages");
        SoapObject finditemresponsemsg = (SoapObject) responsemsgs.getProperty("FindItemResponseMessage");
        SoapPrimitive responseCode = (SoapPrimitive) finditemresponsemsg.getPrimitiveProperty("ResponseCode");
        Log.d(tag, "primitive response code value - " + responseCode.getValue());
        SoapObject RootFolder = (SoapObject) finditemresponsemsg.getProperty("RootFolder");
        Log.d(tag, "Root folder count: " + RootFolder.getPropertyCount());
        SoapObject Items = (SoapObject) RootFolder.getProperty("Items");
        Log.d(tag, "Items count: " + Items.getPropertyCount());
        SoapObject CalendarItem = (SoapObject) Items.getProperty("CalendarItem");
        Log.d(tag, "CalendarItem count - " + CalendarItem.getPropertyCount());
        return responsemsgs;
    }


    public class FindItemSoapRequest
    {
        private static final String HEADER_REQUEST_SERVER_VERSION = "RequestServerVersion";
        private static final String ATTRIBUTE_VERSION = "Version";
        private static final String EXCHANGE_SERVER_VERSION = "Exchange2013_SP1";

        private static final String tag = "ksoap2";
        private static final String SOAP_ACTION = "http://schemas.microsoft.com/exchange/services/2006/messages/FindItem";
        private static final String NAMESPACE = "https://eagle03.span.bay:444/EWS/Services.wsdl";
        private static final String METHOD_NAME = "FindItem";
        private static final String URL = "https://eagle03.span.bay:444/EWS/Services.wsdl";

        private static final String XMLNS_MESSAGES = "http://schemas.microsoft.com/exchange/services/2006/messages";
        private static final String XMLNS_TYPES = "http://schemas.microsoft.com/exchange/services/2006/types";
        private static final String XMLNS = "xmlns";
        private static final String PREFIX_ELEMENT_TYPE = "t:";
        private static final String PREFIX_ELEMENT_MESSAGE = "m:";
        private static final String PREFIX_NAMESPACE_TYPE = ":t";
        private static final String PREFIX_NAMESPACE_MESSAGE = ":m";
        private static final String EMPTY_STRING = "";

        private static final int NR_OF_HEADERS = 1;
        private static final String ATTRIBUTE_TRAVERSAL = "Traversal";
        private static final String ATTRIBUTE_VALUE_SHALLOW = "Shallow";

        //ItemShape constants
        private static final String ELEMENT_ITEM_SHAPE = "ItemShape";

        //BaseShape constants
        private static final String ELEMENT_BASE_SHAPE = "BaseShape";
        private static final String VALUE_BASE_SHAPE_ALLPROPERTIES = "AllProperties";

        //CalendarView constants
        private static final String ELEMENT_CALENDAR_VIEW = "CalendarView";
        private static final String ATTRIBUTE_CALENDAR_VIEW_START_DATE = "StartDate";
        private static final String ATTRIBUTE_CALENDAR_VIEW_START_DATE_VALUE = "2014-12-31T23:00:00.000Z";
        private static final String ATTRIBUTE_CALENDAR_VIEW_END_DATE = "EndDate";
        private static final String ATTRIBUTE_CALENDAR_VIEW_END_DATE_VALUE = "2015-06-15T13:47:48.905Z";

        //ParentFolderIds constants
        private static final String ELEMENT_PARENT_FOLDER_IDS = "ParentFolderIds";

        //DistinguishedFolderId constants
        private static final String ELEMENT_DISTINGUISHED_FOLDER_ID = "DistinguishedFolderId";
        private static final String ATTRIBUTE_DISTINGUISHED_FOLDER_ID_ID = "Id";
        private static final String ATTRIBUTE_DISTINGUISHED_FOLDER_ID_ID_VALUE = "calendar";

        public SoapSerializationEnvelope getFindItemRequestObject()
        {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = false;
//            envelope.implicitTypes = true;
            envelope.setAddAdornments(false);

        /* Header */
            Element[] headers = new Element[1];

            //RequestServerVersion
            Element h = new Element().createElement(XMLNS_TYPES, HEADER_REQUEST_SERVER_VERSION);
//        h.setName(HEADER_REQUEST_SERVER_VERSION);
//        h.setName(NAMESPACE);
//        h.setPrefix(PREFIX_ELEMENT_TYPE, XMLNS_TYPES);
            h.setAttribute(EMPTY_STRING, ATTRIBUTE_VERSION, EXCHANGE_SERVER_VERSION);
            headers[0] = h;
            envelope.headerOut = headers;

        /* Body */

            //FindItem
            SoapObject soFindItem = new SoapObject("", PREFIX_ELEMENT_MESSAGE + METHOD_NAME);
            soFindItem.addAttribute(XMLNS + PREFIX_NAMESPACE_MESSAGE, XMLNS_MESSAGES);
            soFindItem.addAttribute(XMLNS + PREFIX_NAMESPACE_TYPE, XMLNS_TYPES);

            //FindItem attribute Traversal="Shallow"
            AttributeInfo traversalAttribute = new AttributeInfo();
            traversalAttribute.setName(ATTRIBUTE_TRAVERSAL);
            traversalAttribute.setValue(ATTRIBUTE_VALUE_SHALLOW);
            traversalAttribute.setNamespace(EMPTY_STRING);
            soFindItem.addAttribute(traversalAttribute);

            //ItemShape
            SoapObject soItemShape = new SoapObject("", PREFIX_ELEMENT_MESSAGE + ELEMENT_ITEM_SHAPE);
            soItemShape.addProperty(PREFIX_ELEMENT_TYPE + ELEMENT_BASE_SHAPE, VALUE_BASE_SHAPE_ALLPROPERTIES);
            soFindItem.addSoapObject(soItemShape);

            //CalendarView
            SoapObject soCalendarView = new SoapObject(EMPTY_STRING, PREFIX_ELEMENT_MESSAGE + ELEMENT_CALENDAR_VIEW);
            soCalendarView.addAttribute(ATTRIBUTE_CALENDAR_VIEW_START_DATE, ATTRIBUTE_CALENDAR_VIEW_START_DATE_VALUE);
            soCalendarView.addAttribute(ATTRIBUTE_CALENDAR_VIEW_END_DATE, ATTRIBUTE_CALENDAR_VIEW_END_DATE_VALUE);
            soFindItem.addSoapObject(soCalendarView);

            //ParentFolderIds -> DistinguishedFolderId
            SoapObject soParentFolderIds = new SoapObject(EMPTY_STRING, PREFIX_ELEMENT_MESSAGE + ELEMENT_PARENT_FOLDER_IDS);
            SoapObject soDistinguishedFolderId = new SoapObject(EMPTY_STRING, PREFIX_ELEMENT_TYPE + ELEMENT_DISTINGUISHED_FOLDER_ID);
            soDistinguishedFolderId.addAttribute(ATTRIBUTE_DISTINGUISHED_FOLDER_ID_ID, ATTRIBUTE_DISTINGUISHED_FOLDER_ID_ID_VALUE);
            soParentFolderIds.addSoapObject(soDistinguishedFolderId);

            //add last item to operation element as children and insert into envelope
            soFindItem.addSoapObject(soParentFolderIds);
            envelope.setOutputSoapObject(soFindItem);

            return envelope;
        }
    }
}
