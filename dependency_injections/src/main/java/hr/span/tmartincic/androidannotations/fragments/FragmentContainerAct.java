package hr.span.processor.androidannotations.fragments;

import android.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;


import hr.span.processor.androidannotations.R;

@EActivity(R.layout.activity_fragment_container)
public class FragmentContainerAct extends ActionBarActivity
{
    //Fragment injection
    @FragmentById
    DynamicFragment programaticFragment = new DynamicFragment_();

    @AfterInject
    void injectDynamicFragment()
    {
        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().add(R.id.programaticFragment, programaticFragment, "tag").commit();
    }
}
