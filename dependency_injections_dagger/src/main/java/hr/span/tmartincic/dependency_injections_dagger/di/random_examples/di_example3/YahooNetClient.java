package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

public interface YahooNetClient extends NetClient
{
    String getYahooKey();
    void setYahooKey(String key);
}
