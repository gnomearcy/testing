package hr.span.tmartincic.testing.esspresso;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import static org.hamcrest.Matchers.allOf;

class ClearTextViewAction implements ViewAction
{
    @SuppressWarnings("uncheched")
    @Override
    public Matcher<View> getConstraints()
    {
        return allOf(isDisplayed(), isAssignableFrom(TextView.class));
    }

    @Override
    public String getDescription()
    {
        return "clear text";
    }

    @Override
    public void perform(UiController uiController, View view)
    {
        ((TextView)view).setText("");
    }
}