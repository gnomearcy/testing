package hr.span.processor.dependency_injection_roboguice;

import android.util.Log;

import com.google.inject.Inject;

public class SimplePOJO implements Calculator
{
    public int first;
    public int second;

    @Inject
    public SimplePOJO()
    {
        Log.d("RoboGuice", "Inside default SimplePOJO constructor.");
        first = 10;
        second = 20;
    }

    @Override
    public int addIntegers(int first, int second)
    {
        return first + second;
    }
    public String ispisi()
    {
        return String.valueOf(first) + ", " + String.valueOf(second);
    }
}
