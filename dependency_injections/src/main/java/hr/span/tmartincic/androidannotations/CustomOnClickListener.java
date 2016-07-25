package hr.span.processor.androidannotations;

import android.view.View;

import org.androidannotations.annotations.EBean;

/**
 * Created by tmartincic on 5/18/2015.
 */
@EBean
public class CustomOnClickListener implements View.OnClickListener
{
    @Override
    public void onClick(View v)
    {
        //do something
    }
}
