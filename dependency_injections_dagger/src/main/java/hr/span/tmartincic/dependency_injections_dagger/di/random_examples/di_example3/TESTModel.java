package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import android.app.Application;
import android.content.Context;

import eu.span.dev.osijek.di.compdepend.model.Motor;
import eu.span.dev.osijek.di.compdepend.model.Vehicle;

public class TESTModel
{
    String name;

    public TESTModel(Application app, Context context, Vehicle v, Motor m)
    {
        name = "test ime";
    }

    public String getName()
    {
        return name;
    }
}
