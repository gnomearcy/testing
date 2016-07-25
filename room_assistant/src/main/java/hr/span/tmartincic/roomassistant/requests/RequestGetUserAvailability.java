package hr.span.tmartincic.roomassistant.requests;

import org.joda.time.DateTime;

import hr.span.tmartincic.roomassistant.ExtendedEnvelope;
import hr.span.tmartincic.roomassistant.ServiceInfo;
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

public class RequestGetUserAvailability
{
    public static ExtendedEnvelope create(String emailAddress, DateTime _startTime, DateTime _endTime)
    {
//        this.address = emailAddress;
//        this.startTime = _startTime;
//        this.endTime = _endTime;

        /* ---- Object hierarchy construction ---- */

        // Global objects
//        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(ServiceInfo.envelopeVersion);
        ExtendedEnvelope envelope = new ExtendedEnvelope(ServiceInfo.envelopeVersion);
        envelope.dotNet = ServiceInfo.isDotNet;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);

        // Header
//        Element[] headers = new Element[2];

        /** ---- HEADER --- RequestServerVersion ----*/
        AttributeVersion versionAttribute = new AttributeVersion(ExchangeVersion.Exchange2013_SP1.name());
        RequestServerVersion requestServerVersionHeader = new RequestServerVersion(versionAttribute);
        /** ---- HEADER END --- RequestServerVersion ----*/

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

        TimeZoneContext timeZoneContextHeader = new TimeZoneContext(timeZoneDefinition);
        /** ---- HEADER END --- TimeZoneContext ----*/

        /* Convert RequestServerVersion / TimeZoneContext to Element types*/

        /* Construct body */
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

        GetUserAvailabilityRequest getUserAvailabilityRequestBody = new GetUserAvailabilityRequest(mailboxDataArray, fbvo);


        envelope.headerOut = RequestUtils.parseHeader(requestServerVersionHeader, timeZoneContextHeader);
        envelope.setOutputSoapObject(RequestUtils.parseBody(getUserAvailabilityRequestBody));

        return envelope;
    }
}
