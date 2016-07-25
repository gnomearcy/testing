package hr.span.processor.androidannotations;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;

@EBean
public class CustomClassParent
{
    protected static final String tag = "AndroidAnnotations";
    //activity context
    @RootContext
    Context context;
    @ViewById
    Button custombtn;
    @ViewById
    TextView customtv;
    @ViewById
    EditText customet;

    @Click
    void custombtnClicked()
    {
        String name = customet.getText().toString();
        customtv.setText(name);
    }

    /**showToast is called by this class's child.*/
//    @AfterInject
    void showToast()
    {
        Log.d(tag, "custom class parent");
    }
}
