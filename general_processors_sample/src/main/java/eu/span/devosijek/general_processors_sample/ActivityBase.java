package eu.span.devosijek.general_processors_sample;

import android.app.Activity;

/**
 * Created by tmartincic on 9/10/2015.
 */
public class ActivityBase extends Activity implements UtilInterface
{
    private static void customMethod()
    {
        call.showMessage(ActivityBase.class, "");
    }
}
