package hr.span.tmartincic.dependency_injections_dagger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class ActivityEntry extends Activity
{
    public static final String TAG = "DITesting";

    static final Class[] activities = new Class[]
    {
            ActChainDependency.class,
            ActSubcomponent.class,
            ActExtendComp.class,
            ActSingletonNamedObjects.class,
            ActSingletonDifferentInstances.class
    };

    @Bind(R.id.list_entry)
    ListView s;

    @OnItemClick(R.id.list_entry)
    public void itemClicked(int position)
    {
        Intent i = new Intent();
        i.setClass(getApplicationContext(), activities[position]);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);
        s.setAdapter(makeAdapter());
    }

    private ArrayAdapter<String> makeAdapter()
    {
        List<String> list = new ArrayList<>();

        for(Class c : activities)
        {
            list.add(c.getSimpleName());
        }

        return new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
    }
}
