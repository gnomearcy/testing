package hr.span.tmartincic.dependency_injections_dagger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances.Comp1;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances.Comp2;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances.Comp3;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances.DaggerComp1;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances.DaggerComp3;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances.Module1;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances.Module2;
import hr.span.tmartincic.dependency_injections_dagger.di.singleton_with_different_instances.Module3;

public class ActSingletonDifferentInstances extends AppCompatActivity
{
//    @Inject
//    public Module3.Module3Object singletonWithDifferentDependencies;

    @Inject
    public Module2.Module2Object wrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_singleton_different_instances);

        // Construct a parent to be able to add subcomponent
        Comp1 parentComp = DaggerComp1.builder().module1(new Module1()).build();
        Comp2 childComp = parentComp.plus(new Module2());

        childComp.inject(this);

        Module2.Module2Object objectWrapperForProvider = childComp.exposeObject2();
        Module1.Module1Object objectFromModule1 = objectWrapperForProvider.dependencyProvider.get();

        objectFromModule1 = objectWrapperForProvider.dependencyProvider.get();
        objectFromModule1 = objectWrapperForProvider.dependencyProvider.get();
        objectFromModule1 = objectWrapperForProvider.dependencyProvider.get();
        objectFromModule1 = objectWrapperForProvider.dependencyProvider.get();
        objectFromModule1 = objectWrapperForProvider.dependencyProvider.get();
        objectFromModule1 = objectWrapperForProvider.dependencyProvider.get();

//        Comp3 comp3 = DaggerComp3.builder().comp2(childComp).module3(new Module3()).build();
//        comp3.inject(this);

//        Module1.Module1Object object1 = singletonWithDifferentDependencies.getObject1();
//        object1 = singletonWithDifferentDependencies.getObject1();
//        object1 = singletonWithDifferentDependencies.getObject1();
//        object1 = singletonWithDifferentDependencies.getObject1();
//        object1 = singletonWithDifferentDependencies.getObject1();
//        object1 = singletonWithDifferentDependencies.getObject1();

    }
}
