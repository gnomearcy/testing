package hr.span.aa_and_roboguice;

import android.content.Context;
import android.widget.Toast;

import com.google.inject.Inject;

public class SImplePOJO
{
    String myName;
    Context context;

    @Inject
    public SImplePOJO(Context context)
    {
        this.myName = this.getClass().getName();
        this.context = context;
    }

    public void sayHello(String message)
    {
        Toast.makeText(context, myName + " says " + message, Toast.LENGTH_LONG).show();
    }
}
