package hr.span.aa_and_roboguice;

import android.content.Context;
import android.widget.Toast;

import com.google.inject.Inject;

public class RoboListener
{
    int a;

    public RoboListener()
    {
        a = 10;
    }

    @Inject
    Context context;

    public void triggerOnResume()
    {
        Toast.makeText(context, "Resumed!", Toast.LENGTH_LONG).show();
    }
}
