package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityComponentDependency extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_component_dependency);
    }
}
