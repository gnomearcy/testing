package hr.span.processor.dependency_injection_roboguice;

import android.util.Log;

import com.google.inject.AbstractModule;

public class MyModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        Log.d("RoboGuice", "in configure method of MyModule");
        try{
//            bind(SimplePOJO.class).toConstructor(SimplePOJO.class.getConstructor(Integer.class, Integer.class));
            bind(Calculator.class).to(SimplePOJO.class);
        }
        catch(Exception e)
        {
            addError(e.getMessage());
        }
    }
}
