package hr.span.tmartincic.ews;

public class w3cSoapRequest
{
    public static String getRequest()
    {
        return "<soap:Envelope \n" +
                "\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
                "\txmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" \n" +
                "\txmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" \n" +
                "\txmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                "\t<soap:Header />\n" +
                "\t<soap:Body>\n" +
                "\t\t<FahrenheitToCelsius xmlns=\"http://www.w3schools.com/webservices/\">\n" +
                "\t\t\t<Fahrenheit>50</Fahrenheit>\n" +
                "\t\t</FahrenheitToCelsius>\n" +
                "\t</soap:Body>\n" +
                "</soap:Envelope>";
    }
}
