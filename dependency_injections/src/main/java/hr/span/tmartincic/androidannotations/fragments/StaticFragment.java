package hr.span.processor.androidannotations.fragments;

import android.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import hr.span.processor.androidannotations.R;

@EFragment(R.layout.fragment_static)
public class StaticFragment extends Fragment
{
    @ViewById
    Button staticFragmentButton;

    @ViewById
    TextView staticDisplay;

    @Click(R.id.staticFragmentButton)
    void staticFragmentButtonPokaziNesto()
    {
        staticDisplay.setText("Static fragment");
    }
}
