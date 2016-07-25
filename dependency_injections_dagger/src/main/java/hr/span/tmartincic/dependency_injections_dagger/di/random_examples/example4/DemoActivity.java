package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.example4;

import android.app.Activity;
import android.os.Bundle;

import eu.span.dev.osijek.testdi.component.DaggerDemoActivityComponent;
import eu.span.dev.osijek.testdi.component.DemoActivityComponent;
import eu.span.dev.osijek.testdi.module.ActivityModule;

public class DemoActivity extends Activity implements HasComponent<DemoActivityComponent>
{
    private DemoActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        component = DaggerDemoActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .demoApplicationComponent(((DemoApplication) getApplication()).getComponent())
                .build();

        setContentView(R.layout.activity_demo);

        if(savedInstanceState == null)
        {
            getFragmentManager().beginTransaction().add(R.id.container, new DemoFragment()).commit();
        }
    }

    @Override
    public DemoActivityComponent getComponent()
    {
        return component;
    }
}
