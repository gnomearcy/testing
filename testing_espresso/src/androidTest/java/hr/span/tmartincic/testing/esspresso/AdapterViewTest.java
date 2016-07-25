package hr.span.tmartincic.testing.esspresso;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.DataInteraction;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Before;

import java.util.ArrayList;
import java.util.Random;

import hr.span.tmartincic.testing.esspresso.adapterview.ActivityAdapterView;

//espresso
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.Espresso.onView;
import static org.hamcrest.Matchers.anything;

public class AdapterViewTest extends ActivityInstrumentationTestCase2<ActivityAdapterView>
{
    private static final String tag = "EspressoTesting";

    ActivityAdapterView activityAdapterView;

    public AdapterViewTest()
    {
        super(ActivityAdapterView.class);
    }

    @Before
    @Override
    protected void setUp() throws Exception
    {
        Log.d(tag, "setUp in test");
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        setActivityInitialTouchMode(false);
        activityAdapterView = getActivity();
    }
     /** Test flow
      * - long click the list view
      * - randomly select 3 items
      * - click on those 3 items
      * - invoke delete action
      * - collapse contextual action bar (or automatically do it after delete)
      * - invoke star menu item
      * - assert
      * */

    public void testAdapterView()
    {
        int startSize = activityAdapterView.listContentSize; //10
//        Log.d(tag, "testAdapterView test -> list size is ----> " + startSize);

        //long click the list view
        onView(withId(R.id.adapterview_list)).perform(longClick());

        Object[] valuesToRemove = randomize(startSize);

//        Log.d(tag, "First value to remove is at position - " + (int) valuesToRemove[0]);

        //simulate click on 2 items generated (apart from the one from long click action)
        /**anything() is a hamcrest matcher - use it if we don't care about the object under test,
         * in our case the list view.*/
        for(Object value : valuesToRemove)
        {
            onData(anything())
                    .inAdapterView(withId(R.id.adapterview_list))
                    .atPosition((int) value)
                    .perform(click());
        }

        //invoke delete action
        onView(withId(R.id.cab_delete)).perform(click());

        //invoke star menu item
        onView(withId(R.id.how_many_left_in_list)).perform(click());

        //assert that list has 3 items less
        int currentListSize = activityAdapterView.listContentSize;
        assertEquals("asserting list size", startSize - 3, currentListSize);
        Log.d(tag, "Assertion has passed!");
    }

    private Object[] randomize(int maxValue)
    {
        int count = 2;
        ArrayList<Object> result = new ArrayList<>(count);
        Random r = new Random();

        while(count > 0)
        {
            int currentValue = r.nextInt(maxValue);

            //perform "long click" in first step always clicks the sixth element
            //we don't want that behaviour
            if(currentValue != 5)
            {
                if(!result.contains(currentValue))
                {
                    result.add(currentValue);
                    count--;
                }
            }
        }

        return result.toArray();
    }
}
