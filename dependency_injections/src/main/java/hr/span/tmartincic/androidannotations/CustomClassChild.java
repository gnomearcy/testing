package hr.span.processor.androidannotations;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;

@EBean
public class CustomClassChild extends CustomClassParent
{
    @AfterInject
    void showToast()
    {
        super.showToast();
        Log.d(tag, "custom class child");
    }
}
