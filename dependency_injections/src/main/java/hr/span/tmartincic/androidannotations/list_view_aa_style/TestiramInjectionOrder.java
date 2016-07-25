package hr.span.processor.androidannotations.list_view_aa_style;

import android.support.annotation.StringRes;
import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.Trace;
import org.androidannotations.annotations.Transactional;
import org.androidannotations.annotations.res.MovieRes;

@EBean
public class TestiramInjectionOrder
{
    @StringRes
    String someText;

    @AfterInject
    void testMethod()
    {
        Log.d("AndroidAnnotations", "TestiramInjectionOrder - afterInject -> someText " + someText);
    }
}
