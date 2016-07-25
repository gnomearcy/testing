package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

public class GoogleNetClientImpl implements GoogleNetClient
{
    private String key;

    public GoogleNetClientImpl(String key)
    {
        this.key = key;
    }

    @Override
    public String getGoogleKey()
    {
        return "Google";
    }

    @Override
    public void setGoogleKey(String key)
    {
        this.key = key;
    }

    @Override
    public String getUrl()
    {
        return "www.google.com";
    }
}
