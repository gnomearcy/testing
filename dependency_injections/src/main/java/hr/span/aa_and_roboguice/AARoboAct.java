package hr.span.aa_and_roboguice;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.RoboGuice;
import org.androidannotations.annotations.ViewById;

import hr.span.processor.androidannotations.R;
import roboguice.inject.InjectView;

@EActivity(R.layout.activity_aarobo)
@RoboGuice({RoboListener.class})
public class AARoboAct extends Activity
{
//    @ViewById
//    Button showTextBtn;
    @ViewById
    EditText display;
    @Inject
    SImplePOJO pojo;

    @InjectView(R.id.showTextBtn) Button showTextBtn;

    @Click(R.id.showTextBtn)
    void clickOccured()
    {
        String message = display.getText().toString();
        pojo.sayHello(message);
    }

}
