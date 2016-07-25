package eu.span.devosijek.cookie_store_custom;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpCookie;

public class SerializableHttpCookie implements Serializable
{
    private static final long serialVersionUID = 92881878288938828L;
    private static final String tag = "PersistentCookieStore";

    private transient HttpCookie cookie; //removed final

    public SerializableHttpCookie(HttpCookie cookie)
    {
        this.cookie = cookie;
    }

    public HttpCookie getCookie()
    {
        return cookie;
    }

    //HttpCookie fields
//    private String comment; +
//    private String commentURL; +
//    private boolean discard;
//    private String domain; +
//    private long maxAge = -1l; +
//    private final String name; +
//    private String path; +
//    private String portList;
//    private boolean secure; +
//    private boolean httpOnly;
//    private String value; +
//    private int version = 1; +

    private void writeObject(ObjectOutputStream out) throws IOException
    {
        //Name and value are written first for HttpCookie object
        //creation in readObject method of this class
        out.writeObject(cookie.getName());
        out.writeObject(cookie.getValue());

        out.writeObject(cookie.getComment());
        out.writeObject(cookie.getCommentURL());
        out.writeBoolean(cookie.getDiscard());
        out.writeObject(cookie.getDomain());
        out.writeLong(cookie.getMaxAge()); //Changed from ExpiryDate
        out.writeObject(cookie.getPath());
        out.writeObject(cookie.getPortlist());
        out.writeBoolean(cookie.getSecure());
        //httpOnly attribute has no getter, defaults to false
        //see HttpCookie#setAttribute for info about setting to true
        out.writeInt(cookie.getVersion());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException
    {
        String name = (String) in.readObject();
        String value = (String) in.readObject();

        Log.d(tag, "Name (key) / value -> " + name + "/" + value);

        cookie = new HttpCookie(name, value); //Name can not be set, it's final

        //todo maybe eat one result first (name one)
        String comment = (String) in.readObject();
        String commentUrl = (String) in.readObject();
        boolean discard = in.readBoolean();
        String domain = (String) in.readObject();
        long maxAge = in.readLong();
        String path = (String) in.readObject();
        String portList = (String) in.readObject();
        boolean secure = in.readBoolean();
        int version = in.readInt();

        Log.d(tag,
                "Name - "       + name      + "|"   +
                "Value - "      + value     + "|"   +
                "Comment - "    + comment   + "|"   +
                "CommentURL - "    + commentUrl     + "|"   +
                "Discard - "    + discard   + "|"   +
                "Domain - "     + domain    + "|"   +
                "maxAge - "     + maxAge    + "|"   +
                "Path - "       + path      + "|"   +
                "Port list - "  + portList  + "|"   +
                "version - "    + version   + "|"   +
                "secure - "     + secure
            );

        cookie.setValue(value);
        cookie.setComment(comment);
        cookie.setCommentURL(commentUrl);
        cookie.setDiscard(discard);
        cookie.setDomain(domain);
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        cookie.setPortlist(portList);
        cookie.setVersion(version);
        cookie.setSecure(secure);
    }
}
