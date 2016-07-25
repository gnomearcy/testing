package hr.span.processor.androidannotations.list_view_aa_style;

import android.util.Log;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.res.StringArrayRes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@EBean
public class PeopleFinderImpl implements PersonFinder
{
    @StringArrayRes
    String[] names;
    @StringArrayRes
    String[] lastnames;

    Random r = new Random();

    @AfterInject
    void something()
    {
        Log.d("AndroidAnnotations", "Something in people finder implementation.");
    }

    @Override
    public List<Person> findAll()
    {
        Log.d("AndroidAnnotations", "Finding all people. " + names[0] + ", " + lastnames.toString());
        ArrayList<Person> result = new ArrayList<>();
        for(int i = 0; i < 3; i++)
        {
            Person p = new Person(names[r.nextInt(names.length)], lastnames[r.nextInt(lastnames.length)]);
            result.add(p);
        }
        return result;
    }
}
