package eu.span.dev.osijek.dimvp.demo2.interactor;

public class SynchronousLoginInteractor implements ILoginInteractor
{
    @Override
    public boolean validateCredentials(String username, String password)
    {
        return username.contains("gmail");
    }
}
