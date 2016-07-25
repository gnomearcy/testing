package hr.span.tmartincic.roomassistant.models;

public abstract class Attribute
{
    public String value;
    public abstract String label();
    public String value()
    {
        return this.value;
    }
}
