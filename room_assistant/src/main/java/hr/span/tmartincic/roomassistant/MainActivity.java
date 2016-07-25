package hr.span.tmartincic.roomassistant;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import org.joda.time.DateTime;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.kxml2.kdom.Element;

import hr.span.tmartincic.roomassistant.models.TimeZoneAbbreviation;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeBias;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeId;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeKind;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeName;
import hr.span.tmartincic.roomassistant.models.request.attributes.AttributeVersion;
import hr.span.tmartincic.roomassistant.models.request.enums.ExchangeVersion;
import hr.span.tmartincic.roomassistant.models.request.messages.GetUserAvailabilityRequest;
import hr.span.tmartincic.roomassistant.models.request.messages.MailboxDataArray;
import hr.span.tmartincic.roomassistant.models.request.types.Address;
import hr.span.tmartincic.roomassistant.models.request.types.AttendeeType;
import hr.span.tmartincic.roomassistant.models.request.types.DayOfWeek;
import hr.span.tmartincic.roomassistant.models.request.types.Email;
import hr.span.tmartincic.roomassistant.models.request.types.EndTime;
import hr.span.tmartincic.roomassistant.models.request.types.ExcludeConflicts;
import hr.span.tmartincic.roomassistant.models.request.types.FreeBusyViewOptions;
import hr.span.tmartincic.roomassistant.models.request.types.MailboxData;
import hr.span.tmartincic.roomassistant.models.request.types.MergedFreeBusyIntervalInMinutes;
import hr.span.tmartincic.roomassistant.models.request.types.Month;
import hr.span.tmartincic.roomassistant.models.request.types.Occurrence;
import hr.span.tmartincic.roomassistant.models.request.types.Period;
import hr.span.tmartincic.roomassistant.models.request.types.Periods;
import hr.span.tmartincic.roomassistant.models.request.types.RecurringDayTransition;
import hr.span.tmartincic.roomassistant.models.request.types.RequestServerVersion;
import hr.span.tmartincic.roomassistant.models.request.types.RequestedView;
import hr.span.tmartincic.roomassistant.models.request.types.StartTime;
import hr.span.tmartincic.roomassistant.models.request.types.TimeOffset;
import hr.span.tmartincic.roomassistant.models.request.types.TimeWindow;
import hr.span.tmartincic.roomassistant.models.request.types.TimeZoneContext;
import hr.span.tmartincic.roomassistant.models.request.types.TimeZoneDefinition;
import hr.span.tmartincic.roomassistant.models.request.types.To;
import hr.span.tmartincic.roomassistant.models.request.types.Transition;
import hr.span.tmartincic.roomassistant.models.request.types.Transitions;
import hr.span.tmartincic.roomassistant.models.request.types.TransitionsGroup;
import hr.span.tmartincic.roomassistant.models.request.types.TransitionsGroups;
import hr.span.tmartincic.roomassistant.requests.RequestGetUserAvailability;
import hr.span.tmartincic.roomassistant.requests.RequestUtils;
import hr.span.tmartincic.roomassistant.web.NtlmTransport;
import hr.span.tmartincic.roomassistant.web.SOAPClient;


public class MainActivity extends ActionBarActivity
{
    private static final String tag = "testtag";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Element[] elems = RequestUtils.parseHeader(getServerVersion(), getTimeZoneContext());

//        final ExtendedEnvelope envelope = new ExtendedEnvelope(SoapEnvelope.VER11);
//        final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

//        envelope.headerOut = elems;
//        envelope.implicitTypes = true;
//        envelope.setAddAdornments(false);
//        envelope.setOutputSoapObject(getBody());

        String emailaddress = "SastanciOsijek@span.eu";
        DateTime startTime = new DateTime(2015, 6, 1, 0, 0, 0);
        DateTime endTime = new DateTime(2015, 6, 17, 0, 0, 0);

