package hr.span.tmartincic.roomassistant;

import org.ksoap2.SoapEnvelope;

public class ServiceInfo
{
    public static final String serviceUrl = "https://eagle03.span.bay:444/EWS/Exchange.asmx";
    public static final String username = "msa@span.eu";
    public static final String password = "P@ssw0rd4ASM!";
    public static final String userDomain = "span";

    //Envelope settings
    public static final int envelopeVersion = SoapEnvelope.VER11;
    public static final boolean isDotNet = true;

}