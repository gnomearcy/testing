package hr.span.tmartincic.dependency_injections_dagger;

import android.os.Bundle;

import javax.inject.Inject;

import hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency.Comp1;
import hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency.Comp2;
import hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency.DaggerComp1;
import hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency.DaggerComp2;
import hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency.Module1;
import hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency.Module2;
import hr.span.tmartincic.dependency_injections_dagger.di.chain_dependency.Module3;

/**
 *  We have following chain wrapper:
 *  CompA -> CompB -> CompC
 *  CompA gives Module1Object through Module1
 *  CompB gives Module2Object through Module2
 *  CompC gives Module3Object through Module3
 */
public class ActChainDependency extends ActivityBase
{
    @Inject
    public Module1.Module1Object obj1_1;
    @Inject
    public Module1.Module1Object obj1_2;

    @Inject
    public Module2.Module2Object obj2_sign_deps;
    @Inject
    public Module2.Module2Object obj2_sign_nodeps;

    @Inject
    public Module3.Module3Object obj3_sing_nodeps;
    @Inject
    public Module3.Module3Object obj3_sing_deps;
    @Inject
    public Module3.Module3Object obj3_nonsing_deps;
    @Inject
    public Module3.Module3Object obj3_nonsing_nodeps;

    /**
     *  Case where the inject method is present in the Comp2 and we try to
     *  inject the variables from all 3 components. (even those from Comp3)
     */
    private void chainDependencyComp2Injects()
    {
        Module1 module1 = new Module1();
        Comp1 comp1 = DaggerComp1.builder().module1(module1).build();
        Module2 module2 = new Module2();
        Comp2 comp2 = DaggerComp2.builder().comp1(comp1).module2(module2).build();

        log("///////////////////////////////");
        log("Component 2 injects things.");
        log("");

        // Every time we inject
        comp2.inject(this);
        log("Injecting first time...");
//        log("Object1 (singleton, no deps) - " + obj1_sing_nodeps.value);
//        log("Object1 (non-singleton, nodeps) - " + obj1_nonsing_nodeps.value);
//        log("Object2 (singleton, deps) - " + obj2_sign_deps.value);
//        log("Object2 (singleton, no deps) - " + obj2_sign_nodeps.value);
//        log("Object3 (singleton, no deps) - " + obj3_sing_nodeps.value);
//        log("Object3 (non-singleton, no deps) - " + obj3_nonsing_nodeps.value);
//        log("Object3 (singleton, deps) - " + obj3_sing_deps.value);
//        log("Object3 (non-singleton, deps) - " + obj3_nonsing_deps.value);

        log("");
        log("Injecting second time...");
        comp2.inject(this);
//        log("Object1 (singleton, no deps) - " + obj1_sing_nodeps.value);
//        log("Object1 (non-singleton, nodeps) - " + obj1_nonsing_nodeps.value);
//        log("Object2 (singleton, deps) - " + obj2_sign_deps.value);
//        log("Object2 (singleton, no deps) - " + obj2_sign_nodeps.value);
//        log("Object3 (singleton, no deps) - " + obj3_sing_nodeps.value);
//        log("Object3 (non-singleton, no deps) - " + obj3_nonsing_nodeps.value);
//        log("Object3 (singleton, deps) - " + obj3_sing_deps.value);
//        log("Object3 (non-singleton, deps) - " + obj3_nonsing_deps.value);
        log("");
        log("///////////////////////////////");
        breakpointPlaceholder();
    }


    /**
     *  Case where the inject method is present in the Comp3 and removed from Comp2.
     *  We should see that objects from Comp3 are properly injected (if that's what I wanted to do?)
     */
//    private void chainDependencyComp3Injects()
//    {
//        Module1 module1 = new Module1();
//        Comp1 comp1 = DaggerComp1.builder().module1(module1).build();
//        Module2 module2 = new Module2();
//        Comp2 comp2 = DaggerComp2.builder().comp1(comp1).module2(module2).build();
//        Module3 module3 = new Module3();
//        Comp3 comp3 = DaggerComp3.builder().comp2(comp2).module3(module3).build();
//
//        // Every time we inject
//        comp3.inject(this);
//        log("obj1Diff: " + obj1_sing_nodeps.value);
//        log("obj1_nonsing_nodeps: " + obj1_nonsing_nodeps.value);
//        log("obj2Diff: " + obj2_nonsign_deps.value);
//        log("obj2Same: " + obj2Same.value);
//        comp3.inject(this);
//        log("obj1Diff: " + obj1_sing_nodeps.value);
//        log("obj1_nonsing_nodeps: " + obj1_nonsing_nodeps.value);
//        log("obj2Diff: " + obj2_nonsign_deps.value);
//        log("obj2Same: " + obj2Same.value);
//
//        breakpointPlaceholder();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_chain_dependency);
        chainDependencyComp2Injects();
    }
}
