package eu.span.dev.osijek.di.scoping.data.ui.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import trainer.blanka.hr.testdagger.R;

public abstract class ActivityBase extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_base);

        setupActivityComponent();
    }

    protected abstract void setupActivityComponent();
}
