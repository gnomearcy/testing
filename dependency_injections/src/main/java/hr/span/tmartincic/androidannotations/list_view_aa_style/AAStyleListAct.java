package hr.span.processor.androidannotations.list_view_aa_style;

import android.app.Activity;
import android.provider.Contacts;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import hr.span.processor.androidannotations.R;

@EActivity(R.layout.activity_aastyle_list)
public class AAStyleListAct extends Activity
{
    @ViewById
    Button btnAddPerson;

    @ViewById
    ListView personlist;

    @Bean
    TestiramInjectionOrder testiram;
    @Bean
    Adapter adapter;

    @AfterInject
    void afterInjectOperation()
    {
        Log.d("AndroidAnnotations", "Activity - afterInject");
    }
    @AfterViews
    void bindAdapter()
    {
        Log.d("AndroidAnnotations", "Binding adapter");
        personlist.setAdapter(adapter);
    }

    @ItemClick(R.id.personlist)
    void listClicked(Person people)
    {
        Toast.makeText(this, "Name / Lastname " + people.name + ", " + people.lastname, Toast.LENGTH_SHORT).show();
    }
}
