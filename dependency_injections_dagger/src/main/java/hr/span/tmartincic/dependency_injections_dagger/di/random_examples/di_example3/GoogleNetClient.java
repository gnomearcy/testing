package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

public interface GoogleNetClient extends NetClient
{
    String getGoogleKey();
    void setGoogleKey(String key);
}
