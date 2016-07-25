package hr.span.tmartincic.ews.responses.FindItemResponse;

public class FindItemResponse
{
    //template

    private Class myClass = this.getClass();

    public Class getMyClass()
    {
        return this.myClass;
    }

    public String getName()
    {
        return this.getClass().getSimpleName();
    }
}
