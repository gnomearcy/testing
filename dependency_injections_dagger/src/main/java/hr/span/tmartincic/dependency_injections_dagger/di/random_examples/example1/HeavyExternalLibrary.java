package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example1;

public class HeavyExternalLibrary
{
    private boolean isInitialized = false;

    public HeavyExternalLibrary(){}

    public void init()
    {
        try
        {
            Thread.sleep(500);
        }
        catch(InterruptedException e)
        {
            e.printStackTrace();
        }

        isInitialized = true;
    }

    public void callMethod()
    {
        if(!isInitialized)
            throw new RuntimeException("Call init() before you use this library");
    }
}