        final ExtendedEnvelope envelope = RequestGetUserAvailability.create(emailaddress, startTime, endTime);

//        envelope.setOutputSoapObject((SoapObject)RequestUtils.parseBody(getGuar(emailaddress, startTime, endTime)));
//        DateTime now = DateTime.now();
//        String pattern = "yyyy-MM-dd'T'HH-mm-ss";
//        DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
//        String start = dtf.print(startTime);
//        String end = dtf.print(endTime);

        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params)
            {
//                HttpTransportSE transport = new HttpTransportSE("");
//                NtlmTransport transport = new NtlmTransport();
                SOAPClient client = new SOAPClient();
                XTransport x = new XTransport();
                String requestAsString = x.getSoapRequestAsXml(envelope);
                client.callService(requestAsString, new SOAPClient.OnServiceListener()
                {
                    @Override
                    public void onServiceSuccess(String responseXml)
                    {
                       String response = responseXml;
                        int i = 2;
                    }
                });

//                try
//                {
////                    transport.debug = true;
////                    transport.call("GetUserAvailability", envelope);
//                }
//                catch(Exception e)
//                {
//                    int i = 1;
//                    e.printStackTrace();
//                }

                return null;
            }
        }.execute();
    }

    //test
    SoapObject getBody()
    {
        SoapObject template = new SoapObject("", "m:template_name");
        template.addAttribute("xmlns:t","type_namespace");
        template.addAttribute("xmlns:m", "message_namespace");

//        SoapPrimitive primNoValue = new SoapPrimitive("", "noValuePrim", "");
//        SoapPrimitive primValue = new SoapPrimitive("", "primValue", 2);
        PropertyInfo pi = new PropertyInfo();
        pi.setValue("val");
        pi.setName("t:ref_only");
        pi.setType(PropertyInfo.INTEGER_CLASS);

        PropertyInfo pi2 = new PropertyInfo();
        pi2.setValue("val");
        pi2.setName("t:multiref");
        pi2.setType(PropertyInfo.MULTI_REF);

        PropertyInfo pi3 = new PropertyInfo();
        pi3.setValue("val");
        pi3.setName("t:transient");
        pi3.setType(PropertyInfo.TRANSIENT);
        PropertyInfo pi4 = new PropertyInfo();
        pi4.setValue("val");
        pi4.setName("t:objectclass");
        pi4.setType(PropertyInfo.OBJECT_CLASS);

        PropertyInfo pi5 = new PropertyInfo();
        pi5.setValue("val");
        pi5.setName("t:objecttype");
        pi5.setType(PropertyInfo.OBJECT_TYPE);

        PropertyInfo pi6 = new PropertyInfo();
        pi6.setValue("val");
        pi6.setName("t:vectorclass");
        pi6.setType(PropertyInfo.VECTOR_CLASS);


        SoapObject empty = new SoapObject("", "t:emptyObject");
        empty.addAttribute("attr_name", "attr_value");

        SoapObject nullObj = new SoapObject(null, null);

        template.addProperty(pi);
        template.addProperty(pi2);
        template.addProperty(pi3);
        template.addProperty(pi4);
        template.addProperty(pi5);
        template.addProperty(pi6);
        template.addSoapObject(empty);


        return template;
    }

    Element[] getManualHeaders()
    {
        Element[] elements = new Element[1];

        Element e = new Element();
        e.setNamespace("namespace");
        e.setName("ime");
        e.setAttribute("", "attrname", "attrvalue");
        e.setPrefix("t", "namespace"); //ok

        Element innerElem = new Element().createElement("", "t:inner"); //nema namespace, dodaj manualno
        innerElem.setAttribute("", "innerattrname", "innerattvalue");

        e.addChild(2, innerElem);

        elements[0] = e;
        return elements;
    }

    Periods getPeriods()
    {
        String bias1Value = "-P0DT1H0M0.0S";
        AttributeBias bias1 = new AttributeBias(bias1Value);
        String name1Value = "Standard";
        AttributeName name1 = new AttributeName(name1Value);
        String id1Value = "Std";
        AttributeId id1 = new AttributeId(id1Value);
        Period period1 = new Period(bias1, name1, id1);

        String bias2Value = "-P0DT2H0M0.0S";
        AttributeBias bias2 = new AttributeBias(bias2Value);
        String name2Value = "Daylight";
        AttributeName name2 = new AttributeName(name2Value);
        String id2Value = "Dlt/1";
        AttributeId id2 = new AttributeId(id2Value);
        Period period2 = new Period(bias2, name2, id2);

        return new Periods(period1, period2);
    }

    RequestServerVersion getServerVersion()
    {
        /** ---- HEADER --- RequestServerVersion ----*/
        AttributeVersion versionAttribute = new AttributeVersion(ExchangeVersion.Exchange2013_SP1.name());
        RequestServerVersion requestServerVersion = new RequestServerVersion(versionAttribute);
        return requestServerVersion;
        /** ---- HEADER END --- RequestServerVersion ----*/
    }

    TimeZoneContext getTimeZoneContext()
    {
        /** ---- HEADER --- TimeZoneContext ----*/
        /** ---- First child of TimeZoneDefinition ---- */
        String bias1Value = "-P0DT1H0M0.0S";
        AttributeBias bias1 = new AttributeBias(bias1Value);
        String name1Value = "Standard";
        AttributeName name1 = new AttributeName(name1Value);
        String id1Value = "Std";
        AttributeId id1 = new AttributeId(id1Value);
        Period period1 = new Period(bias1, name1, id1);

        String bias2Value = "-P0DT2H0M0.0S";
        AttributeBias bias2 = new AttributeBias(bias2Value);
        String name2Value = "Daylight";
        AttributeName name2 = new AttributeName(name2Value);
        String id2Value = "Dlt/1";
        AttributeId id2 = new AttributeId(id2Value);
        Period period2 = new Period(bias2, name2, id2);

        Periods periods = new Periods(period1, period2);
        /** ---- END ---- */

        /** ---- Second child of TimeZoneDefinition ---- */
        //First RecurringDayTransition element
        AttributeKind toAttribute = new AttributeKind(AttributeKind.Values.Period.name());
        String toValue = "Dlt/1";
        To to = new To(toAttribute, toValue);
        String timeOffsetValue = "P0DT2H0M0.0S";
        TimeOffset timeOffset = new TimeOffset(timeOffsetValue);
        int monthValue = 3;
        Month month = new Month(monthValue);
        DayOfWeek.Values dayOfWeekValue = DayOfWeek.Values.Sunday;
        DayOfWeek dayOfWeek = new DayOfWeek(dayOfWeekValue);
        int occurrenceValue = -1;
        Occurrence occurence = new Occurrence(occurrenceValue);
        RecurringDayTransition recurringDayTransition1 = new RecurringDayTransition(to, timeOffset, month, dayOfWeek, occurence);

        //Second RecurringDayTransition element
        AttributeKind toAttribute2 = new AttributeKind(AttributeKind.Values.Period.name());
        String toValue2 = "Std";
        To to2 = new To(toAttribute2, toValue2);
        String timeOffsetValue2 = "P0DT3H0M0.0S";
        TimeOffset timeOffset2 = new TimeOffset(timeOffsetValue2);
        int monthValue2 = 10;
        Month month2 = new Month(monthValue2);
        DayOfWeek.Values dayOfWeekValue2 = DayOfWeek.Values.Sunday;
        DayOfWeek dayOfWeek2 = new DayOfWeek(dayOfWeekValue2);
        int occurrenceValue2 = -1;
        Occurrence occurence2 = new Occurrence(occurrenceValue2);
        RecurringDayTransition recurringDayTransition2 = new RecurringDayTransition(to2, timeOffset2, month2, dayOfWeek2, occurence2);

        //TransitionsGroup
        String idValue = "0";
        AttributeId attributeId = new AttributeId(idValue);
        TransitionsGroup transitionsGroup = new TransitionsGroup(attributeId, recurringDayTransition1, recurringDayTransition2);
        //TransitionsGroups
        TransitionsGroups transitionsGroups = new TransitionsGroups(transitionsGroup);
        /** ---- END ---- */

        /** ---- Third child of TimeZoneDefinition ---- */
        AttributeKind toAttribute3 = new AttributeKind(AttributeKind.Values.Group.name());
        String toValue3 = "0";
        To toTransitions = new To(toAttribute3, toValue3);
        Transition transition = new Transition(toTransitions);
        Transitions transitions = new Transitions(transition);
        /** ---- END ---- */

        /** ---- TimeZoneDefinition ----*/
        AttributeName nameAttribute = new AttributeName(TimeZoneAbbreviation.UTC_01);
        AttributeId idAttribute = new AttributeId(TimeZoneAbbreviation.CEST);
        TimeZoneDefinition timeZoneDefinition = new TimeZoneDefinition(
                nameAttribute, idAttribute, periods, transitionsGroups, transitions);
        /** ---- END ---- */

        TimeZoneContext timeZoneContext = new TimeZoneContext(timeZoneDefinition);
        return timeZoneContext;
    }

    GetUserAvailabilityRequest getGuar(String emailAddress, DateTime _startTime, DateTime _endTime)
    {
        Address address = new Address(emailAddress);
        Email email = new Email(address, null, null);
        AttendeeType attendeeType = new AttendeeType(AttendeeType.Values.Required);
        ExcludeConflicts excludeConflicts = new ExcludeConflicts(false);

        MailboxData mailboxData = new MailboxData(email, attendeeType, excludeConflicts);
        MailboxDataArray mailboxDataArray = new MailboxDataArray(mailboxData);


        StartTime startTime = new StartTime(_startTime);
        EndTime endTime = new EndTime(_endTime);

        TimeWindow timeWindow = new TimeWindow(startTime, endTime);
        MergedFreeBusyIntervalInMinutes mfbi = new MergedFreeBusyIntervalInMinutes(30);
        RequestedView requestedView = new RequestedView(RequestedView.Values.Detailed);

        FreeBusyViewOptions fbvo = new FreeBusyViewOptions(timeWindow, mfbi, requestedView);


        GetUserAvailabilityRequest guar = new GetUserAvailabilityRequest(mailboxDataArray, fbvo);

        return guar;
    }

}
