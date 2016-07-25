package hr.span.tmartincic.ews;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.AttributeInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.kxml2.kdom.Element;

import java.io.IOException;

//target code
/*<?xml version="1.0" encoding="utf-8"?>
<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:m="http://schemas.microsoft.com/exchange/services/2006/messages" xmlns:t="http://schemas.microsoft.com/exchange/services/2006/types" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
  <soap:Header>
    <t:RequestServerVersion Version="Exchange2013_SP1" />
  </soap:Header>
  <soap:Body>
    <m:FindItem Traversal="Shallow">
      <m:ItemShape>
        <t:BaseShape>AllProperties</t:BaseShape>
      </m:ItemShape>
      <m:CalendarView StartDate="2014-12-31T23:00:00.000Z" EndDate="2015-06-15T13:47:48.905Z" />
      <m:ParentFolderIds>
        <t:DistinguishedFolderId Id="calendar" />
      </m:ParentFolderIds>
    </m:FindItem>
  </soap:Body>
</soap:Envelope>*/

public class FindItemSoapRequest
{
//    private static final String tag = "ksoap2";
//    private static final String SOAP_ACTION = "https://eagle03.span.bay:444/EWS/Exchange.asmx/FindItem";
//    private static final String METHOD_NAME = "FindItem";
//    private static final String NAMESPACE = "https://eagle03.span.bay:444/EWS/";
//    private static final String URL = "https://eagle03.span.bay:444/EWS/Exchange.asmx";

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
    private static final String PREFIX_NAMESPACE_MESSAGE  = ":m";
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

    public static String getFindItemRequestObject()
    {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = false;
        envelope.implicitTypes = true;
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

        XTransport xTrans = new XTransport();
        return xTrans.getSoapRequestAsXml(envelope);
//        return new SOAPRequest(xTrans.getSoapRequestAsXml(envelope), ServiceInfo.serviceUrl);
    }
}
