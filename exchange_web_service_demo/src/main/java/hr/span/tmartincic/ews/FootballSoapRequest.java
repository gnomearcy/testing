package hr.span.tmartincic.ews;

/**
 * Created by tmartincic on 6/18/2015.
 */
public class FootballSoapRequest
{
    public static SOAPRequest getFootball()
    {
        String request = "<v:Envelope \n" +
                        "\txmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\" \n" +
                        "\txmlns:d=\"http://www.w3.org/2001/XMLSchema\" \n" +
                        "\txmlns:c=\"http://schemas.xmlsoap.org/soap/encoding/\" \n" +
                        "\txmlns:v=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                        "\t<v:Header />\n" +
                        "\t<v:Body>\n" +
                        "\t\t<TopGoalScorers xmlns=\"http://footballpool.dataaccess.eu\" id=\"o0\" c:root=\"1\">\n" +
                        "\t\t\t<iTopN i:type=\"d:string\">5</iTopN>\n" +
                        "\t\t</TopGoalScorers>\n" +
                        "\t</v:Body>\n" +
                        "</v:Envelope>";
        return null;
    }
}
