package hr.span.processor.androidannotations.fragments;

import android.app.Fragment;
import android.widget.Button;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import hr.span.processor.androidannotations.R;

@EFragment(R.layout.fragment_dynamic)
public class DynamicFragment extends Fragment
{
    @ViewById
    Button dynamicFragmentButton;
    @ViewById
    TextView dynamicDisplay;

    @Click(R.id.dynamicFragmentButton)
    void dynamicFragmentButtonClick()
    {
        dynamicDisplay.setText("Dynamic fragment");
    }
}
