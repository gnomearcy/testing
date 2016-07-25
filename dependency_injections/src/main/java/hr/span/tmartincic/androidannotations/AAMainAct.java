package hr.span.processor.androidannotations;

import android.app.Activity;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;

@EActivity(R.layout.activity_aamain)
@OptionsMenu(R.menu.menu_aamain)
public class AAMainAct extends Activity
{
    @Bean
    CustomClassChild cc;
}
