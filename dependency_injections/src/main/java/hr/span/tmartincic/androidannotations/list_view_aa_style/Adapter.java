package hr.span.processor.androidannotations.list_view_aa_style;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

import hr.span.processor.androidannotations.R;

@EBean
public class Adapter extends BaseAdapter
{
    List<Person> persons;

    /** context is initialised to value from where the adapter was set (typically an Activity) */
    @RootContext
    Context context;

    /** composition reference to a guy who can find all people */
    @Bean(PeopleFinderImpl.class)
    PersonFinder finder;

    @AfterInject
    void initAdapter()
    {
        Log.d("AndroidAnnotations", "initAdapter");
        persons = finder.findAll();
    }

    @Override
    public int getCount()
    {
        return persons.size();
    }

    @Override
    public Person getItem(int position)
    {
        return persons.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        CustomView v;
        if(convertView == null)
        {
            v = CustomView_.build(context);
        }
        else
        {
            v = (CustomView) convertView;
        }

        v.bind(getItem(position));

        return v;
    }
}
