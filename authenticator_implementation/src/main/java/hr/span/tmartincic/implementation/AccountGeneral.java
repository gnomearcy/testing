package hr.span.tmartincic.implementation;

public class AccountGeneral
{
    //account id
    public final static String ACCOUNT_TYPE = "hr.span.tmartincic";
    //account name
    public final static String ACCOUNT_NAME = "Tmartincic";

    //constants for auth token types
    public final static String AUTHTOKEN_TYPE_READ_ONLY = "read only auth token";
    public final static String AUTHTOKEN_TYPE_READ_ONLY_LABEL = "READ ONLY LABEL";
    public final static String AUTHTOKEN_TYPE_FULL_ACCESS = "full access";
    public final static String AUTHTOKEN_TYPE_FULL_ACCESS_LABEL = "full access label";

    public static final String USERDATA_USER_OBJ_ID = "userObjectId"; //parse.com object id
    public static final ServerAuthenticate sServerAuthenticate = new ParseComServerAuthenticate();
}
