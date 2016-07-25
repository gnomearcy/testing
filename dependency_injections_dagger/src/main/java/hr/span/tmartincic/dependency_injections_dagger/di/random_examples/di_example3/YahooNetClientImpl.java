package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

public class YahooNetClientImpl implements YahooNetClient
{
    private String key;

    public YahooNetClientImpl(String key)
    {
        this.key = key;
    }
    
    @Override
    public String getYahooKey()
    {
        return "Yahoo";
    }

    @Override
    public void setYahooKey(String key)
    {
        this.key = key;
    }

    @Override
    public String getUrl()
    {
        return "www.yahoo.com";
    }
}
