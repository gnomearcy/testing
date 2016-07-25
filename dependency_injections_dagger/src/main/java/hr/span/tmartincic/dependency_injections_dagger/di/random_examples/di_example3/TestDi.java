package hr.span.tmartincic.dependency_injections_dagger.di.random_examples.di_example3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TestDi extends AppCompatActivity
{
//    @Inject @Named("google")
//    NetClient googleClient;
//
//    @Inject @Named("yahoo")
//    NetClient yahooClient;

//    @Inject
//    Car car1;
//
//    @Inject
//    Car car2;

//    @Inject
//    Car car3;
//
//    @Inject
//    Car car4;

//    @Inject
//    Engine engine1;
//
//    @Inject
//    Engine engine2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_di);

        constructComponentDependency();

        dummy();
    }

    private void constructComponentDependency()
    {

    }

    private void dummy(){}
}
