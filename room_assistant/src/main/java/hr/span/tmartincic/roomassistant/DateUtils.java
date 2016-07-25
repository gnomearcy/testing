package hr.span.tmartincic.roomassistant;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtils
{
    public static String jodaToString(DateTime datetime)
    {
        DateTimeFormatter dtf = DateTimeFormat.forPattern(Constants.DATETIME_FORMAT);
        return dtf.print(datetime);
    }
}
