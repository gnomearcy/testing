package eu.span.devosijek.asos;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;

public class Protected extends ActivityAsos
{
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
    {
        Resources res = getResources();
        red = res.getColor(R.color.red);
        green = res.getColor(R.color.green);
        blue = res.getColor(R.color.blue);

        super.onCreate(savedInstanceState, persistentState);
    }
}
