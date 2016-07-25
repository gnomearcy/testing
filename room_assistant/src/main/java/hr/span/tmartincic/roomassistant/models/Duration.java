package hr.span.tmartincic.roomassistant.models;

import org.joda.time.DateTime;

public class Duration
{
    private DateTime StartTime;
    private DateTime EndTime;

    public void setStartTime(DateTime startTime)
    {
        StartTime = startTime;
    }

    public void setEndTime(DateTime endTime)
    {
        EndTime = endTime;
    }

    public DateTime getEndTime()
    {
        return EndTime;
    }

    public DateTime getStartTime()
    {
        return StartTime;
    }
}
