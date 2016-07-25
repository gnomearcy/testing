package eu.span.dev.osijek.proguard.rules;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
    FragmentManager manager;
    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Bind(R.id.changeFragments)
    View view;

    int backStackCount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // See if this throws a NPE when using proguard
        fragmentContainer.setBackgroundColor(Color.GRAY);

        manager = getSupportFragmentManager();
//        manager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener()
//        {
//            @Override
//            public void onBackStackChanged()
//            {
//                // First time adding to back stack
//                if(backStackCount == -1){
//                    Log.d("Testing123", "Back stack was empty, probably adding a transaction");
//                }
//
//                int oldValue = backStackCount;
//                backStackCount = manager.getBackStackEntryCount();
//                if(oldValue > backStackCount)
//                {
//                    Log.d("Testing123", "Something was removed from back stack");
//                }
//
//                Log.d("Testing123", String.format("Current backstack count %d", backStackCount));
//                if(backStackCount == 0)
//                {
//                    backStackCount = -1;
//                    Log.d("Testing123", "Nothing is present on the backstack, reseting counter...");
//                }
//                else{
//                    for(int i = 0; i < backStackCount; i++)
//                    {
//                        FragmentManager.BackStackEntry entry = manager.getBackStackEntryAt(i);
//                        Log.d("Testing123", String.format("Transaction - \"%s\"", entry.getName() != null ? entry.getName() : "Null name"));
//                    }
//                }
//
//                lock = false;
//            }
//        });
        FragmentTransaction transaction = manager.beginTransaction();
        FragmentMain mainFragment = FragmentMain.newInstance();
        transaction.add(R.id.fragment_container, mainFragment, "MainFragment");
        transaction.commit();
    }

    @OnClick(R.id.changeFragments)
    public void click(View view)
    {
        // Find both fragments and manipulate transition based on which one is visible
        fromMainToLoading();
    }

    private void fromMainToLoading()
    {
        // Checkup
        FragmentMain main = (FragmentMain) manager.findFragmentByTag("MainFragment");
        if(main == null)
            throw new IllegalStateException("Main Fragment not found.");
        FragmentLoading loading = (FragmentLoading) manager.findFragmentByTag("LoadingFragment");
        if(loading == null){
            Log.d("Testing123", "Creating new LoadingFragment");
            loading = new FragmentLoading();
        }
        FragmentTransaction transaction = manager.beginTransaction();
        Log.d("Testing123", "Not recording back stack to loading fragment...");
        transaction.addToBackStack(null);
        transaction.remove(main);
        transaction.commit();

        transaction = manager.beginTransaction();
        transaction.addToBackStack("Loading to details");
        transaction.add(R.id.fragment_container, loading, "LoadingFragment");
        transaction.commit();
    }

    public void fromLoadingToDetails()
    {
        // Testing - remove loading view
//        Fragment load = manager.findFragmentByTag("LoadingFragment");
//        manager.beginTransaction().detach(load).commit();
        FragmentDetails details = (FragmentDetails) manager.findFragmentByTag("DetailsFragment");
        if(details == null)
            details = FragmentDetails.newInstance();
        FragmentLoading loading = (FragmentLoading) manager.findFragmentByTag("LoadingFragment");
        FragmentTransaction transaction = manager.beginTransaction();
        boolean loadingRemoved = false;
        if(loading != null){
            Log.d("Testing123", "Detaching FragmentLoading");
            transaction.addToBackStack(null);
            transaction.remove(loading).commit();
            loadingRemoved = true;
        }

        // If loading was removed in this phase, we need a new transaction?
        if(loadingRemoved)
            transaction = manager.beginTransaction();
        transaction.addToBackStack("Loading to details.");
        transaction.add(R.id.fragment_container, details, "DetailsFragment");
        transaction.commit();
    }

    public void onFragmentManipulation()
    {
        dummy();
    }

    public static final void dummy(){}
    public void fromDetailsToMain(){

    }
}
